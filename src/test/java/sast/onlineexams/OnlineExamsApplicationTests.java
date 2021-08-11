package sast.onlineexams;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sast.onlineexams.dao.UmsAdminRoleRelationDao;
import sast.onlineexams.mbg.mapper.UmsPermissionMapper;
import sast.onlineexams.mbg.model.UmsPermission;
import sast.onlineexams.mbg.model.UmsPermissionExample;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OnlineExamsApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    UmsPermissionMapper umsPermissionMapper;
    @Autowired
    UmsAdminRoleRelationDao adminRoleRelationDao;
    @Test
    void contextLoads() throws SQLException {
//        System.out.println(dataSource.getConnection());
        List<UmsPermission>permissions = adminRoleRelationDao.getPermissionList((long)1);
        for(UmsPermission permission:permissions){
            UmsPermissionExample example=new UmsPermissionExample();
            example.createCriteria().andPidEqualTo(permission.getId());
            List<UmsPermission>childPermissions = umsPermissionMapper.selectByExample(example);
            System.out.println(childPermissions.get(1).getValue());
        }

    }

}
