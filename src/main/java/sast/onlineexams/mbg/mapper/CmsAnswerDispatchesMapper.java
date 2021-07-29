package sast.onlineexams.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.CmsAnswerDispatches;
import sast.onlineexams.mbg.model.CmsAnswerDispatchesExample;

public interface CmsAnswerDispatchesMapper {
    long countByExample(CmsAnswerDispatchesExample example);

    int deleteByExample(CmsAnswerDispatchesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CmsAnswerDispatches record);

    int insertSelective(CmsAnswerDispatches record);

    List<CmsAnswerDispatches> selectByExample(CmsAnswerDispatchesExample example);

    CmsAnswerDispatches selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CmsAnswerDispatches record, @Param("example") CmsAnswerDispatchesExample example);

    int updateByExample(@Param("record") CmsAnswerDispatches record, @Param("example") CmsAnswerDispatchesExample example);

    int updateByPrimaryKeySelective(CmsAnswerDispatches record);

    int updateByPrimaryKey(CmsAnswerDispatches record);
}