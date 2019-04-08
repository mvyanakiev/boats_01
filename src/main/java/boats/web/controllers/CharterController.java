package boats.web.controllers;


import boats.domain.entities.Boat;
import boats.domain.entities.Direction;
import boats.domain.entities.People;
import boats.domain.models.binding.CharterAddBindingModel;
import boats.domain.models.binding.CharterAddStep1BindingModel;
import boats.domain.models.binding.CharterAdd_Step2_BindingModel;
import boats.domain.models.serviceModels.CharterServiceModel;
import boats.domain.models.view.*;
import boats.service.BoatService;
import boats.service.CharterService;
import boats.service.DirectionsService;
import boats.service.PeopleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/charters")
public class CharterController extends BaseController {

    private final CharterService charterService;
    private final ModelMapper modelMapper;
    private final BoatService boatService;
    private final PeopleService peopleService;
    private final DirectionsService directionsService;

    @Autowired
    public CharterController(CharterService charterService, ModelMapper modelMapper, BoatService boatService, PeopleService peopleService, DirectionsService directionsService) {
        this.charterService = charterService;
        this.modelMapper = modelMapper;
        this.boatService = boatService;
        this.peopleService = peopleService;
        this.directionsService = directionsService;
    }


    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView step1AddCharter(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            CharterAddStep1BindingModel bindingModel) {


        List<DirectionListViewModel> directions = this.directionsService.findAllDirections()
                .stream()
                .map(x -> this.modelMapper.map(x,
                        DirectionListViewModel.class))
                .collect(Collectors.toList());

        bindingModel.setDirections(directions);

        modelAndView.addObject("bindingModel", bindingModel);

        return super.view("/charters/add-charter", modelAndView);
    }


    @PostMapping("/select-boat")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView step2FindBoat(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            CharterAdd_Step2_BindingModel bindingModel, HttpSession session) {

        List<BoatSelectViewModel> availableBoats = this.boatService
                .findAvailableBoats(bindingModel.getStartDate(), bindingModel.getId())
                .stream()
                .map(b -> this.modelMapper.map(b, BoatSelectViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("boats", availableBoats);

        session.setAttribute("startDate", bindingModel.getStartDate());
        session.setAttribute("directionId", bindingModel.getId());

        return super.view("/charters/select-boat", modelAndView);
    }


    @GetMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView step3AddCustomer(@PathVariable("id") String boatId,
                                         ModelAndView modelAndView, HttpSession session,
                                         CharterAddBindingModel charterAddBindingModel,
                                         PeopleListViewModel customers) {

        String date = (String) session.getAttribute("startDate");
        String directionId = (String) session.getAttribute("directionId");

        Boat boat = this.modelMapper.map(this.boatService.findBoatById(boatId), Boat.class);
        Direction direction = this.modelMapper.map(this.directionsService.findDirectionById(directionId), Direction.class);
        BigDecimal price = direction.getPrice().add(boat.getPrice());

        charterAddBindingModel.setBoat(boat);
        charterAddBindingModel.setDirection(direction);
        charterAddBindingModel.setStartDate(LocalDate.parse(date));
        charterAddBindingModel.setPrice(price);


        session.setAttribute("charter", charterAddBindingModel);


        modelAndView.addObject("peoples",
                this.peopleService.findAllCustomers()
                        .stream()
                        .map(x -> this.modelMapper.map(x,
                                PeopleListViewModel.class))
                        .collect(Collectors.toList()));


        modelAndView.addObject("charter", charterAddBindingModel);

        //todo clear session key = null

        return super.view("/charters/final-add", modelAndView);
    }


    @PostMapping("/complete")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView completeCharter(@ModelAttribute(name = "peopleBinding") PeopleListViewModel peopleBindingModel, HttpSession session,
                                        BindingResult bindingResult) {


        CharterAddBindingModel charter = (CharterAddBindingModel) session.getAttribute("charter");

        People people = this.modelMapper.map(this.peopleService.findPeopleById(peopleBindingModel.getId()), People.class);
        charter.setCustomer(people);

        this.charterService.addCharter(this.modelMapper.map(charter, CharterServiceModel.class));


//        if (bindingResult.hasErrors()) {
//            return super.redirect("/boats/add");
////            throw new IllegalArgumentException("Boat not added! (invalid data)");
//        }

        return redirect("/charters/show");
    }


    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showAllBoats(ModelAndView modelAndView) {
        modelAndView.addObject("charters", this.charterService.findAllCharters()
                .stream()
                .map(b -> this.modelMapper.map(b, CharterViewModel.class))
                .collect(Collectors.toList()));

        return super.view("/charters/all-charters", modelAndView);
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteCharter(ModelAndView modelAndView, @PathVariable("id") String charterId) {

        this.charterService.deleteCharter(charterId);

        return super.redirect("/charters/show");
    }
}
