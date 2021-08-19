package sast.onlineexams.service;

import sast.onlineexams.mbg.model.AmsAnnouncements;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-12 10:50
 * @description
 */
public interface AmsNoticeService {
    void deleteNotice(Long id);
    int updateNotice(AmsAnnouncements announcements);
    List<AmsAnnouncements> getNoticeList();
    AmsAnnouncements getNoticeById(Long id);
}
