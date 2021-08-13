package sast.onlineexams.service;

import sast.onlineexams.mbg.model.CmsAnswers;
import sast.onlineexams.mbg.model.UmsContests;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-13 9:18
 * @description
 */
public interface AmsAnswerService {
    UmsContests getDueTime();
    int submit(CmsAnswers answer);
    List<CmsAnswers>getSubmitted();
}
