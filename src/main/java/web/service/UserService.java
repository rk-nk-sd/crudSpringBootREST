package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    void delete(long id);
    User getCurrentUser(long id);
    User update(User user);
    List<User> getAllUsers();
}
