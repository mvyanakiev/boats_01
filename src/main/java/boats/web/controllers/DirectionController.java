package boats.web.controllers;


import boats.domain.models.view.DirectionViewModel;
import boats.service.DirectionsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView showAllDirections(ModelAndView modelAndView) {
        modelAndView.addObject("directions", this.directionsService.findAllDirections()
                .stream()
                .map(b -> this.modelMapper.map(b, DirectionViewModel.class))
                .collect(Collectors.toList()));

        return super.view("/directions/all-directions", modelAndView);
    }





}
