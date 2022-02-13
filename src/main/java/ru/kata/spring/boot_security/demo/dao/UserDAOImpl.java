package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDAOImpl implements UserDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public List<User> index() {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    @Override
    public User show(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByName(String name) {
        return entityManager.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setRoles(getRolesFromDb(user));
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void update(int id, User updateUser) {
        User userToBeUpdate = entityManager.find(User.class, id);
        userToBeUpdate.setName(updateUser.getName());
        userToBeUpdate.setSurname(updateUser.getSurname());
        userToBeUpdate.setAge(updateUser.getAge());
        userToBeUpdate.setEmail(updateUser.getEmail());
        userToBeUpdate.setPassword(updateUser.getPassword());
        userToBeUpdate.setRoles(getRolesFromDb(updateUser));
    }

    private List<Role> getRolesFromDb(User user) {
        return user.getRoles().stream()
                .map(r -> roleDAO.getRoleByName(r.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

}
