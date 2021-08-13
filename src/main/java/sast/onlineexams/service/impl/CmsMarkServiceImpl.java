package sast.onlineexams.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sast.onlineexams.dao.CmsAnswerDispatchDao;
import sast.onlineexams.dto.AmsProblemDetails;
import sast.onlineexams.dto.CmsAnswerDetails;
import sast.onlineexams.dto.CmsProgress;
import sast.onlineexams.dto.GroupProblemRelation;
import sast.onlineexams.mbg.mapper.AmsProblemsMapper;
import sast.onlineexams.mbg.mapper.CmsAnswerDispatchesMapper;
import sast.onlineexams.mbg.mapper.CmsAnswersMapper;
import sast.onlineexams.mbg.mapper.UmsGroupsMapper;
import sast.onlineexams.mbg.model.*;
import sast.onlineexams.service.AmsProblemService;
import sast.onlineexams.service.CmsMarkService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sherman
 * @create 2021-08-13 9:53
 * @description
 */
@Service
public class CmsMarkServiceImpl implements CmsMarkService {
    @Autowired
    private AmsProblemsMapper problemsMapper;
    @Autowired
    private UmsGroupsMapper groupsMapper;
    @Autowired
    private CmsAnswerDispatchesMapper dispatchesMapper;
    @Autowired
    private CmsAnswerDispatchDao answerDispatchDao;
    @Autowired
    private AmsProblemService problemService;
    @Autowired
    private CmsAnswersMapper answersMapper;
    @Override
    public List<GroupProblemRelation> getGroupProblemRelation() {
        UmsGroupsExample groupsExample = new UmsGroupsExample();
        groupsExample.createCriteria();
        List<UmsGroups>groups=groupsMapper.selectByExample(groupsExample);
        List<GroupProblemRelation>relations=new ArrayList<>();
        for(UmsGroups group:groups){
            AmsProblemsExample problemsExample=new AmsProblemsExample();
            problemsExample.createCriteria().andGroupIdEqualTo(group.getId());
            List<Long>problem_id_list = problemsMapper.selectByExample(problemsExample).stream().map(AmsProblems::getId).collect(Collectors.toList());
            relations.add(new GroupProblemRelation(group,problem_id_list));
        }
        return relations;
    }

    @Override
    public CmsAnswerDetails getMarkContent(Long problem_id, int num) {
        List<CmsAnswers>answers = answerDispatchDao.getAnswers(problem_id,num);
        AmsProblemDetails problemDetail = problemService.getProblem(problem_id);
        return new CmsAnswerDetails(problemDetail,answers);
    }

    @Override
    public int markSubmit(CmsAnswerDispatches answerDispatches) {
        if(answerDispatches.getId()!=null)
            return dispatchesMapper.updateByPrimaryKeySelective(answerDispatches);
        return dispatchesMapper.insertSelective(answerDispatches);
    }

    @Override
    public List<CmsProgress> markProgress() {
        List<CmsProgress>progressList=new ArrayList<>();
        List<GroupProblemRelation>relations = getGroupProblemRelation();
        for(GroupProblemRelation relation:relations){
            int total_count=0;
            int marked_count=0;
            for (Long id:relation.getProblem_id()){
                CmsAnswersExample answersExample = new CmsAnswersExample();
                answersExample.createCriteria().andProblemIdEqualTo(id);
                total_count+=answersMapper.countByExample(answersExample);
                CmsAnswerDispatchesExample dispatchesExample = new CmsAnswerDispatchesExample();
                dispatchesExample.createCriteria().andProblemIdEqualTo(id).andSolvedEqualTo(true);
                marked_count+=dispatchesMapper.countByExample(dispatchesExample);
            }
            progressList.add(new CmsProgress(relation.getGroup().getName(),total_count,marked_count,(double)total_count/marked_count));
        }
        return progressList;
    }
}
