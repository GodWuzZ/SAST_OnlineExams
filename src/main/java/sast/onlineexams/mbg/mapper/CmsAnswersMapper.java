package sast.onlineexams.mbg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.CmsAnswers;
import sast.onlineexams.mbg.model.CmsAnswersExample;

@Mapper
public interface CmsAnswersMapper {
    long countByExample(CmsAnswersExample example);

    int deleteByExample(CmsAnswersExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CmsAnswers record);

    int insertSelective(CmsAnswers record);

    List<CmsAnswers> selectByExampleWithBLOBs(CmsAnswersExample example);

    List<CmsAnswers> selectByExample(CmsAnswersExample example);

    CmsAnswers selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CmsAnswers record, @Param("example") CmsAnswersExample example);

    int updateByExampleWithBLOBs(@Param("record") CmsAnswers record, @Param("example") CmsAnswersExample example);

    int updateByExample(@Param("record") CmsAnswers record, @Param("example") CmsAnswersExample example);

    int updateByPrimaryKeySelective(CmsAnswers record);

    int updateByPrimaryKeyWithBLOBs(CmsAnswers record);

    int updateByPrimaryKey(CmsAnswers record);
}