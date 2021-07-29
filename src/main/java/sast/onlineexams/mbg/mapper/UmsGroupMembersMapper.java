package sast.onlineexams.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.UmsGroupMembers;
import sast.onlineexams.mbg.model.UmsGroupMembersExample;

public interface UmsGroupMembersMapper {
    long countByExample(UmsGroupMembersExample example);

    int deleteByExample(UmsGroupMembersExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsGroupMembers record);

    int insertSelective(UmsGroupMembers record);

    List<UmsGroupMembers> selectByExample(UmsGroupMembersExample example);

    UmsGroupMembers selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsGroupMembers record, @Param("example") UmsGroupMembersExample example);

    int updateByExample(@Param("record") UmsGroupMembers record, @Param("example") UmsGroupMembersExample example);

    int updateByPrimaryKeySelective(UmsGroupMembers record);

    int updateByPrimaryKey(UmsGroupMembers record);
}