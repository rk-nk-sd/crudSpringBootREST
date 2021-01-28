package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService {
    User addNewUser(User user);
    void delete(int id);
    User getCurrentUser(int id);
    User update(User user);
    List<User> getAllUsers();

    User findByUserName(String username);

    Role findByRoleName(String role);
}
