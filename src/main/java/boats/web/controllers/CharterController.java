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
    public ModelAndView createCharter_step1_SelectDateAndDirection(
            ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            CharterAdd_Step1_BindingModel bindingModel,
            BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return super.redirect("/charters/add");
        }

        List<DirectionListViewModel> directions = this.directionsService.findAllDirections()
                .stream()
                .map(x -> this.modelMapper
                        .map(x, DirectionListViewModel.class))
                .collect(Collectors.toList());

        bindingModel.setDirections(directions);

        modelAndView.addObject("bindingModel", bindingModel);

        return super.view("/charters/step1-select-date", modelAndView);
    }


    @PostMapping("/select-boat")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createCharter_step2_SelectBoat(
            ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            CharterAdd_Step2_BindingModel bindingModel, HttpSession session,
            BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return super.redirect("/charters/add");
        }


        try {
            if (LocalDate.parse(bindingModel.getStartDate()).isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Date must not be in the past!");
            }
        } catch (Exception e){
            return super.redirect("/charters/add");
        }


        List<BoatSelectViewModel> availableBoats = this.boatService
                .findAvailableBoats(bindingModel.getStartDate(), bindingModel.getId())
                .stream()
                .map(b -> this.modelMapper.map(b, BoatSelectViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("boats", availableBoats);

        session.setAttribute("startDate", bindingModel.getStartDate());
        session.setAttribute("directionId", bindingModel.getId());

        return super.view("/charters/step2-select-boat", modelAndView);
    }


    @GetMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createCharter_step3_SelectCustomer(@PathVariable("id") String boatId,
                                                           ModelAndView modelAndView, HttpSession session,
                                                           CharterAdd_Step3_BindingModel charterBindingModel,
                                                           PeopleListViewModel customers,
                                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return super.redirect("/charters/step2-select-boat");
        }

        String date = (String) session.getAttribute("startDate");
        String directionId = (String) session.getAttribute("directionId");

        session.removeAttribute("startDate");
        session.removeAttribute("directionId");

        Boat boat = this.modelMapper.map(this.boatService.findBoatById(boatId), Boat.class);
        Direction direction = this.modelMapper.map(this.directionsService.findDirectionById(directionId), Direction.class);
        BigDecimal price = direction.getPrice().add(boat.getPrice());

        charterBindingModel.setBoat(boat);
        charterBindingModel.setDirection(direction);
        charterBindingModel.setStartDate(LocalDate.parse(date));
        charterBindingModel.setPrice(price);

        session.setAttribute("charter", charterBindingModel);

        modelAndView.addObject("peoples",
                this.peopleService.findAllCustomers()
                        .stream()
                        .map(x -> this.modelMapper.map(x,
                                PeopleListViewModel.class))
                        .collect(Collectors.toList()));

        modelAndView.addObject("charter", charterBindingModel);

        return super.view("/charters/step3-complete-adding", modelAndView);
    }


    @PostMapping("/complete")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createCharter_step4_completeCharterCreation(
            @ModelAttribute(name = "peopleBinding") PeopleListViewModel peopleBindingModel,
            HttpSession session, BindingResult bindingResult) {

        CharterAdd_Step3_BindingModel charter = (CharterAdd_Step3_BindingModel) session.getAttribute("charter");
        session.removeAttribute("charter");


        if (bindingResult.hasErrors()) {
            return super.redirect("/charters/step3-complete-adding");
        }

        People people = this.modelMapper.map(this.peopleService.findPeopleById(peopleBindingModel.getId()), People.class);
        charter.setCustomer(people);

        this.charterService.addCharter(this.modelMapper.map(charter, CharterServiceModel.class));

        return redirect("/charters/show");
    }


    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showAllCharters(ModelAndView modelAndView) {
        modelAndView.addObject("charters", this.charterService.findAllCharters()
                .stream()
                .map(b -> this.modelMapper.map(b, CharterViewModel.class))
                .collect(Collectors.toList()));

        return super.view("/charters/charters-all", modelAndView);
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCharter(@PathVariable("id") String charterId) {

        this.charterService.deleteCharter(charterId);

        return super.redirect("/charters/show");
    }
}
