package fr.vertours.buddtwo.configuration;

import fr.vertours.buddtwo.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private Collection<? extends GrantedAuthority>  authorities;
    private String password;
    private String mail;


    public MyUserDetails(Collection<Role> roleCollection, String password, String mail) {
        grantedAuthorityFromRole(roleCollection);
        this.password = password;
        this.mail = mail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.mail;
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

    private void grantedAuthorityFromRole(Collection<Role> roleCollection) {
        this.authorities = roleCollection.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void setAuthorities(Collection<Role> roleCollection) {
        grantedAuthorityFromRole(roleCollection);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
