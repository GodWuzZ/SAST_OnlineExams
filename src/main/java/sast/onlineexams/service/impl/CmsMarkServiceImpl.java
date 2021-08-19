package sast.onlineexams.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sast.onlineexams.dao.CmsAnswerDispatchDao;
import sast.onlineexams.dto.AmsProblemDetails;
import sast.onlineexams.dto.CmsAnswerDetails;
import sast.onlineexams.dto.CmsProgress;
import sast.onlineexams.dto.GroupProblemRelation;
import sast.onlineexams.mbg.mapper.*;
import sast.onlineexams.mbg.model.*;
import sast.onlineexams.service.AmsProblemService;
import sast.onlineexams.service.CmsMarkService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sherman
 * @create 2021-08-13 9:53
 * @description
 */
@Service
public class CmsMarkServiceImpl implements CmsMarkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmsProblemServiceImpl.class);
    @Autowired
    private AmsProblemsMapper problemsMapper;
    @Autowired
    private UmsGroupsMapper groupsMapper;
    @Autowired
    private CmsAnswerDispatchesMapper dispatchesMapper;
    @Autowired
    private AmsProblemService problemService;
    @Autowired
    private CmsAnswersMapper answersMapper;
    @Autowired
    private UmsStudentMapper studentMapper;
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
    public CmsAnswerDetails getMarkContent(Long problem_id, Long user_id, int num) {
        int surplus = num;
        List<CmsAnswers> answers = new ArrayList<>();
        CmsAnswersExample answersExample = new CmsAnswersExample();
        CmsAnswerDispatchesExample example0 = new CmsAnswerDispatchesExample();
        example0.createCriteria().andUserIdEqualTo(user_id).andProblemIdEqualTo(problem_id)
                .andSolvedEqualTo(false).andExpiredAtGreaterThanOrEqualTo(new Date());
        List<CmsAnswerDispatches> cmsAnswerDispatches = dispatchesMapper.selectByExample(example0);
        if (cmsAnswerDispatches!=null&&cmsAnswerDispatches.size()>0){
            List<Long> idList;
            if (num<=cmsAnswerDispatches.size()){
                idList = cmsAnswerDispatches.subList(0, num).stream()
                        .map(CmsAnswerDispatches::getAnswerId)
                        .collect(Collectors.toList());
                surplus=0;
            }else {
                idList = cmsAnswerDispatches.stream()
                        .map(CmsAnswerDispatches::getAnswerId)
                        .collect(Collectors.toList());
                surplus=num-cmsAnswerDispatches.size();
            }
            answersExample.createCriteria().andIdIn(idList);
            answers.addAll(answersMapper.selectByExampleWithBLOBs(answersExample));
        }
        if (surplus>0){
            CmsAnswerDispatchesExample example1 = new CmsAnswerDispatchesExample();
            example1.or().andSolvedEqualTo(true);
            example1.or().andSolvedEqualTo(false).andExpiredAtGreaterThanOrEqualTo(new Date());
            List<Long> idList = dispatchesMapper.selectByExample(example1).stream()
                    .map(CmsAnswerDispatches::getAnswerId)
                    .collect(Collectors.toList());
            if (idList!=null&&idList.size()>0){
                answersExample.createCriteria().andIdNotIn(idList).andProblemIdEqualTo(problem_id);
            }else{
                answersExample.createCriteria().andProblemIdEqualTo(problem_id);
            }
            List<CmsAnswers>answers_tmp = answersMapper.selectByExampleWithBLOBs(answersExample);
            Collections.shuffle(answers_tmp);
            if (surplus<=answers_tmp.size()){
                answers.addAll(answers_tmp.subList(0,surplus));
            }else{
                answers.addAll(answers_tmp);
            }
            CmsAnswerDispatches answerDispatch = new CmsAnswerDispatches();
            for (CmsAnswers answer:answers_tmp){
                CmsAnswerDispatchesExample example2 = new CmsAnswerDispatchesExample();
                example2.createCriteria().andAnswerIdEqualTo(answer.getId());
                List<CmsAnswerDispatches> dispatches = dispatchesMapper.selectByExample(example2);
                if (dispatches!=null&&dispatches.size()>0){
                    CmsAnswerDispatches dispatch = dispatches.get(0);
                    dispatch.setUserId(user_id);
                    dispatch.setDispatchedAt(new Date());
                    dispatch.setExpiredAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000));
                    dispatchesMapper.updateByPrimaryKeySelective(dispatch);
                }else{
                    answerDispatch.setAnswerId(answer.getId());
                    answerDispatch.setProblemId(problem_id);
                    answerDispatch.setUserId(user_id);
                    answerDispatch.setSolved(false);
                    answerDispatch.setDispatchedAt(new Date());
                    answerDispatch.setExpiredAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000));
                    dispatchesMapper.insertSelective(answerDispatch);
                }
            }
        }
        if (answers!=null&&answers.size()>0){
            AmsProblemDetails problemDetail = problemService.getProblem(problem_id);
            return new CmsAnswerDetails(problemDetail,answers);
        }
        return null;
    }

    @Override
    public int markSubmit(CmsAnswerDispatches answerDispatches) {
        CmsAnswerDispatchesExample example = new CmsAnswerDispatchesExample();
        example.createCriteria().andAnswerIdEqualTo(answerDispatches.getAnswerId());
        List<CmsAnswerDispatches> cmsAnswerDispatches = dispatchesMapper.selectByExample(example);
        if (cmsAnswerDispatches!=null&&cmsAnswerDispatches.size()>0){
            CmsAnswerDispatches dispatch = cmsAnswerDispatches.get(0);
            if (dispatch.getExpiredAt().compareTo(new Date())<=0){
                return 0;
            }
            answerDispatches.setSolved(true);
            answerDispatches.setId(dispatch.getId());
            return dispatchesMapper.updateByPrimaryKeySelective(answerDispatches);
        }
        return 0;
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
            progressList.add(new CmsProgress(relation.getGroup().getName(),total_count,marked_count,(double)marked_count/total_count));
        }
        return progressList;
    }

    @Override
    public int autoCorrect(int missing_part_score) {
        AmsProblemsExample problemsExample = new AmsProblemsExample();
        problemsExample.createCriteria().andTypeNotEqualTo(3);
        List<AmsProblems> problems = problemsMapper.selectByExampleWithBLOBs(problemsExample);
        for (AmsProblems problem:problems){
            CmsAnswersExample answersExample = new CmsAnswersExample();
            answersExample.createCriteria().andProblemIdEqualTo(problem.getId());
            List<CmsAnswers> answers = answersMapper.selectByExampleWithBLOBs(answersExample);
            for (CmsAnswers answer:answers){
                switch (problem.getType()){
                    case 0:
                        if (problem.getStandardAnswer().equals(answer.getOptionValue())){
                            quicklyInsert(answer.getId(),problem.getId(),problem.getMaxScore());
                        }else {
                            List<String> standard_list = Arrays.asList(problem.getStandardAnswer().split(""));
                            if (standard_list.size()==1)
                                quicklyInsert(answer.getId(),problem.getId(),0);
                            else {
                                List<String> answer_list = Arrays.asList(answer.getOptionValue().split(""));
                                int i;
                                for (i=0;i<answer_list.size();i++){
                                    if (!standard_list.contains(answer_list.get(i))){
                                        quicklyInsert(answer.getId(),problem.getId(),0);
                                        break;
                                    }
                                }
                                if (i==answer_list.size())
                                    quicklyInsert(answer.getId(),problem.getId(),missing_part_score);
                            }
                        }
                        break;
                    case 1:
                        if (problem.getStandardAnswer().equals(answer.getOptionValue())){
                            quicklyInsert(answer.getId(),problem.getId(),problem.getMaxScore());
                        }else {
                            quicklyInsert(answer.getId(),problem.getId(),0);
                        }
                        break;
                    case 2:
                        if (problem.getStandardAnswer().equals(answer.getContent())){
                            quicklyInsert(answer.getId(),problem.getId(),problem.getMaxScore());
                        }else {
                            quicklyInsert(answer.getId(),problem.getId(),0);
                        }
                        break;
                }
            }
        }
        return 0;
    }

    public void quicklyInsert(Long answer_id,Long problem_id, int score){
        CmsAnswerDispatches answerDispatch = new CmsAnswerDispatches();
        CmsAnswerDispatchesExample answersExample = new CmsAnswerDispatchesExample();
        answersExample.createCriteria().andAnswerIdEqualTo(answer_id);
        List<CmsAnswerDispatches> cmsAnswerDispatches = dispatchesMapper.selectByExample(answersExample);
        if (cmsAnswerDispatches!=null&&cmsAnswerDispatches.size()>0){
            CmsAnswerDispatches dispatch = cmsAnswerDispatches.get(0);
            dispatch.setAnswerId(answer_id);
            dispatch.setProblemId(problem_id);
            dispatch.setScore(score);
            dispatch.setSolved(true);
            dispatch.setDispatchedAt(new Date());
            dispatch.setExpiredAt(null);
            dispatchesMapper.updateByPrimaryKey(dispatch);
        }else {
            answerDispatch.setScore(score);
            answerDispatch.setAnswerId(answer_id);
            answerDispatch.setSolved(true);
            answerDispatch.setProblemId(problem_id);
            answerDispatch.setDispatchedAt(new Date());
            dispatchesMapper.insertSelective(answerDispatch);
        }
    }

    @Override
    public List<List<String>> head() {
        AmsProblemsExample problemsExample  =new AmsProblemsExample();
        problemsExample.createCriteria();
        List<AmsProblems> problems = problemsMapper.selectByExample(problemsExample);
        int i=1;
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        List<String> head1 = new ArrayList<String>();
        List<String> head2 = new ArrayList<String>();
        head0.add("学号");
        head1.add("姓名");
        list.add(head0);
        list.add(head1);
        for (AmsProblems problem:problems){
            List<String> head = new ArrayList<String>();
            head.add("第"+i+"题");
            head.add(problem.getTitle());
            list.add(head);
            i++;
        }
        head2.add("总分");
        list.add(head2);
        return list;

    }

    @Override
    public List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        UmsStudentExample studentExample = new UmsStudentExample();
        studentExample.createCriteria();
        List<UmsStudent> students = studentMapper.selectByExample(studentExample);
        AmsProblemsExample problemsExample = new AmsProblemsExample();
        problemsExample.createCriteria();
        List<AmsProblems> problems = problemsMapper.selectByExample(problemsExample);
        for (UmsStudent student:students){
            int total=0;
            List<Object> data = new ArrayList<Object>();
            data.add(student.getNumber());
            data.add(student.getName());
            for (AmsProblems problem:problems){
                int score = Score(student.getId(),problem.getId());
                data.add(score);
                total+=score;
            }
            data.add(total);
            list.add(data);
        }
        return list;
    }

    public int Score(Long student_id,Long problem_id){
        CmsAnswersExample answersExample = new CmsAnswersExample();
        answersExample.createCriteria().andStudentIdEqualTo(student_id).andProblemIdEqualTo(problem_id);
        List<CmsAnswers> answers = answersMapper.selectByExample(answersExample);
        if (answers!=null&&answers.size()>0){
            Long answer_id = answers.get(0).getId();
            CmsAnswerDispatchesExample dispatchesExample = new CmsAnswerDispatchesExample();
            dispatchesExample.createCriteria().andAnswerIdEqualTo(answer_id);
            return dispatchesMapper.selectByExample(dispatchesExample).get(0).getScore();
        }
        return 0;
    }

    @Override
    public Boolean isCorrectingCompleted() {
        CmsAnswersExample answersExample = new CmsAnswersExample();
        answersExample.createCriteria();
        CmsAnswerDispatchesExample dispatchesExample = new CmsAnswerDispatchesExample();
        dispatchesExample.createCriteria().andSolvedEqualTo(true);
        Long answer_count = answersMapper.countByExample(answersExample);
        Long dispatch_count = dispatchesMapper.countByExample(dispatchesExample);
        return answer_count==dispatch_count?true:false;
    }
}
