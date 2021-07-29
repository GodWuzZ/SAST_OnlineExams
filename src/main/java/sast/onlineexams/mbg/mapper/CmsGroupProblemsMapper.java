package sast.onlineexams.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.CmsGroupProblems;
import sast.onlineexams.mbg.model.CmsGroupProblemsExample;

public interface CmsGroupProblemsMapper {
    long countByExample(CmsGroupProblemsExample example);

    int deleteByExample(CmsGroupProblemsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CmsGroupProblems record);

    int insertSelective(CmsGroupProblems record);

    List<CmsGroupProblems> selectByExample(CmsGroupProblemsExample example);

    CmsGroupProblems selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CmsGroupProblems record, @Param("example") CmsGroupProblemsExample example);

    int updateByExample(@Param("record") CmsGroupProblems record, @Param("example") CmsGroupProblemsExample example);

    int updateByPrimaryKeySelective(CmsGroupProblems record);

    int updateByPrimaryKey(CmsGroupProblems record);
}