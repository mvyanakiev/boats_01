package boats.web.controllers;


import boats.domain.models.binding.PeopleAddBindingModel;
import boats.domain.models.serviceModels.PeopleServiceModel;
import boats.domain.models.view.PeopleViewModel;
import boats.service.interfaces.PeopleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ModelAndView showAllPeoples(ModelAndView modelAndView) {
        modelAndView.addObject("peoples", this.peopleService.findAllPeoples()
                .stream()
                .map(b -> this.modelMapper.map(b, PeopleViewModel.class))
                .collect(Collectors.toList()));

        return super.view("/peoples/all-peoples", modelAndView);
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addPeople(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel")
            PeopleAddBindingModel bindingModel){

            modelAndView.addObject("bindingModel", bindingModel);

            return super.view("/peoples/add-people", modelAndView);
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
}
