package boats.web.controllers;


import boats.domain.models.view.DirectionViewModel;
import boats.domain.models.view.PeopleViewModel;
import boats.service.DirectionsService;
import boats.service.PeopleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
