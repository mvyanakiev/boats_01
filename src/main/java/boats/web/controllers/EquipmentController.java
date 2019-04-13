package boats.web.controllers;

import boats.config.ConfigValues;
import boats.domain.entities.Boat;
import boats.domain.models.binding.EquipmentAddBindingModel;
import boats.domain.models.binding.EquipmentEditBindingModel;
import boats.domain.models.serviceModels.EquipmentServiceModel;
import boats.domain.models.view.BoatListViewModel;
import boats.domain.models.view.EquipmentViewModel;
import boats.error.NotFoundExceptions;
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
@RequestMapping("/equipment")
public class EquipmentController extends BaseController {

    private final EquipmentService equipmentService;
    private final ModelMapper modelMapper;
    private final BoatService boatService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService, ModelMapper modelMapper, BoatService boatService) {
        this.equipmentService = equipmentService;
        this.modelMapper = modelMapper;
        this.boatService = boatService;
    }


    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All equipment")
    public ModelAndView showAllEquipment(ModelAndView modelAndView) {
        modelAndView.addObject("equipments", this.equipmentService.findAllEquipment()
                .stream()
                .map(b -> this.modelMapper.map(b, EquipmentViewModel.class))
                .collect(Collectors.toList()));
        return super.view("/equipment/equipment-all", modelAndView);
    }


    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add equipment")
    public ModelAndView addEquipment(
            ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            EquipmentAddBindingModel bindingModel,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            if (ConfigValues.THROW_EXCEPTION_FOR_INVALID_DATA_IN_CONTROLLER) {
                throw new IllegalArgumentException("Equipment not added! (invalid data)");
            }
            return super.redirect("/equipment/add");
        }

        modelAndView.addObject("bindingModel", bindingModel);

        modelAndView.addObject("boats", findAllBoatsForDropDown());

        return super.view("/equipment/equipment-add", modelAndView);
    }


    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") EquipmentAddBindingModel bindingAddModel,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            if (ConfigValues.THROW_EXCEPTION_FOR_INVALID_DATA_IN_CONTROLLER) {
                throw new IllegalArgumentException("Equipment not added! (invalid data)");
            }
            return super.redirect("/equipment/add");
        }

        EquipmentServiceModel equipmentServiceModel = this.modelMapper.map(bindingAddModel, EquipmentServiceModel.class);

        if (bindingAddModel.getBoatId() != null) {
            equipmentServiceModel.setBoat(findBoatIfExist(bindingAddModel.getBoatId()));
        } else {
            throw new NotFoundExceptions("Boat not found!");
        }

        equipmentServiceModel = this.equipmentService.addEquipment(equipmentServiceModel);

        if (equipmentServiceModel == null) {
            throw new IllegalArgumentException("Equipment not added! (service error)");
        }
        return super.redirect("/equipment/show");
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView saveEditedEquipment(@PathVariable("id") String id,
                                       @Valid @ModelAttribute(name = "bindingModel") EquipmentEditBindingModel bindingModel,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            if (ConfigValues.THROW_EXCEPTION_FOR_INVALID_DATA_IN_CONTROLLER) {
                throw new IllegalArgumentException("Equipment not edited! (invalid data)");
            }
            return super.redirect("/equipment/edit/" + id);
        }
        EquipmentServiceModel equipmentServiceModel = this.modelMapper.map(bindingModel, EquipmentServiceModel.class);

        if (bindingModel.getBoatId() != null) {
            equipmentServiceModel.setBoat(findBoatIfExist(bindingModel.getBoatId()));
        } else {
            throw new NotFoundExceptions("Boat not found!");
        }

        this.equipmentService.editEquipment(equipmentServiceModel);

        if (equipmentServiceModel == null) {
            throw new IllegalArgumentException("Equipment not edited (service error)");
        }
        return super.redirect("/equipment/show");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit equipment")
    public ModelAndView equipmentEditView(@PathVariable("id") String id, ModelAndView modelAndView, EquipmentEditBindingModel model) {
        model = this.modelMapper.map(this.equipmentService.findEquipmentById(id), EquipmentEditBindingModel.class);
        modelAndView.addObject("model", model);

        modelAndView.addObject("boats", findAllBoatsForDropDown());

        return super.view("/equipment/equipment-edit", modelAndView);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteEquipment(@PathVariable("id") String charterId) {

        this.equipmentService.deleteEquipment(charterId);

        return super.redirect("/equipment/show");
    }

    private List<BoatListViewModel> findAllBoatsForDropDown() {

        return this.boatService.findAllBoats()
                .stream()
                .map(x -> this.modelMapper
                        .map(x, BoatListViewModel.class))
                .collect(Collectors.toList());
    }

    private Boat findBoatIfExist(String boatId){
        try {
            return this.modelMapper.map(this.boatService.findBoatById(boatId), Boat.class);
        } catch (Exception e) {
            return null;
        }
    }
}
