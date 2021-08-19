package sast.onlineexams;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sast.onlineexams.common.utils.JwtTokenUtil;
import sast.onlineexams.common.utils.RedisUtil;
import sast.onlineexams.dao.UmsAdminRoleRelationDao;
import sast.onlineexams.mbg.mapper.CmsAnswerDispatchesMapper;
import sast.onlineexams.mbg.mapper.CmsAnswersMapper;
import sast.onlineexams.mbg.mapper.UmsAdminMapper;
import sast.onlineexams.mbg.mapper.UmsPermissionMapper;
import sast.onlineexams.mbg.model.*;

import javax.sound.midi.Soundbank;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CmsAnswersMapper answersMapper;
    @Autowired
    private CmsAnswerDispatchesMapper dispatchesMapper;
    @Test
    void contextLoads(){
        String string = "faf"+System.currentTimeMillis();
        System.out.println(string);
        }

    }
