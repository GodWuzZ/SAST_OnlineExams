package sast.onlineexams;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sast.onlineexams.dao.UmsAdminRoleRelationDao;
import sast.onlineexams.mbg.mapper.UmsAdminMapper;
import sast.onlineexams.mbg.mapper.UmsPermissionMapper;
import sast.onlineexams.mbg.model.UmsAdmin;
import sast.onlineexams.mbg.model.UmsAdminExample;
import sast.onlineexams.mbg.model.UmsPermission;
import sast.onlineexams.mbg.model.UmsPermissionExample;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OnlineExamsApplicationTests {
    private static final Logger LOGGER=LoggerFactory.getLogger(OnlineExamsApplicationTests.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UmsPermissionMapper umsPermissionMapper;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;
    @Autowired
    private UmsAdminMapper adminMapper;
    @Test
    void contextLoads(){
        PageHelper.startPage(1,5);
        List<UmsPermission>permissionList=umsPermissionMapper.selectByExample(new UmsPermissionExample());
        PageHelper.startPage(2,5);
        List<UmsAdmin>admins=adminMapper.selectByExample(new UmsAdminExample());
        PageInfo<UmsPermission>pageInfo=new PageInfo<>(permissionList);
        List<UmsPermission>list=pageInfo.getList();
        for(UmsPermission permission:list){
            LOGGER.info(permission.getName());
        }
        for(UmsAdmin admin:admins){
            LOGGER.info(admin.getUsername());
        }
    }

    }
