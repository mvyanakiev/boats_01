package boats.web.controllers;


import boats.domain.entities.Boat;
import boats.domain.entities.Direction;
import boats.domain.entities.People;
import boats.domain.models.binding.CharterAdd_Step3_BindingModel;
import boats.domain.models.binding.CharterAdd_Step1_BindingModel;
import boats.domain.models.binding.CharterAdd_Step2_BindingModel;
import boats.domain.models.serviceModels.CharterServiceModel;
import boats.domain.models.view.*;
import boats.service.interfaces.BoatService;
import boats.service.interfaces.CharterService;
import boats.service.interfaces.DirectionsService;
import boats.service.interfaces.PeopleService;
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
    public CharterController(CharterService charterService,
                             ModelMapper modelMapper,
                             BoatService boatService,
                             PeopleService peopleService,
                             DirectionsService directionsService) {
        this.charterService = charterService;
        this.modelMapper = modelMapper;
        this.boatService = boatService;
        this.peopleService = peopleService;
        this.directionsService = directionsService;
    }


    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createCharter_step1_SelectDateAndDirection(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            CharterAdd_Step1_BindingModel bindingModel) {


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
    public ModelAndView createCharter_step2_FindBoats(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
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
    public ModelAndView createCharter_step3_AddCustomer(@PathVariable("id") String boatId,
                                         ModelAndView modelAndView, HttpSession session,
                                         CharterAdd_Step3_BindingModel charterAddStep3BindingModel,
                                         PeopleListViewModel customers) {

        String date = (String) session.getAttribute("startDate");
        String directionId = (String) session.getAttribute("directionId");

        Boat boat = this.modelMapper.map(this.boatService.findBoatById(boatId), Boat.class);
        Direction direction = this.modelMapper.map(this.directionsService.findDirectionById(directionId), Direction.class);
        BigDecimal price = direction.getPrice().add(boat.getPrice());

        charterAddStep3BindingModel.setBoat(boat);
        charterAddStep3BindingModel.setDirection(direction);
        charterAddStep3BindingModel.setStartDate(LocalDate.parse(date));
        charterAddStep3BindingModel.setPrice(price);


        session.setAttribute("charter", charterAddStep3BindingModel);


        modelAndView.addObject("peoples",
                this.peopleService.findAllCustomers()
                        .stream()
                        .map(x -> this.modelMapper.map(x,
                                PeopleListViewModel.class))
                        .collect(Collectors.toList()));


        modelAndView.addObject("charter", charterAddStep3BindingModel);

        //todo clear session key = null

        return super.view("/charters/final-add", modelAndView);
    }


    @PostMapping("/complete")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createCharter_step4_completeCharterCreation(@ModelAttribute(name = "peopleBinding") PeopleListViewModel peopleBindingModel,
                                                      HttpSession session, BindingResult bindingResult) {


        CharterAdd_Step3_BindingModel charter = (CharterAdd_Step3_BindingModel) session.getAttribute("charter");

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
    public ModelAndView showAllCharters(ModelAndView modelAndView) {
        modelAndView.addObject("charters", this.charterService.findAllCharters()
                .stream()
                .map(b -> this.modelMapper.map(b, CharterViewModel.class))
                .collect(Collectors.toList()));

        return super.view("/charters/all-charters", modelAndView);
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteCharter(ModelAndView modelAndView, @PathVariable("id") String charterId) {

        //todo try-catch

        this.charterService.deleteCharter(charterId);

        return super.redirect("/charters/show");
    }
}
