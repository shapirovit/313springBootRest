package ru.shapirovet.crudboot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.shapirovet.crudboot.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("from Role r join fetch r.users where r.role = :role")
    public Role findRoleByRole(@Param("role") String role);

    @Query("select distinct r from Role r join fetch r.users")
    public List<Role> listRole();

    @Query("from Role r join fetch r.users where r.id = :id")
    public Optional<Role> findById(Long id);
}
