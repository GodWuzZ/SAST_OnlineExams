package sast.onlineexams.mbg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.AmsProblemImages;
import sast.onlineexams.mbg.model.AmsProblemImagesExample;

@Mapper
public interface AmsProblemImagesMapper {
    long countByExample(AmsProblemImagesExample example);

    int deleteByExample(AmsProblemImagesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsProblemImages record);

    int insertSelective(AmsProblemImages record);

    List<AmsProblemImages> selectByExample(AmsProblemImagesExample example);

    AmsProblemImages selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsProblemImages record, @Param("example") AmsProblemImagesExample example);

    int updateByExample(@Param("record") AmsProblemImages record, @Param("example") AmsProblemImagesExample example);

    int updateByPrimaryKeySelective(AmsProblemImages record);

    int updateByPrimaryKey(AmsProblemImages record);
}