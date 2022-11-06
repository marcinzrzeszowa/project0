package pl.projectarea.project0.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.projectarea.project0.pricealert.PriceAlert;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
/*
    @GetMapping("/user/register")
    public String registerUser(){
            return "register";
    }*/

    @GetMapping("/users")
    public String showUsers(Model model){
        List<User> usersList= userService.getUsers();
        if(!usersList.isEmpty()){
            model.addAttribute("usersList",usersList );
            return "users";
        }
        return "error/403";
    }

    @GetMapping("/user/{id}")
    public String showUser(Model model, @PathVariable Long id){
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
        }else {
            //return "error/404";
        }
        return "user";
    }

    @PostMapping("/user/{id}")
    public String editUser(@ModelAttribute("user")User user,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            //return "error/404";
        }
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteAlert(@PathVariable("id") Long id){
        Long InitAdminId = Long.valueOf(1);
        if(id != InitAdminId){
            userService.deleteUser(id);
        }
        return "redirect:/users";
    }


}
