package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    void delete(Long id);
    Role getCurrentRole(Long id);
    Role update(Role role);
    List<Role> getAllRole();
//    Role findByRoleName(String role);
}
