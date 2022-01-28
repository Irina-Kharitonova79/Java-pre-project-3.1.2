package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDAO {

    List<User> index();
    User show(int id);
    User findUserByName(String name);
    void save(User user);
    void update(int id, User updateUser);
    void delete(int id);
}
