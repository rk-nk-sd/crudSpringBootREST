package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.repository.UserRepository;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8088")
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
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


    //=============Role=============

    @GetMapping("/admin/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRole());
    }

    @GetMapping("/admin/roles/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roleService.getCurrentRole(id));
    }

    @GetMapping("/admin/roles/new-role")
    public ResponseEntity<Role> createRoleRequest(@ModelAttribute("role") Role role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }


////    Разобраться
//    @GetMapping("/admin/roles/{id}/edit")
//    public ResponseEntity<Role> editRole (@PathVariable("id") Long id){
//        return ResponseEntity.ok(roleService.getCurrentRole(id));
//    }

    @PostMapping("/admin/roles")
    public ResponseEntity<?> createUser (@RequestBody Role _role){
        Role role = new Role();

        role.setName(_role.getName());
        roleService.createRole(role);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @PatchMapping("/admin/roles/{id}")
//    public ResponseEntity<Role> update (@ModelAttribute("role") @Valid Role role){
//        return ResponseEntity.ok(roleService.update(role));
//    }

    @DeleteMapping("/admin/roles/{id}")
    public ResponseEntity delete ( @PathVariable("id") Long id) {
        roleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    //=============User=============

    @GetMapping("/users/whoAmI")
    public ResponseEntity<Authentication> whoAmI(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }

    @GetMapping("/admin/users/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<User> getCurrentUser(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getCurrentUser(id));
    }

    @PostMapping("/users/get/by/email")
        public ResponseEntity<User> getUserByEmail(@RequestBody User user) {
        return ResponseEntity.ok(userService.findByUserEmail(user.getEmail()));
        }

    @PostMapping("/admin/users/create/user")
    public ResponseEntity<?> createUser (@RequestBody User _user) {
        User user = new User();

        user.setFirstName(_user.getFirstName());
        user.setLastName(_user.getLastName());
        user.setAge(_user.getAge());
        user.setEmail(_user.getEmail());
        user.setPassword(passwordEncoder.encode(_user.getPassword()));
        user.setRoles(_user.getRoles());

        userService.addNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/admin/users/{id}/edit")
    public ResponseEntity<User> update (@PathVariable("id") int id, @RequestBody User _user ){ // @PathVariable("id") int id,
        User user = userService.getCurrentUser(id);

        if(!_user.getFirstName().isEmpty()) {
            user.setFirstName(_user.getFirstName());
        }
        if(!_user.getLastName().isEmpty()) {
            user.setLastName(_user.getLastName());
        }
        if(_user.getAge()>0 && _user.getAge()<110) {
            user.setAge(_user.getAge());
        }
        // по хорошему нужно сделать проверку на валидность email
        if(!_user.getEmail().isEmpty()) {
            user.setEmail(_user.getEmail());
        }
        if(!_user.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(_user.getPassword()));
        }
        if(!_user.getRoles().isEmpty()) {
            user.setRoles(_user.getRoles());
        }

        return ResponseEntity.ok(userService.update(user));
    }


    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity delete ( @PathVariable("id") int id){
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
