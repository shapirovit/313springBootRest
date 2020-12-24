package ru.shapirovet.crudboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shapirovet.crudboot.model.User;
import ru.shapirovet.crudboot.service.UserService;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/")
    public String printUsers(Model model) {
        List<User> list = userService.listUsers();
        model.addAttribute("usersList", list);
        return "users";
    }

    @GetMapping("/admin")
    public String reAdmin() {
        return "redirect:/admin/";
    }

    @GetMapping("/")
    public String getMain() {
        return "redirect:/user";
    }
}
