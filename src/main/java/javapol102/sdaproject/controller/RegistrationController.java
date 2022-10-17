package javapol102.sdaproject.controller;

import javapol102.sdaproject.exception.EmptyUsernameException;
import javapol102.sdaproject.model.User;
import javapol102.sdaproject.service.AutologinService;
import javapol102.sdaproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RegistrationController {

    private final UserService userService;

    private final AutologinService autologinService;

    public RegistrationController(UserService userService, AutologinService autologinService) {
        this.userService = userService;
        this.autologinService = autologinService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(ModelMap modelMap) {
        modelMap.addAttribute("emptyUser", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String handleNewUser(@ModelAttribute("emptyUser") User user, ModelMap modelMap) {

        log.info("Received new user: " + user.getUsername());

        try {
            if (userService.existsByUsername(user.getUsername())) {//czy istnieje w bazie danych użytkownik o takim username
                log.info("User with username " + user.getUsername() + " exists!");
                modelMap.addAttribute("exceptionMessage", "User with username " + user.getUsername() + " exists!");
                return "registration";
            }
        } catch (EmptyUsernameException e) {
            log.info(e.getMessage());
            modelMap.addAttribute("exceptionMessage", e.getMessage());
            return "registration";
        }

        userService.save(user);

        autologinService.autoLogin(user.getUsername());

        return "redirect:/books/list";
    }

}
