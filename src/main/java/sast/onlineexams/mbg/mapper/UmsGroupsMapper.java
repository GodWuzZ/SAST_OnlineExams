package sast.onlineexams.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.UmsGroups;
import sast.onlineexams.mbg.model.UmsGroupsExample;

public interface UmsGroupsMapper {
    long countByExample(UmsGroupsExample example);

    int deleteByExample(UmsGroupsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsGroups record);

    int insertSelective(UmsGroups record);

    List<UmsGroups> selectByExample(UmsGroupsExample example);

    UmsGroups selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsGroups record, @Param("example") UmsGroupsExample example);

    int updateByExample(@Param("record") UmsGroups record, @Param("example") UmsGroupsExample example);

    int updateByPrimaryKeySelective(UmsGroups record);

    int updateByPrimaryKey(UmsGroups record);
}