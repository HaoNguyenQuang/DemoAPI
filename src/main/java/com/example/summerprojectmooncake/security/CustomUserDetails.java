package com.example.summerprojectmooncake.security;

import com.example.summerprojectmooncake.entity.Role;
import com.example.summerprojectmooncake.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String email;
    @JsonIgnore
    private String password;
    private boolean isEnable;
    private Collection<? extends GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    // tu thong tin user chuyen sang thong tin customUserDetails
    public static CustomUserDetails mapUserToUserDetail(User user){
        //Lay cac quyen tu doi tuong user
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role role: user.getListRole()){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRoleName().name());
            authorityList.add(simpleGrantedAuthority);
        }
        //return obj customUserDetails
        return new CustomUserDetails(
                user.getId(),
                user.getName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPassword(),
                user.isEnable(),
                authorityList
        );
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    //het han account
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
        return isEnable;
    }
}
