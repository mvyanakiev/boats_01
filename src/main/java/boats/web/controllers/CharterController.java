package boats.web.controllers;


import boats.domain.models.binding.CharterAddStep1BindingModel;
import boats.domain.models.binding.CharterAdd_Step2_BindingModel;
import boats.domain.models.serviceModels.BoatServiceModel;
import boats.domain.models.view.*;
import boats.service.BoatService;
import boats.service.CharterService;
import boats.service.DirectionsService;
import boats.service.PeopleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView step1AddCharter(ModelAndView modelAndView, @ModelAttribute (name = "bindingModel")
            CharterAddStep1BindingModel bindingModel) {


        List<DirectionListViewModel> directions = this.directionsService.findAllDirections()
                .stream()
                .map(x -> this.modelMapper.map(x,
                        DirectionListViewModel.class))
                .collect(Collectors.toList());

        bindingModel.setDirections(directions);

        modelAndView.addObject("bindingModel", bindingModel);

        String startDate = "2005-08-22";
        String directionId = "f78f6a7e-4659-11e9-b210-d663bd873d93";
        List<BoatServiceModel> availableBoats = this.boatService.findAvailableBoats(startDate, directionId);



        return super.view("/charters/add-charter", modelAndView);

//        modelAndView.addObject("peoples",
//                this.peopleService.findAllCustomers()
//                        .stream()
//                        .map(x -> this.modelMapper.map(x,
//                                PeopleListViewModel.class))
//                        .collect(Collectors.toList()));

    }


    @PostMapping("/select-boat")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView step2FindBoat(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            CharterAdd_Step2_BindingModel bindingModel) {

            bindingModel.getId();

        System.out.println("debug");
        //todo how to get from binding model



        // test only
        String startDate = "2005-08-22";
        String directionId = "f78f6a7e-4659-11e9-b210-d663bd873d93";


        List<BoatServiceModel> availableBoats = this.boatService.findAvailableBoats(startDate, directionId);

        modelAndView.addObject("boats", availableBoats);


        return super.view("/charters/set-customer", modelAndView);
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


}
