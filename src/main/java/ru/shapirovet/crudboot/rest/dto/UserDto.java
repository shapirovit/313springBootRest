package ru.shapirovet.crudboot.rest.dto;

import ru.shapirovet.crudboot.model.Role;
import ru.shapirovet.crudboot.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDto {
    private Long id = -1L;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private List<String> roles;

    public UserDto() {
    }

    public UserDto(Long id, String email, String password, String firstName, String lastName, int age, List<String> roles) {

        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public static UserDto toDto(User user) {

        return new UserDto(
                user.getId(),
                user.getEmail(),
                "",
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getRoles().stream().map(role -> role.getRole().replace("ROLE_", "")).collect(Collectors.toList()));
//                user.getRoles().stream().map(role -> role.getRole().replace("ROLE_", "")).reduce((r1, r2) -> r1 + " " + r2).get());
    }

    public static User fromDto(UserDto userDto, Set<Role> roles) {
        return new User(userDto.getEmail(), userDto.getPassword(), userDto.getFirstName(), userDto.getLastName(), userDto.getAge(), roles);
    }
}