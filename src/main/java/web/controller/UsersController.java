package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.service.UserService;
import web.service.impl.UserDetailsServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String profile(Model model, Authentication authentication) {
        //Получим список пользователей и передадим в представление
            UserDetails details = userDetailsService.loadUserByUsername(authentication.getName());
            if (details != null && details.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                model.addAttribute("menu", "menuAdminProfile");
            } else {
                model.addAttribute("menu", "menuUser");
            }
        model.addAttribute("userinfo", authentication);
        model.addAttribute("user", userService.findByUserEmail(authentication.getName()));
        return "users/index";
    }
}