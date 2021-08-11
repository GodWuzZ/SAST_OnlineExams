package sast.onlineexams.mbg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.AmsProblemAttachments;
import sast.onlineexams.mbg.model.AmsProblemAttachmentsExample;

@Mapper
public interface AmsProblemAttachmentsMapper {
    long countByExample(AmsProblemAttachmentsExample example);

    int deleteByExample(AmsProblemAttachmentsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsProblemAttachments record);

    int insertSelective(AmsProblemAttachments record);

    List<AmsProblemAttachments> selectByExample(AmsProblemAttachmentsExample example);

    AmsProblemAttachments selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsProblemAttachments record, @Param("example") AmsProblemAttachmentsExample example);

    int updateByExample(@Param("record") AmsProblemAttachments record, @Param("example") AmsProblemAttachmentsExample example);

    int updateByPrimaryKeySelective(AmsProblemAttachments record);

    int updateByPrimaryKey(AmsProblemAttachments record);
}