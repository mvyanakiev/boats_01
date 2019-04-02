package boats.web.controllers;


import boats.domain.models.binding.BoatAddBindingModel;
import boats.domain.models.serviceModels.BaseServiceModel;
import boats.domain.models.serviceModels.BoatServiceModel;
import boats.domain.models.view.BoatAllViewModel;
import boats.domain.models.view.BoatDetailsViewModel;
import boats.service.BoatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/boats")
public class BoatController extends BaseController {

    private final BoatService boatService;
    private final ModelMapper modelMapper;

    @Autowired
    public BoatController(BoatService boatService, ModelMapper modelMapper) {
        this.boatService = boatService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") BoatAddBindingModel bindingModel) {
        modelAndView.addObject("bindingModel", bindingModel);

        return super.view("/boats/add-boat", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") BoatAddBindingModel bindingModel
    ) {

        BoatServiceModel boatServiceModel = this.modelMapper.map(bindingModel, BoatServiceModel.class);
        boatServiceModel = this.boatService.addBoat(boatServiceModel);

        if (boatServiceModel == null) {
            throw new IllegalArgumentException("Boat not added!");
        }

        return super.redirect("/show");
    }

    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showAllBoats(ModelAndView modelAndView) {
        modelAndView.addObject("boats", this.boatService.findAllBoats()
                .stream()
                .map(b -> this.modelMapper.map(b, BoatAllViewModel.class))
                .collect(Collectors.toList()));

        return super.view("/boats/all-boats", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView boatDetailsView(@PathVariable("id") String id, ModelAndView modelAndView, BoatDetailsViewModel model) {
        model = this.modelMapper.map(this.boatService.findBoatById(id), BoatDetailsViewModel.class);
        modelAndView.addObject("model", model);

        return super.view("/boats/boat-details", modelAndView);
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView boatEditView(@PathVariable("id") String id, ModelAndView modelAndView, BoatDetailsViewModel model) {
        model = this.modelMapper.map(this.boatService.findBoatById(id), BoatDetailsViewModel.class);
        modelAndView.addObject("model", model);

        return super.view("/boats/boat-edit", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView saveEditedVirus(@PathVariable("id") String id,
                                        @Valid @ModelAttribute(name = "bindingModel") BoatDetailsViewModel bindingModel){

        BoatServiceModel boatServiceModel = this.modelMapper.map(bindingModel, BoatServiceModel.class);
        boatServiceModel.setId(id);
        this.boatService.saveEditedBoat(boatServiceModel);

        // todo validation

        if (boatServiceModel == null) {
            throw new IllegalArgumentException("Boat do not saved!");
        }
            return super.redirect("/boats/show");
    }




}
