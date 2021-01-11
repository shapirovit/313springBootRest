package ru.shapirovet.crudboot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shapirovet.crudboot.model.User;
import ru.shapirovet.crudboot.service.RoleService;
import ru.shapirovet.crudboot.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String printUsers(@ModelAttribute("newUser") User user, Model model) {
        List<User> list = userService.listUsers();
        model.addAttribute("usersList", list);
        model.addAttribute("listRole", roleService.listRole());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userLogin = userService.getUserByLogin(username);
        model.addAttribute("userLogin", userLogin);

        boolean isUser = userLogin.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_USER"));
        boolean isAdmin = userLogin.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_ADMIN"));
        model.addAttribute("isUser", isUser);
        model.addAttribute("isAdmin", isAdmin);

        return "admin";
    }

    @GetMapping("/admin/")
    public String reAdmin() {
        return "redirect:/admin";
    }

    @GetMapping("/")
    public String getMain() {
        return "redirect:/user";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("newUser") User user,
                          @RequestParam(value = "rolesId", required = false) int[] roles) {
        System.out.println("USER in POST-Mapping = " + user);
        userService.addUser(user, roles);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(value = "rolesId", required = false) int[] roles) {
        userService.updateUser(id, user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
