package boats.web.controllers;

import boats.domain.models.view.BoatHomeViewModel;
import boats.service.interfaces.BoatService;
import boats.service.interfaces.CharterService;
import boats.utils.ChartHomeView;
import boats.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final CharterService charterService;
    private final ChartHomeView chartHomeView;
    private final ModelMapper modelMapper;
    private final BoatService boatService;

    @Autowired
    public HomeController(CharterService charterService, ChartHomeView chartHomeView, ModelMapper modelMapper, BoatService boatService) {
        this.charterService = charterService;
        this.chartHomeView = chartHomeView;
        this.modelMapper = modelMapper;
        this.boatService = boatService;
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Welcome")
    public ModelAndView index() {
        return super.view("index");
    }


    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public ModelAndView home(ModelAndView modelAndView){

        modelAndView.addObject("charters", this.chartHomeView
                .activeCharters(this.charterService.findActiveCharters()));


        modelAndView.addObject("boatsToCheck", this.boatService.findBoatsNeedToCheck()
        .stream()
        .map(b -> this.modelMapper.map(b, BoatHomeViewModel.class))
        .collect(Collectors.toList()));

        return super.view("home", modelAndView);
    }
}
