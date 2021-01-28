package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String profile(Model model, Principal principal) {
        //Получим список пользователей и передадим в представление
        String massage = "Личная информация...";
        model.addAttribute("msg", massage);
        model.addAttribute("userinfo", principal);
        return "users/index";
    }
}