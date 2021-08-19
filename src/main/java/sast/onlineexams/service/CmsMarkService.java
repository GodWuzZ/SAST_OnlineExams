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
    CmsAnswerDetails getMarkContent(Long problem_id,Long user_id, int num);
    int markSubmit(CmsAnswerDispatches answerDispatches);
    List<CmsProgress>markProgress();
    int autoCorrect(int missing_part_score);
    List<List<String>>head();
    List<List<Object>>dataList();
    Boolean isCorrectingCompleted();
}
