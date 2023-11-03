package org.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

// fixme Entity -_-

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

@Data
@Entity
@Table(name = "users", schema = "my_bd", catalog = "")
@Accessors(chain = true)
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String email;
    private String password;

    @Transient private String confirmPassword;

    // fixme set ?
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    private String name, surname, middleName, phone;

    private String img;
    private List<String> files;

    @ManyToMany
    private List<Branch> branches;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<UserReview> reviews;
}