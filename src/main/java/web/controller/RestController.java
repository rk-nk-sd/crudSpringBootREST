package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
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

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/admin")
public class RestController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RestController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

//    @GetMapping()
//    public ResponseEntity<List<User>> redirect() {
//
//        return ResponseEntity.ok(userService.getAllUsers());
//
////        return "Hello!";
//    }

    //=============Role=============

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRole());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roleService.getCurrentRole(id));
    }

    @GetMapping("/roles/new-role")
    public ResponseEntity<Role> createRoleForm(@ModelAttribute("role") Role role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }


//    Разобраться
    @GetMapping("/roles/{id}/edit")
    public ResponseEntity<Role> editRole (@PathVariable("id") Long id){
        return ResponseEntity.ok(roleService.getCurrentRole(id));
    }

    @PostMapping("/roles")
    public String createUser (@ModelAttribute("role") @Valid Role role,
                              BindingResult bindingResult,
                              @RequestParam(name = "rolename") String rolename){
        if(bindingResult.hasErrors())
            return "admin/create_role";

        role.setName(rolename);
        roleService.createRole(role);

        return "redirect:/admin/roles";
    }

    @PatchMapping("/roles/{id}")
    public ResponseEntity<Role> update (@ModelAttribute("role") @Valid Role role){
        return ResponseEntity.ok(roleService.update(role));
    }

//    Разобраться
    @DeleteMapping("/roles/{id}")
    public ResponseEntity delete ( @PathVariable("id") Long id){
        roleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    //=============User=============
    @GetMapping("/users/whoami")
    public ResponseEntity<Authentication> whoAmI(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getCurrentUser(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getCurrentUser(id));
    }

    //перенес
//    @GetMapping("/users/new-user")
//    public String createUserForm(@ModelAttribute("user") User user, Model model, Authentication authentication) {
//        //Вернет html форму для создания нового пользователя
//        model.addAttribute("userinfo", authentication);
//        return "admin/create_user";
//    }

    @GetMapping("/users/{id}/edit")
    public String editUser (Model model,@PathVariable("id") int id, Authentication authentication){
        //Вернет html форму для редактирования страницы пользователя
        model.addAttribute("user", userService.getCurrentUser(id));
        model.addAttribute("roles", roleService.getAllRole());
        model.addAttribute("userinfo", authentication);
        return "admin/edit_user";
    }

    @PostMapping("/users")
    public String createUser (@ModelAttribute("user") @Valid User user,
                              BindingResult bindingResult,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "lastname") String lastname,
                              @RequestParam(name = "age") String age,
                              @RequestParam(name = "email") String email,
                              @RequestParam(name = "password") String password,
                              @RequestParam(name = "addrole") String[] role){
        if(bindingResult.hasErrors())
            return "admin/create_user";

        Set<Role> roles = new HashSet<>();
//        roles.add(userService.findByRoleName("ROLE_USER"));
        for (String variable : role) {
            roles.add(userService.findByRoleName(variable));
        }

        user.setFirstName(name);
        user.setLastName(lastname);
        user.setAge(Integer.valueOf(age).intValue());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);

        userService.addNewUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/users/{id}")
    public String update (@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          Model model,
                          @PathVariable("id") int id,
                          @RequestParam(name = "firstname") String firstname,
                          @RequestParam(name = "lastname") String lastname,
                          @RequestParam(name = "age") String age,
                          @RequestParam(name = "email") String email,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "addrole") String[] role){
        //Обновляет пользователя
        if(bindingResult.hasErrors())
            return "admin/index";

        Set<Role> roles = new HashSet<>();
//        Set<Role> roles = user.getRoles();
        for (String variable : role) {
            // некоторые операторы
            // ...
            roles.add(userService.findByRoleName(variable));
        }
//        roles.add(userService.findByRoleName(role));
//        roles.add(userService.findByRoleName("ROLE_USER"));

        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setAge(Integer.valueOf(age).intValue());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);

        userService.update(user);
        return "redirect:/admin";
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity delete ( @PathVariable("id") int id){
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
