package com.project.shopHoangCamPro.models;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CustomUserDetail implements UserDetails {

    @Getter
    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetail(User user,Collection<? extends GrantedAuthority> authorities){
        super();
        this.user =user;
        this.authorities=authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole_id() == null) {
            return Collections.singletonList(new SimpleGrantedAuthority("user"));
        }
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole_id().getName()));
    }

    public CustomUserDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPhone();
    }

    public Integer getId(){
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }

    public String getAddress() {
        return user.getAddress();
    }

    public String getPhone(){
        return user.getPhone();
    }

    public Date getDateOfBirth(){
        return user.getDateOfBirth();
    }

    public Role getRole(){
        return user.getRole_id();
    }

    public Boolean getIsActive(){
        return user.getIsActive();
    }


    @Override
    public boolean isAccountNonExpired(){
        return true ;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true ;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true ;
    }

    @Override
    public boolean isEnabled(){
        return true ;
    }
}
