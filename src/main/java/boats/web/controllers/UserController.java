package boats.web.controllers;

import boats.domain.models.binding.UserEditBindingModel;
import boats.domain.models.binding.UserRegisterBindingModel;
import boats.domain.models.serviceModels.UserServiceModel;
import boats.domain.models.view.UserAllViewModel;
import boats.domain.models.view.UserProfileViewModel;
import boats.service.interfaces.UserService;

import boats.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Register")
    public ModelAndView register() {
        return super.view("register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view("register");
        }

        this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));

        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login")
    public ModelAndView login(){
        return super.view("login");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Profile")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
        modelAndView
                .addObject("model", this.modelMapper
                        .map(this.userService.findByUsername(principal.getName()), UserProfileViewModel.class));

        return super.view("profile", modelAndView);
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
        modelAndView
                .addObject("model", this.modelMapper
                        .map(this.userService
                                .findByUsername(principal.getName()), UserProfileViewModel.class));

        return super.view("edit-profile", modelAndView);
    }

    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit")
    public ModelAndView editProfileConfirm(@ModelAttribute UserEditBindingModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view("edit-profile");
        }

        this.userService.editUserProfile(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());

        return super.redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All users")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        List<UserAllViewModel> users = this.userService.findAllUsers()
                .stream()
                .map(u -> {
                    UserAllViewModel user = this.modelMapper.map(u, UserAllViewModel.class);
                    user.setAuthorities(u.getAuthorities().stream()
                            .map(a -> a.getAuthority()).collect(Collectors.toSet()));
                    return user;
                }).collect(Collectors.toList());


        modelAndView.addObject("users", users);
        return super.view("all-users", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id){
        this.userService.setUserRole(id, "user");

        return super.redirect("/users/all");
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id){
        this.userService.setUserRole(id, "moderator");

        return super.redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id){
        this.userService.setUserRole(id, "admin");

        return super.redirect("/users/all");
    }
}