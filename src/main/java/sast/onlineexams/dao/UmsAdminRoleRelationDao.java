package sast.onlineexams.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import sast.onlineexams.mbg.model.UmsPermission;

import java.util.List;

/**
 * @author sherman
 * @create 2021-07-29 12:59
 * @description 后台用户与角色管理自定义Dao
 */
@Repository
public interface UmsAdminRoleRelationDao {
    /**
     * 获取用户所有权限(包括+-权限)
     */
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
