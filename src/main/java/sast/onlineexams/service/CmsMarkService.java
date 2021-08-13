package sast.onlineexams.service;

import sast.onlineexams.dto.CmsAnswerDetails;
import sast.onlineexams.dto.CmsProgress;
import sast.onlineexams.dto.GroupProblemRelation;
import sast.onlineexams.mbg.model.CmsAnswerDispatches;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-13 9:52
 * @description
 */
public interface CmsMarkService {
    List<GroupProblemRelation> getGroupProblemRelation();
    CmsAnswerDetails getMarkContent(Long problem_id,int num);
    int markSubmit(CmsAnswerDispatches answerDispatches);
    List<CmsProgress>markProgress();
}
