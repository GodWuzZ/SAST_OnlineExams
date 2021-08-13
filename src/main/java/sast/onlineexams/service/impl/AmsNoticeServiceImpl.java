package sast.onlineexams.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sast.onlineexams.mbg.mapper.AmsAnnouncementsMapper;
import sast.onlineexams.mbg.model.AmsAnnouncements;
import sast.onlineexams.mbg.model.AmsAnnouncementsExample;
import sast.onlineexams.service.AmsNoticeService;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-12 10:51
 * @description
 */
@Service
public class AmsNoticeServiceImpl implements AmsNoticeService {
    @Autowired
    private AmsAnnouncementsMapper amsAnnouncementsMapper;
    @Override
    public int insertNotice(AmsAnnouncements announcements) {
        return amsAnnouncementsMapper.insertSelective(announcements);
    }

    @Override
    public int deleteNotice(Long id) {
        return amsAnnouncementsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateNotice(AmsAnnouncements announcements) {
        return amsAnnouncementsMapper.updateByPrimaryKeySelective(announcements);
    }

    @Override
    public List<AmsAnnouncements> getNoticeList() {
        AmsAnnouncementsExample example = new AmsAnnouncementsExample();
        example.createCriteria();
        return amsAnnouncementsMapper.selectByExample(example);
    }

    @Override
    public AmsAnnouncements getNoticeById(Long id) {
        return amsAnnouncementsMapper.selectByPrimaryKey(id);
    }
}
