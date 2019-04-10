package boats.web.controllers;


import boats.domain.models.binding.PeopleAddBindingModel;
import boats.domain.models.binding.PeopleEditBindingModel;
import boats.domain.models.serviceModels.PeopleServiceModel;
import boats.domain.models.view.PeopleViewModel;
import boats.service.interfaces.PeopleService;
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
@RequestMapping("/peoples")
public class PeopleController extends BaseController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All people")
    public ModelAndView showAllPeoples(ModelAndView modelAndView) {
        modelAndView.addObject("peoples", this.peopleService.findAllPeoples()
                .stream()
                .map(b -> this.modelMapper.map(b, PeopleViewModel.class))
                .collect(Collectors.toList()));

        return super.view("/peoples/peoples-all", modelAndView);
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add people")
    public ModelAndView addPeople(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            PeopleAddBindingModel bindingModel){

            modelAndView.addObject("bindingModel", bindingModel);

            return super.view("/peoples/people-add", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") PeopleAddBindingModel bindingModel,
                                   BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return super.redirect("/peoples/add");
//            throw new IllegalArgumentException("People not added! (invalid data)");
        }

        PeopleServiceModel peopleServiceModel = this.modelMapper.map(bindingModel, PeopleServiceModel.class);
        peopleServiceModel = this.peopleService.addPeople(peopleServiceModel);

        if (peopleServiceModel == null) {
            throw new IllegalArgumentException("People not added! (service error)");
        }

        return super.redirect("/peoples/show");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit people")
    public ModelAndView boatEditView(@PathVariable("id") String id, ModelAndView modelAndView, PeopleEditBindingModel model) {
        model = this.modelMapper.map(this.peopleService.findPeopleById(id), PeopleEditBindingModel.class);
        modelAndView.addObject("model", model);

        return super.view("/peoples/people-edit", modelAndView);
    }


    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView saveEditedBoat(@PathVariable("id") String id,
                                       @Valid @ModelAttribute(name = "bindingModel")
                                               PeopleEditBindingModel bindingModel,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
//            throw new IllegalArgumentException("Boat not edited! (invalid data)");
            return super.redirect("/peoples/edit/" + id);
        }

        PeopleServiceModel peopleServiceModel  = this.modelMapper.map(bindingModel, PeopleServiceModel.class);
        peopleServiceModel.setId(id);
        peopleServiceModel = this.peopleService.editPeople(peopleServiceModel);

        if (peopleServiceModel == null) {
            throw new IllegalArgumentException("People not edited (service error)");
        }
        return super.redirect("/peoples/show");
    }
}
