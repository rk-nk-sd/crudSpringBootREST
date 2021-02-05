package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
//@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping()
    public String redirect(Model model, Authentication authentication) {
        model.addAttribute("userinfo", authentication);
        return "admin/index";
    }

    //=============Role=============

//    @GetMapping("/roles")
//    public String getAllRoles(Model model, Principal principal) {
//        //Получим список пользователей и передадим в представление
//        model.addAttribute("roles", roleService.getAllRole());
//        model.addAttribute("userinfo", principal);
//        return "admin/all_roles";
//    }
//
//    @GetMapping("/roles/{id}")
//    public String getRole(@PathVariable("id") Long id, Model model, Principal principal) {
//        //Получим одного пользователя по id и передадим на представление
//        model.addAttribute("role", roleService.getCurrentRole(id));
//        model.addAttribute("userinfo", principal);
//        return "admin/info_role";
//    }

//    @GetMapping("/roles/new-role")
//    public String createRoleForm(@ModelAttribute("role") Role role, Model model, Principal principal) {
//        //Вернет html форму для создания нового пользователя
//        model.addAttribute("userinfo", principal);
//        return "admin/create_role";
//    }
//
//    @GetMapping("/roles/{id}/edit")
//    public String editRole (Model model,@PathVariable("id") Long id, Principal principal){
//        //Вернет html форму для редактирования страницы пользователя
//        model.addAttribute("role", roleService.getCurrentRole(id));
//        model.addAttribute("userinfo", principal);
//        return "admin/edit_role";
//    }
//
//    @PostMapping("/roles")
//    public String createUser (@ModelAttribute("role") @Valid Role role,
//                              BindingResult bindingResult,
//                              @RequestParam(name = "rolename") String rolename){
//        if(bindingResult.hasErrors())
//            return "admin/create_role";
//
//        role.setName(rolename);
//        roleService.createRole(role);
//
//        return "redirect:/admin/roles";
//    }
//
//    @PatchMapping("/roles/{id}")
//    public String update (@ModelAttribute("role") @Valid Role role,
//                          BindingResult bindingResult,
//                          @PathVariable("id") int id,
//                          @RequestParam(name = "rolename") String rolename){
//        //Обновляет роль
//        if(bindingResult.hasErrors())
//            return "admin/edit_role";
//
//        role.setName(rolename);
//        roleService.update(role);
//
//        return "redirect:/admin/roles";
//    }
//
//
//    @DeleteMapping("/roles/{id}")
//    public String delete ( @PathVariable("id") Long id){
//        //Удаляет пользователя
//        roleService.delete(id);
//        return "redirect:/admin/roles";
//    }

    //=============User=============

//    @GetMapping("/users")
//    public String getAllUsers(@ModelAttribute("user") User user, Model model, Authentication authentication) {
//        //Получим список пользователей и передадим в представление
//        model.addAttribute("users", userService.getAllUsers());
//        model.addAttribute("roles", roleService.getAllRole());
//        model.addAttribute("userinfo", authentication);
//        return "admin/index";
//    }
//
//    @GetMapping("/users/{id}")
//    public String getCurrentUser(@PathVariable("id") int id, Model model, Authentication authentication) {
//        //Получим одного пользователя по id и передадим на представление
//        model.addAttribute("user", userService.getCurrentUser(id));
//        model.addAttribute("userinfo", authentication);
//        return "admin/info_user";
//    }

    //перенес
//    @GetMapping("/users/new-user")
//    public String createUserForm(@ModelAttribute("user") User user, Model model, Authentication authentication) {
//        //Вернет html форму для создания нового пользователя
//        model.addAttribute("userinfo", authentication);
//        return "admin/create_user";
//    }

//    @GetMapping("/users/{id}/edit")
//    public String editUser (Model model,@PathVariable("id") int id, Authentication authentication){
//        //Вернет html форму для редактирования страницы пользователя
//        model.addAttribute("user", userService.getCurrentUser(id));
//        model.addAttribute("roles", roleService.getAllRole());
//        model.addAttribute("userinfo", authentication);
//        return "admin/edit_user";
//    }
//
//    @PostMapping("/users")
//    public String createUser (@ModelAttribute("user") @Valid User user,
//                              BindingResult bindingResult,
//                              @RequestParam(name = "name") String name,
//                              @RequestParam(name = "lastname") String lastname,
//                              @RequestParam(name = "age") String age,
//                              @RequestParam(name = "email") String email,
//                              @RequestParam(name = "password") String password,
//                              @RequestParam(name = "addrole") String[] role){
//        if(bindingResult.hasErrors())
//            return "admin/create_user";
//
//        Set<Role> roles = new HashSet<>();
////        roles.add(userService.findByRoleName("ROLE_USER"));
//        for (String variable : role) {
//            roles.add(userService.findByRoleName(variable));
//        }
//
//        user.setFirstName(name);
//        user.setLastName(lastname);
//        user.setAge(Integer.valueOf(age).intValue());
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRoles(roles);
//
//        userService.addNewUser(user);
//        return "redirect:/admin";
//    }
//
//    @PatchMapping("/users/{id}")
//    public String update (@ModelAttribute("user") @Valid User user,
//                          BindingResult bindingResult,
//                          Model model,
//                          @PathVariable("id") int id,
//                          @RequestParam(name = "firstname") String firstname,
//                          @RequestParam(name = "lastname") String lastname,
//                          @RequestParam(name = "age") String age,
//                          @RequestParam(name = "email") String email,
//                          @RequestParam(name = "password") String password,
//                          @RequestParam(name = "addrole") String[] role){
//        //Обновляет пользователя
//        if(bindingResult.hasErrors())
//            return "admin/index";
//
//        Set<Role> roles = new HashSet<>();
////        Set<Role> roles = user.getRoles();
//        for (String variable : role) {
//            // некоторые операторы
//            // ...
//            roles.add(userService.findByRoleName(variable));
//        }
////        roles.add(userService.findByRoleName(role));
////        roles.add(userService.findByRoleName("ROLE_USER"));
//
//        user.setFirstName(firstname);
//        user.setLastName(lastname);
//        user.setAge(Integer.valueOf(age).intValue());
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRoles(roles);
//
//        userService.update(user);
//        return "redirect:/admin";
//    }
//
//
//    @DeleteMapping("/users/{id}")
//    public String delete ( @PathVariable("id") int id){
//        //Удаляет пользователя
//        userService.delete(id);
//        return "redirect:/admin";
//    }

}
