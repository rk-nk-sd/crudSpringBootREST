package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.repository.UserRepository;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8088")
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/admin")
public class RestController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    @Autowired
    public RestController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, UserRepository repository) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
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
    public ResponseEntity<Role> createRoleRequest(@ModelAttribute("role") Role role) {
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
    public ResponseEntity delete ( @PathVariable("id") Long id) {
        roleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    //=============User=============

    @GetMapping("/users/whoAmI")
    public ResponseEntity<Authentication> whoAmI(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }

    @GetMapping("/users/getAllUsers")
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

//    @GetMapping("/users/{id}/edit")
//    public String editUser (Model model,@PathVariable("id") int id, Authentication authentication){
//        //Вернет html форму для редактирования страницы пользователя
//        model.addAttribute("user", userService.getCurrentUser(id));
//        model.addAttribute("roles", roleService.getAllRole());
//        model.addAttribute("userinfo", authentication);
//        return "admin/edit_user";
//    }

    @PostMapping("/users")
    public User createUser (@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> update ( @PathVariable("id") int id, @RequestBody User user ){
        //возможно вариант 1
//        Optional<User> userOptional = Optional.ofNullable(userService.getCurrentUser(id));
//
//        if (!userOptional.isPresent())
//            return ResponseEntity.notFound().build();
//
//        user.setId(id);
//
//        userService.update(user);
//
//        return ResponseEntity.noContent().build();

        // возможно вариант 2
//        return userService.getCurrentUser(id)
//                .map(record -> {
//                    record.setName(contact.getName());
//                    record.setEmail(contact.getEmail());
//                    record.setPhone(contact.getPhone());
//                    Contact updated = repository.save(record);
//                    return ResponseEntity.ok().body(updated);
//                }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity delete ( @PathVariable("id") int id){
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
