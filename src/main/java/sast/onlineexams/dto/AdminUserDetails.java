package sast.onlineexams.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sast.onlineexams.mbg.model.UmsAdmin;
import sast.onlineexams.mbg.model.UmsPermission;
import sast.onlineexams.mbg.model.UmsRole;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sherman
 * @create 2021-07-29 13:14
 * @description SpringSecurity需要的用户详情
 */
public class AdminUserDetails implements UserDetails {
    @JsonView(UmsAdmin.AdminSimpleView.class)
    private UmsAdmin umsAdmin;
    private List<UmsPermission> permissionList;
    @JsonView(UmsAdmin.AdminSimpleView.class)
    private List<Long>roleList;

    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> permissionList) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
    }

    public AdminUserDetails(UmsAdmin umsAdmin,Collection<Long>roleList){
        this.umsAdmin=umsAdmin;
        this.roleList=(List<Long>)roleList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return permissionList.stream()
                .filter(permission -> permission.getValue()!=null)
                .map(permission ->new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
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
        return umsAdmin.getStatus().equals(1);
    }
}
