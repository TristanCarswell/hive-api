package carswell.tristan.hive.hiveapi.auth.models.users;

import carswell.tristan.hive.hiveapi.auth.models.enums.Gender;
import carswell.tristan.hive.hiveapi.auth.models.enums.Role;
import carswell.tristan.hive.hiveapi.auth.models.intf.IMergeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "tbl_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails, IMergeable<User> {

    @Id
    @GeneratedValue
    private Long id;

    //            (unique = true)
    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;


//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @JoinTable(name = "tbl_roles", joinColumns = @JoinColumn(name = "user_id"))
//    @Column(name = "role", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private List<Role> roles;
//
//    @OneToOne
//    private Client client;

    @Column
    private String password;

    @Column
    private boolean active;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles()
//                .map(Enum::name)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toSet());
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public User merge(User merger) {
        if (merger == null) {
            return this;
        }

        Long idM = merger.getId();
        if (null != idM) {
            setId(idM);
        }

        String firstNameM = merger.getFirstName();
        if (null != firstNameM) {
            setFirstName(firstNameM);
        }

        String passwordM = merger.getPassword();
        if (null != passwordM) {
            setPassword(passwordM);
        }

        Role rolesM = merger.getRole();
        if (null != rolesM) {
            setRole(rolesM);
        }

        Gender genderM = merger.getGender();
        if (null != genderM) {
            setGender(genderM);
        }

        return this;
    }
}
