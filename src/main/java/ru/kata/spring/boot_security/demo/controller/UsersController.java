package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping()
public class UsersController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public UsersController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("users", userService.index());
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", roleService.index());
        return "admin/all_users";
    }

    @GetMapping("/admin/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin/showUser";
    }

    @GetMapping("/user")
    public String show(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", userService.show(user.getId()));
        return "user/show";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user,
                          @ModelAttribute("role") Role role,
                          Model model) {
        model.addAttribute("allRoles", roleService.index());
        return "admin/new";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "listRoles") String[] listRoles
    ) {
        List<Role> roles = new ArrayList<>();
        for (String role : listRoles) {

            roles.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("allRoles", roleService.index());
        model.addAttribute("user", userService.show(id));
        return "admin/edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") int id,
                         @RequestParam(value = "listRoles") String [] listRoles
    ) {
        List<Role> roles = new ArrayList<>();
        for (String role : listRoles) {
            roles.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles);
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
