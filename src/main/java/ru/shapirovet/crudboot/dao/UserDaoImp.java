package ru.shapirovet.crudboot.dao;

import org.springframework.stereotype.Repository;
import ru.shapirovet.crudboot.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

//    @Autowired
//    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> listUsers() {
        Query query = entityManager.createQuery("select distinct u from User u join fetch u.roles", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager
                .createQuery("from User u join fetch u.roles where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public User getUserByLogin(String login) {
        return entityManager
                .createQuery("from User u join fetch u.roles where u.login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
    }
}
