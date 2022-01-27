package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class RoleDAO {

    @PersistenceContext
    EntityManager entityManager;

    public List<Role> index() {
        return entityManager.createQuery("select r from Role r").getResultList();
    }


    public Role getRoleByName(String role) {
         return entityManager.createQuery("select r from Role r where r.roleName = :role", Role.class)
                .setParameter("role", role)
                .getSingleResult();
    }
}
