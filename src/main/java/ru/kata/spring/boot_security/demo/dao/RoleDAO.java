package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleDAO {

    List<Role> index();
    Role getRoleByName(String role);
    void saveRole(Role role);

}
