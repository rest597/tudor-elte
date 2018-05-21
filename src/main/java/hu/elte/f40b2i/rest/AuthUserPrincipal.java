package hu.elte.f40b2i.rest;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hu.elte.f40b2i.data.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUserPrincipal implements UserDetails {
    private User user;
    private List<GrantedAuthority> auths=new ArrayList<GrantedAuthority>(5);


    public Collection<? extends GrantedAuthority> getAuthorities() { return auths; }
    public String getUsername() { return user.getUsername(); }
    public String getPassword() { return user.getPassword(); }
    public int getId() { return user.getUserid(); }

    public boolean isEnabled() { return true; }
    public boolean isCredentialsNonExpired() { return true; }
    public boolean isAccountNonExpired() { return true; }
    public boolean isAccountNonLocked() { return true; }

    public AuthUserPrincipal(User user) {
        this.user = user;

        auths.add(new SimpleGrantedAuthority("ROLE_" + user.getUserType().name().toUpperCase()));

    }

}
