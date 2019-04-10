package boats.web.controllers;


import boats.config.ConfigValues;
import boats.domain.models.binding.DirectionAddBindingModel;
import boats.domain.models.binding.DirectionEditBindingModel;
import boats.domain.models.binding.PeopleAddBindingModel;
import boats.domain.models.serviceModels.DirectionServiceModel;
import boats.domain.models.view.DirectionViewModel;
import boats.service.interfaces.DirectionsService;
import boats.web.annotations.PageTitle;
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
@RequestMapping("/directions")
public class DirectionController extends BaseController {

    private final DirectionsService directionsService;
    private final ModelMapper modelMapper;

    @Autowired
    public DirectionController(DirectionsService directionsService, ModelMapper modelMapper) {
        this.directionsService = directionsService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All directions")
    public ModelAndView showAllDirections(ModelAndView modelAndView) {
        modelAndView.addObject("directions", this.directionsService.findAllDirections()
                .stream()
                .map(b -> this.modelMapper.map(b, DirectionViewModel.class))
                .collect(Collectors.toList()));

        return super.view("/directions/directions-all", modelAndView);
    }


    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add direction")
    public ModelAndView addDirection(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            DirectionAddBindingModel bindingModel) {

        modelAndView.addObject("bindingModel", bindingModel);

        return super.view("/directions/direction-add", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") DirectionAddBindingModel bindingModel,
                                   BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {

            if (ConfigValues.THROW_EXCEPTION_FOR_INVALID_DATA_IN_CONTROLLER) {
                throw new IllegalArgumentException("Direction not added! (invalid data)");
            }
            return super.redirect("/directions/add");
        }

        DirectionServiceModel directionServiceModel = this.modelMapper.map(bindingModel, DirectionServiceModel.class);

        directionServiceModel = this.directionsService.addDirection(directionServiceModel);

        if (directionServiceModel == null) {
            throw new IllegalArgumentException("Direction not added! (service error)");
        }

        return super.redirect("/directions/show");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit direction")
    public ModelAndView boatEditView(@PathVariable("id") String id, ModelAndView modelAndView, DirectionEditBindingModel model) {
        model = this.modelMapper.map(this.directionsService.findDirectionById(id), DirectionEditBindingModel.class);
        modelAndView.addObject("model", model);

        return super.view("/directions/direction-edit", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView saveEditedBoat(@PathVariable("id") String id,
                                       @Valid @ModelAttribute(name = "bindingModel")
                                               DirectionEditBindingModel bindingModel,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            if (ConfigValues.THROW_EXCEPTION_FOR_INVALID_DATA_IN_CONTROLLER) {
                throw new IllegalArgumentException("Direction not edited! (invalid data)");
            }
            return super.redirect("/directions/edit/" + id);
        }

        DirectionServiceModel directionServiceModel = this.modelMapper.map(bindingModel, DirectionServiceModel.class);
        directionServiceModel.setId(id);
        directionServiceModel = this.directionsService.editDirection(directionServiceModel);

        if (directionServiceModel == null) {
            throw new IllegalArgumentException("Direction not edited (service error)");
        }
        return super.redirect("/directions/show");
    }
}
