package sast.onlineexams.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sast.onlineexams.common.utils.JwtTokenUtil;
import sast.onlineexams.dao.UmsAdminRoleRelationDao;
import sast.onlineexams.dto.StudentUserDetails;
import sast.onlineexams.mbg.mapper.UmsAdminMapper;
import sast.onlineexams.mbg.mapper.UmsPermissionMapper;
import sast.onlineexams.mbg.mapper.UmsStudentMapper;
import sast.onlineexams.mbg.model.UmsStudent;
import sast.onlineexams.mbg.model.UmsStudentExample;
import sast.onlineexams.service.UmsStudentService;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-11 10:53
 * @description
 */
@Service
public class UmsStudentSeviceImpl implements UmsStudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsStudentMapper umsStudentMapper;

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UmsStudent student = getStudentByUsername(username);
            if (student!=null){
                StudentUserDetails userDetails=new StudentUserDetails(student);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                    throw new BadCredentialsException("密码不正确");
                }
                token = jwtTokenUtil.generateTokenDefault(userDetails);
            }
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public UmsStudent getStudentByUsername(String username) {
        UmsStudentExample example = new UmsStudentExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsStudent>students = umsStudentMapper.selectByExample(example);
        if(students!=null&&students.size()>0){
            return students.get(0);
        }
        return null;
    }

    @Override
    public int insertStudent(UmsStudent umsStudent) {
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsStudent.getPassword());
        umsStudent.setPassword(encodePassword);
        return umsStudentMapper.insertSelective(umsStudent);
    }

    @Override
    public int insertStudentMassively(List<UmsStudent> umsStudents) {
        int count=0;
        for(UmsStudent umsStudent:umsStudents){
            //将密码进行加密操作
            String encodePassword = passwordEncoder.encode(umsStudent.getPassword());
            umsStudent.setPassword(encodePassword);
            umsStudentMapper.insertSelective(umsStudent);
            count++;
        }
        return count;
    }

    @Override
    public int updateStudent(UmsStudent umsStudent) {
        return umsStudentMapper.updateByPrimaryKeySelective(umsStudent);
    }

    @Override
    public int deleteStudent(long id) {
        return umsStudentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UmsStudent> studentList() {
        UmsStudentExample example = new UmsStudentExample();
        example.createCriteria();
        return umsStudentMapper.selectByExample(example);
    }


}
