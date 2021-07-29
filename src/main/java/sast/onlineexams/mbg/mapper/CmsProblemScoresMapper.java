package sast.onlineexams.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.CmsProblemScores;
import sast.onlineexams.mbg.model.CmsProblemScoresExample;

public interface CmsProblemScoresMapper {
    long countByExample(CmsProblemScoresExample example);

    int deleteByExample(CmsProblemScoresExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CmsProblemScores record);

    int insertSelective(CmsProblemScores record);

    List<CmsProblemScores> selectByExampleWithBLOBs(CmsProblemScoresExample example);

    List<CmsProblemScores> selectByExample(CmsProblemScoresExample example);

    CmsProblemScores selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CmsProblemScores record, @Param("example") CmsProblemScoresExample example);

    int updateByExampleWithBLOBs(@Param("record") CmsProblemScores record, @Param("example") CmsProblemScoresExample example);

    int updateByExample(@Param("record") CmsProblemScores record, @Param("example") CmsProblemScoresExample example);

    int updateByPrimaryKeySelective(CmsProblemScores record);

    int updateByPrimaryKeyWithBLOBs(CmsProblemScores record);

    int updateByPrimaryKey(CmsProblemScores record);
}