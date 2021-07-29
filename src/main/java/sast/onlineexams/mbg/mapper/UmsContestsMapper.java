package sast.onlineexams.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.UmsContests;
import sast.onlineexams.mbg.model.UmsContestsExample;

public interface UmsContestsMapper {
    long countByExample(UmsContestsExample example);

    int deleteByExample(UmsContestsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsContests record);

    int insertSelective(UmsContests record);

    List<UmsContests> selectByExample(UmsContestsExample example);

    UmsContests selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsContests record, @Param("example") UmsContestsExample example);

    int updateByExample(@Param("record") UmsContests record, @Param("example") UmsContestsExample example);

    int updateByPrimaryKeySelective(UmsContests record);

    int updateByPrimaryKey(UmsContests record);
}