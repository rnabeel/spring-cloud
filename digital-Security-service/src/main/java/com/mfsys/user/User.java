package com.mfsys.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dg_user")
//@IdClass(UserId.class)
public class User implements UserDetails {
    private String porOrgacode;
    @Id
    private String email;
    private String userName;
    private String name;
    private String password;
    private String phone;
    private boolean pending;
    private String lastLogin;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreviousPassword> previousPasswords = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
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
        return true; // TODO: form based
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // TODO: form based
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // TODO: form based
    }

    @Override
    public boolean isEnabled() {
        return true; // TODO: form based
    }
}
