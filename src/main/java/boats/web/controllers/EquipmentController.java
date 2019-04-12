package boats.web.controllers;

import boats.config.ConfigValues;
import boats.domain.entities.Boat;
import boats.domain.models.binding.EquipmentAddBindingModel;
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

        List<BoatListViewModel> boats = this.boatService.findAllBoats()
                .stream()
                .map(x -> this.modelMapper
                        .map(x, BoatListViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("boats", boats);

        return super.view("/equipment/equipment-add", modelAndView);
    }


    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") EquipmentAddBindingModel bindingModel,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            if (ConfigValues.THROW_EXCEPTION_FOR_INVALID_DATA_IN_CONTROLLER) {
                throw new IllegalArgumentException("Equipment not added! (invalid data)");
            }
            return super.redirect("/equipment/add");
        }

        Boat boat = null;
        EquipmentServiceModel equipmentServiceModel = this.modelMapper.map(bindingModel, EquipmentServiceModel.class);


        try {
            boat = this.modelMapper.map(this.boatService.findBoatById(bindingModel.getBoatId()), Boat.class);
            equipmentServiceModel.setBoat(boat);
        } catch (Exception e) {
            throw new NotFoundExceptions("Boat not found!");
        }


        this.equipmentService.addEquipment(equipmentServiceModel);


        if (equipmentServiceModel == null) {
            throw new IllegalArgumentException("Equipment not added! (service error)");
        }

        return super.redirect("/equipment/show");
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCharter(@PathVariable("id") String charterId) {

        this.equipmentService.deleteEquipment(charterId);

        return super.redirect("/equipment/show");
    }
}
