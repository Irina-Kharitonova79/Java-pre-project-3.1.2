package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping()
public class MyRESTController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MyRESTController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/rest_admin")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> allUsers = userService.index();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/rest_admin/currentUser")
    public ResponseEntity<User> showOneUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/rest_admin/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        User user = userService.show(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/rest_admin")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/rest_admin/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user,
                                                      @PathVariable("id") int id) {
        userService.update(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/rest_admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/rest_admin/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> listRoles = roleService.index();
        return ResponseEntity.ok(listRoles);
    }
}
