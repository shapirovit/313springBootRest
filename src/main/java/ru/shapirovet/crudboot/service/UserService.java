package ru.shapirovet.crudboot.service;

import ru.shapirovet.crudboot.model.User;

import java.util.List;

public interface UserService {
    List<User> listUsers();

    User getUserById(Long id);

    void addUser(User user, int[] roles);

    void updateUser(Long id, User user, int[] roles);

    void deleteUser(Long id);

    User getUserByLogin(String login);
}
