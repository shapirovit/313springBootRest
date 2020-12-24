package ru.shapirovet.crudboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shapirovet.crudboot.dao.UserDao;
import ru.shapirovet.crudboot.model.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public void addUser(User user, int[] roles) {
        if (roles != null) {
            for (int roleId : roles) {
                user.addRole(roleService.getRoleById(Long.valueOf(roleId)));
            }
        }
//        System.out.println("in addUser UserServiceImp");
//        System.out.println("user = " + user);
//        Role role = roleService.getRoleByRole("ROLE_USER");
//        System.out.println("role =" + role);
//        user.addRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Transactional
    @Override
    public void updateUser(Long id, User user, int[] roles) {
        System.out.println("roles = " + roles);
//        System.out.println("roles.length = " + roles.length);
//        Set<Role> roles = new HashSet<>();
//        Role role = new Role("ROLE_USER", "Пользователь");
//        roles.add(role);
//        user.setRoles(roles);
        System.out.println("id = " + id);
        System.out.println("user " + user);
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRoles(userDao.getUserById(id).getRoles());
        if (roles != null) {
            for (int roleId : roles) {
                user.addRole(roleService.getRoleById(Long.valueOf(roleId)));
            }
        }

//        Role role = roleService.getRoleByRole("ROLE_USER");
//        user.addRole(role);

        userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        User user = userDao.getUserById(id);

        if (user != null) {
            userDao.deleteUser(user);
        }

    }

    @Transactional
    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.getUserByLogin(s);
    }
}
