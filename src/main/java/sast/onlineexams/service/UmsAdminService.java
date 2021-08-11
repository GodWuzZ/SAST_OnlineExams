package sast.onlineexams.service;

import sast.onlineexams.mbg.model.UmsAdmin;
import sast.onlineexams.mbg.model.UmsPermission;

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
    int insertAdmin(UmsAdmin umsAdmin);
    int updateAdmin(UmsAdmin umsAdmin);
    int deleteAdmin(long id);
    List<UmsAdmin>adminList();

}
