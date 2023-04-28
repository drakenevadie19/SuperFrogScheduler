package edu.tcu.cs.superfrogscheduler.user.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private final UserSecurity userSecurity;

    public UserPrincipal(UserSecurity userSecurity) {
        this.userSecurity = userSecurity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert a user's roles from space-delimited string to a list of SimpleGrantedAuthority objects.
        // Example: user roles are stored in a string like "admin user", we need to convert it to a list of GrantedAuthority.
        // Before conversion, we need to add this "ROLE_" prefix to each role name.
        return Arrays.stream(StringUtils.tokenizeToStringArray(this.userSecurity.getRoles(), " "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.userSecurity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userSecurity.getEmail();
    }

    public String getUserId() {
        return this.userSecurity.getUser().getId();
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
        return this.userSecurity.isEnabled();
    }

    public UserSecurity getUserSecurity() {
        return this.userSecurity;
    }

}
