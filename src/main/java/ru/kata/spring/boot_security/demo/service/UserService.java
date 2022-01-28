package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> index();
    User show(int id);
    void save(User user);
    void update(int id, User updateUser);
    void delete(int id);
}
