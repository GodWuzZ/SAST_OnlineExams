package sast.onlineexams.service.impl;

import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import sast.onlineexams.dto.AdminUserDetails;
import sast.onlineexams.mbg.mapper.UmsAdminMapper;
import sast.onlineexams.mbg.mapper.UmsGroupsMapper;
import sast.onlineexams.mbg.mapper.UmsPermissionMapper;
import sast.onlineexams.mbg.mapper.UmsRoleMapper;
import sast.onlineexams.mbg.model.*;
import sast.onlineexams.service.UmsAdminService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sherman
 * @create 2021-07-29 12:57
 * @description UmsAdminService实现类
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("$(ums.default.admin.password)")
    private String DEFAULT_ADMIN_PASSWORD;
    @Autowired
    private UmsAdminMapper adminMapper;
    @Autowired
    private UmsRoleMapper umsRoleMapper;
    @Autowired
    private UmsPermissionMapper umsPermissionMapper;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;
    @Autowired
    private UmsGroupsMapper umsGroupsMapper;


    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList!=null&&umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        List<UmsPermission>permissions = adminRoleRelationDao.getPermissionList(adminId);
        List<UmsPermission> results = new ArrayList<>();
        results.addAll(permissions);
        for(UmsPermission permission:permissions){
            UmsPermissionExample example = new UmsPermissionExample();
            example.createCriteria().andPidEqualTo(permission.getId());
            results.addAll(umsPermissionMapper.selectByExample(example));
    }
        return results;
    }

    @Override
    public UmsAdmin updateAdmin(UmsAdmin umsAdmin) {
        if(umsAdmin.getId()==null) {
            //查询是否有相同用户名的用户
            UmsAdminExample example = new UmsAdminExample();
            example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
            List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
            if (umsAdminList!=null&&umsAdminList.size() > 0) {
                return null;
            }
            if(umsAdmin.getPassword()==null){
                umsAdmin.setPassword(DEFAULT_ADMIN_PASSWORD);
            }
            //将密码进行加密操作
            String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
            umsAdmin.setPassword(encodePassword);
            umsAdmin.setCreateTime(new Date());
            if(umsAdmin.getStatus()==null)
                umsAdmin.setStatus(1);
            adminMapper.insertSelective(umsAdmin);
            return umsAdmin;
        }
        if(umsAdmin.getPassword()!=null){
            //将密码进行加密操作
            String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
            umsAdmin.setPassword(encodePassword);
        }
        adminMapper.updateByPrimaryKeySelective(umsAdmin);
        return umsAdmin;
    }

    @Override
    public void deleteAdmin(long id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AdminUserDetails> adminList() {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria();
        List<UmsAdmin>adminList = adminMapper.selectByExample(example);
        List<AdminUserDetails>adminUserDetailsList = new ArrayList<>();
        for(UmsAdmin admin:adminList){
            List<Long> idList = adminRoleRelationDao.getRoleList(admin.getId()).stream().map(UmsRole::getId).collect(Collectors.toList());
            adminUserDetailsList.add(new AdminUserDetails(admin,idList));
        }
        return adminUserDetailsList;
    }

    @Override
    public List<UmsRole> getRoleList() {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria();
        return umsRoleMapper.selectByExample(example);
    }

    @Override
    public UmsGroups updateGroup(UmsGroups group) {
        if(group.getId()==null) {
            UmsGroupsExample example = new UmsGroupsExample();
            example.createCriteria().andNameEqualTo(group.getName());
            List<UmsGroups>groups = umsGroupsMapper.selectByExample(example);
            if(groups.size()>0){
                return null;
            }
            umsGroupsMapper.insertSelective(group);
            return group;
        }
        umsGroupsMapper.updateByPrimaryKeySelective(group);
        return group;
    }

    @Override
    public void deleteGroup(Long id) {
        umsGroupsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UmsGroups> getGroups() {
        UmsGroupsExample example = new UmsGroupsExample();
        example.createCriteria();
        List<UmsGroups>groups = umsGroupsMapper.selectByExample(example);
        if (groups!=null&&groups.size()>0){
            return groups;
        }
        return null;
    }

    @Override
    public void updateLoginTime(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin>admins = adminMapper.selectByExample(example);
        if(admins!=null&&admins.size()>0){
            UmsAdmin admin = admins.get(0);
            admin.setLoginTime(new Date());
            adminMapper.updateByPrimaryKeySelective(admin);
        }
    }
}
