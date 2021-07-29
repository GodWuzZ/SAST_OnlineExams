package sast.onlineexams.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.AmsProblems;
import sast.onlineexams.mbg.model.AmsProblemsExample;

public interface AmsProblemsMapper {
    long countByExample(AmsProblemsExample example);

    int deleteByExample(AmsProblemsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsProblems record);

    int insertSelective(AmsProblems record);

    List<AmsProblems> selectByExampleWithBLOBs(AmsProblemsExample example);

    List<AmsProblems> selectByExample(AmsProblemsExample example);

    AmsProblems selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsProblems record, @Param("example") AmsProblemsExample example);

    int updateByExampleWithBLOBs(@Param("record") AmsProblems record, @Param("example") AmsProblemsExample example);

    int updateByExample(@Param("record") AmsProblems record, @Param("example") AmsProblemsExample example);

    int updateByPrimaryKeySelective(AmsProblems record);

    int updateByPrimaryKeyWithBLOBs(AmsProblems record);

    int updateByPrimaryKey(AmsProblems record);
}