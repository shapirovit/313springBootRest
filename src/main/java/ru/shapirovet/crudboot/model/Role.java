package ru.shapirovet.crudboot.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "title")
    private String title;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "users_roles",
//            //foreign key for EmployeeEntity in employee_car table
//            joinColumns = @JoinColumn(name = "role_id"),
//            //foreign key for other side - EmployeeEntity in employee_car table
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
//    private Set<User> users = new HashSet<>();

    public Role() {}

    public Role(String role, String title) {
        this.role = role;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", title='" + title + '\'' +
                ", users.size()=" + users.size() +
                '}';
    }


}
