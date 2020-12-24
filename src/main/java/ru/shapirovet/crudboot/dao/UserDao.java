package ru.shapirovet.crudboot.dao;

import ru.shapirovet.crudboot.model.User;

import java.util.List;

public interface UserDao {
    List<User> listUsers();

    User getUserById(Long id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    User getUserByLogin(String login);
}
