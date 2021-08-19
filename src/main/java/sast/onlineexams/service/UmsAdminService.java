package sast.onlineexams.service;

import sast.onlineexams.dto.AdminUserDetails;
import sast.onlineexams.mbg.model.UmsAdmin;
import sast.onlineexams.mbg.model.UmsGroups;
import sast.onlineexams.mbg.model.UmsPermission;
import sast.onlineexams.mbg.model.UmsRole;

import java.util.List;

/**
 * @author sherman
 * @create 2021-07-29 12:48
 * @description 后台管理员service
 */
public interface UmsAdminService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdmin umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<UmsPermission> getPermissionList(Long adminId);
    UmsAdmin updateAdmin(UmsAdmin umsAdmin);
    void deleteAdmin(long id);
    List<AdminUserDetails>adminList();
    List<UmsRole>getRoleList();
    UmsGroups updateGroup(UmsGroups group);
    void deleteGroup(Long id);
    List<UmsGroups>getGroups();
    void updateLoginTime(String username);
}
