package ru.shapirovet.crudboot.dao;

import ru.shapirovet.crudboot.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> listRole();

    Role getRoleById(Long id);

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(Role role);

    Role getRoleByRole(String role);
}
