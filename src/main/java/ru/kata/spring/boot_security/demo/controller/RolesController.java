package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

@Controller
@RequestMapping()
public class RolesController {

    private RoleService roleService;

    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }
//
//    @GetMapping("/admin")
//    public String indexRole(Model model) {
//        model.addAttribute("roles", roleService.index());
//        return "admin/new";
//    }

//    @GetMapping("/admin/new")
//    public String newRole(@ModelAttribute("role") Role role){
//        return "admin/new";
//    }
}
