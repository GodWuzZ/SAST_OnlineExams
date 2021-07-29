package sast.onlineexams.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sast.onlineexams.mbg.model.AmsAnnouncements;
import sast.onlineexams.mbg.model.AmsAnnouncementsExample;

public interface AmsAnnouncementsMapper {
    long countByExample(AmsAnnouncementsExample example);

    int deleteByExample(AmsAnnouncementsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmsAnnouncements record);

    int insertSelective(AmsAnnouncements record);

    List<AmsAnnouncements> selectByExample(AmsAnnouncementsExample example);

    AmsAnnouncements selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmsAnnouncements record, @Param("example") AmsAnnouncementsExample example);

    int updateByExample(@Param("record") AmsAnnouncements record, @Param("example") AmsAnnouncementsExample example);

    int updateByPrimaryKeySelective(AmsAnnouncements record);

    int updateByPrimaryKey(AmsAnnouncements record);
}