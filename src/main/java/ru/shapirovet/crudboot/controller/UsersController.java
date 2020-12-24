package ru.shapirovet.crudboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shapirovet.crudboot.model.Role;
import ru.shapirovet.crudboot.model.User;
import ru.shapirovet.crudboot.service.RoleService;
import ru.shapirovet.crudboot.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String printUsers(Model model) {
        List<User> list = userService.listUsers();
        model.addAttribute("usersList", list);
        return "users";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    @GetMapping("/new")
    public String showFormAddUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRole", roleService.listRole());
        return "new";
    }

    @PostMapping
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(value = "rolesId", required = false) int[] roles) {
        System.out.println("USER in POST-Mapping = " + user);
        userService.addUser(user, roles);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String showFormUpdateUser(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", user.getRoles());
        model.addAttribute("listRole", roleService.listRole());
        List<Long> arrIdRoles = new ArrayList<>();
        Set<Role> allRoles = user.getRoles();
        for (Role role: allRoles) {
            arrIdRoles.add(role.getId());
        }
        model.addAttribute("arrIdRoles", arrIdRoles);

        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(value = "rolesId", required = false) int[] roles) {
//        if (roles != null) {
//            userService.updateUser(id, user, roles);
//        } else {
////            int[] arrRoles = null;
//            userService.updateUser(id, user, null);
//        }
//        userService.updateUser(id, user);
        userService.updateUser(id, user, roles);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
