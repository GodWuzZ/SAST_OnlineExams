package sast.onlineexams.mbg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.UmsStudent;
import sast.onlineexams.mbg.model.UmsStudentExample;

@Mapper
public interface UmsStudentMapper {
    long countByExample(UmsStudentExample example);

    int deleteByExample(UmsStudentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsStudent record);

    int insertSelective(UmsStudent record);

    List<UmsStudent> selectByExample(UmsStudentExample example);

    UmsStudent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsStudent record, @Param("example") UmsStudentExample example);

    int updateByExample(@Param("record") UmsStudent record, @Param("example") UmsStudentExample example);

    int updateByPrimaryKeySelective(UmsStudent record);

    int updateByPrimaryKey(UmsStudent record);
}