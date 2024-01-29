package ru.rtstudy.educplatformsecurity.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.rtstudy.educplatformsecurity.model.constant.CreateUpdateTime;
import ru.rtstudy.educplatformsecurity.model.constant.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ToString.Include
    @Column(name = "first_name")
    private String firstName;

    @ToString.Include
    @Column(name = "last_name")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserCourse> userCourses = new HashSet<>();

    @Embedded
    private CreateUpdateTime time;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Grade> usersGrades = new HashSet<>();

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private Set<Grade> mentorGrades = new HashSet<>();

    @OneToMany(mappedBy = "courseAuthor", cascade = CascadeType.ALL)
    private Set<Course> courseAuthor = new HashSet<>();

    @ToString.Include(name = "password")
    private String maskPassword() {
        return "********";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
