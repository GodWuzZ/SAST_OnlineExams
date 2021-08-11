package sast.onlineexams.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sast.onlineexams.mbg.model.UmsStudent;

import java.util.Collection;

/**
 * @author sherman
 * @create 2021-08-11 11:10
 * @description
 */
public class StudentUserDetails implements UserDetails {
    private UmsStudent umsStudent;

    public StudentUserDetails(UmsStudent umsStudent) {
        this.umsStudent = umsStudent;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return umsStudent.getPassword();
    }

    @Override
    public String getUsername() {
        return umsStudent.getUsername();
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
