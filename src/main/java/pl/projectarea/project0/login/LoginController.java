package pl.projectarea.project0.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.projectarea.project0.user.User;
import pl.projectarea.project0.user.UserService;

@Controller
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.saveUser(userForm);
        //userService.login(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String login( ){
      /*  if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        */
        return "login";
    }

}
