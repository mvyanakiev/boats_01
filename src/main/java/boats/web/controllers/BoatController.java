package boats.web.controllers;


import boats.domain.models.binding.BoatAddBindingModel;
import boats.domain.models.binding.BoatEditBindingModel;
import boats.domain.models.serviceModels.BoatServiceModel;
import boats.domain.models.view.BoatAllViewModel;
import boats.domain.models.view.BoatDetailsViewModel;
import boats.domain.models.view.EquipmentViewModel;
import boats.service.interfaces.BoatService;
import boats.service.interfaces.EquipmentService;
import boats.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/boats")
public class BoatController extends BaseController {

    private final BoatService boatService;
    private final ModelMapper modelMapper;
    private final EquipmentService equipmentService;

    @Autowired
    public BoatController(BoatService boatService, ModelMapper modelMapper,
                          EquipmentService equipmentService) {
        this.boatService = boatService;
        this.modelMapper = modelMapper;
        this.equipmentService = equipmentService;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add")
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            BoatAddBindingModel bindingModel) {

        modelAndView.addObject("bindingModel", bindingModel);

        return super.view("/boats/boat-add", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") BoatAddBindingModel bindingModel,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return super.redirect("/boats/add");
//            throw new IllegalArgumentException("Boat not added! (invalid data)");
        }

        BoatServiceModel boatServiceModel = this.modelMapper.map(bindingModel, BoatServiceModel.class);
        boatServiceModel = this.boatService.addBoat(boatServiceModel);

        if (boatServiceModel == null) {
            throw new IllegalArgumentException("Boat not added! (service error)");
        }

        return super.redirect("/boats/show");
    }


    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All boats")
    public ModelAndView showAllBoats(ModelAndView modelAndView) {
        modelAndView.addObject("boats", this.boatService.findAllBoats()
                .stream()
                .map(b -> this.modelMapper.map(b, BoatAllViewModel.class))
                .collect(Collectors.toList()));

        return super.view("/boats/boats-all", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Details")
    public ModelAndView boatDetailsView(@PathVariable("id") String id, ModelAndView modelAndView,
                                        BoatDetailsViewModel model) {

        model = this.modelMapper.map(this.boatService.findBoatById(id), BoatDetailsViewModel.class);
        modelAndView.addObject("model", model);

        List<EquipmentViewModel> boatEquipments = this.equipmentService.findByBoatId(id).stream()
                .map(e -> this.modelMapper.map(e, EquipmentViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("boatEquipments", boatEquipments);

        return super.view("/boats/boat-details", modelAndView);
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit")
    public ModelAndView boatEditView(@PathVariable("id") String id, ModelAndView modelAndView, BoatEditBindingModel model) {
        model = this.modelMapper.map(this.boatService.findBoatById(id), BoatEditBindingModel.class);
        modelAndView.addObject("model", model);

        return super.view("/boats/boat-edit", modelAndView);
    }


    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView saveEditedBoat(@PathVariable("id") String id,
                                        @Valid @ModelAttribute(name = "bindingModel")
                                                BoatEditBindingModel bindingModel,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
//            throw new IllegalArgumentException("Boat not edited! (invalid data)");
            return super.redirect("/boats/edit/" + id);
        }



        BoatServiceModel boatServiceModel = this.modelMapper.map(bindingModel, BoatServiceModel.class);
        boatServiceModel.setId(id);
        boatServiceModel = this.boatService.saveEditedBoat(boatServiceModel);

        if (boatServiceModel == null) {
            throw new IllegalArgumentException("Boat not edited (service error)");
        }
        return super.redirect("/boats/details/" + id);
    }
}
