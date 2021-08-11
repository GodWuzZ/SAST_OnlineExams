package sast.onlineexams.mbg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.AmsProblemOptions;
import sast.onlineexams.mbg.model.AmsProblemOptionsExample;

@Mapper
public interface AmsProblemOptionsMapper {
    long countByExample(AmsProblemOptionsExample example);

    int deleteByExample(AmsProblemOptionsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsProblemOptions record);

    int insertSelective(AmsProblemOptions record);

    List<AmsProblemOptions> selectByExample(AmsProblemOptionsExample example);

    AmsProblemOptions selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsProblemOptions record, @Param("example") AmsProblemOptionsExample example);

    int updateByExample(@Param("record") AmsProblemOptions record, @Param("example") AmsProblemOptionsExample example);

    int updateByPrimaryKeySelective(AmsProblemOptions record);

    int updateByPrimaryKey(AmsProblemOptions record);
}