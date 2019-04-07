package boats.web.controllers;

import boats.domain.models.binding.CharterAddBindingModel;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView addCharterStep1(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            CharterAddBindingModel bindingModel) {

        modelAndView.addObject("bindingModel", bindingModel);

        modelAndView.addObject("boats",
                this.boatService.findAllBoats()
                        .stream()
                        .map(x -> this.modelMapper.map(x,
                                BoatListViewModel.class))
                        .collect(Collectors.toList()));

        modelAndView.addObject("directions",
                this.directionsService.findAllDirections()
                        .stream()
                        .map(x -> this.modelMapper.map(x,
                                DirectionListViewModel.class))
                        .collect(Collectors.toList()));

        modelAndView.addObject("peoples",
                this.peopleService.findAllCustomers()
                        .stream()
                        .map(x -> this.modelMapper.map(x,
                                PeopleListViewModel.class))
                        .collect(Collectors.toList()));

        return super.view("/charters/add-charter", modelAndView);
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
