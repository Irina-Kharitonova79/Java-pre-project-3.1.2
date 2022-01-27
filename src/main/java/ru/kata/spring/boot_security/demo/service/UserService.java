package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public List<User> index() {
        return userDAO.index();
    }

    public User show(int id) {
       return userDAO.show(id);
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public void update(int id, User updateUser) {
        userDAO.update(id, updateUser);
    }

    public void delete(int id) {
        userDAO.delete(id);
    }

}
