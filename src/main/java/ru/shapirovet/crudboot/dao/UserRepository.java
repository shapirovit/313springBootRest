package ru.shapirovet.crudboot.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.shapirovet.crudboot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from User u join fetch u.roles where u.email = :login")
    public User findUserByLogin(@Param("login") String login);

    @Query("select distinct u from User u join fetch u.roles")
    public List<User> listUsers();

    @Query("from User u join fetch u.roles where u.id = :id")
    public Optional<User> findById(Long id);
}
