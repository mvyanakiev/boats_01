package boats.web.controllers;

import boats.service.interfaces.CharterService;
import boats.utils.ChartHomeView;
import boats.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    private final CharterService charterService;
    private final ChartHomeView chartHomeView;

    @Autowired
    public HomeController(CharterService charterService, ChartHomeView chartHomeView) {
        this.charterService = charterService;
        this.chartHomeView = chartHomeView;
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

        return super.view("home", modelAndView);
    }
}
