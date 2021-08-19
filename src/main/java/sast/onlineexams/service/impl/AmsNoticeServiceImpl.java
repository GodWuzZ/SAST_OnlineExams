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
    public void deleteNotice(Long id) {
        amsAnnouncementsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateNotice(AmsAnnouncements announcements) {
        if (announcements.getId()==null)
            return amsAnnouncementsMapper.insertSelective(announcements);
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
