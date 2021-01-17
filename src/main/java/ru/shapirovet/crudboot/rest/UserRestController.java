package ru.shapirovet.crudboot.rest;

import org.springframework.web.bind.annotation.*;
import ru.shapirovet.crudboot.model.Role;
import ru.shapirovet.crudboot.model.User;
import ru.shapirovet.crudboot.rest.dto.UserDto;
import ru.shapirovet.crudboot.service.RoleService;
import ru.shapirovet.crudboot.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;

    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/api/users")
    public List<UserDto> getAllUsers() {
        return userService.listUsers().stream().map(UserDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/users/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        return UserDto.toDto(userService.getUserById(id));
    }

    @PostMapping("/api/users")
    public UserDto addUser(@RequestBody UserDto newUserDto) {
        Set<Role> roles = roleService.listRole().stream()
                .filter(role -> newUserDto.getRoles().contains(role.getRole().replace("ROLE_", "")))
                .collect(Collectors.toSet());
        User newUser = UserDto.fromDto(newUserDto, roles);
        userService.addUser(newUser);
        return UserDto.toDto(userService.getUserByLogin(newUser.getEmail()));
    }

    @PutMapping("/api/users/{id}")
    public UserDto editUser(@RequestBody UserDto editUserDto,
                            @PathVariable("id") Long id) {
        Set<Role> roles = roleService.listRole().stream()
                .filter(role -> editUserDto.getRoles().contains(role.getRole().replace("ROLE_", "")))
                .collect(Collectors.toSet());
        User user = UserDto.fromDto(editUserDto, roles);
        userService.updateUser(id, user);
//        userService.updateUser(id, UserDto.fromDto(editUserDto, roles, editUser));
        return UserDto.toDto(userService.getUserById(id));
    }

    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}