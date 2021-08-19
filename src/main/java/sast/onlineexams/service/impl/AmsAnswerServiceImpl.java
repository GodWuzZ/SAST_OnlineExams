package sast.onlineexams.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sast.onlineexams.mbg.mapper.CmsAnswersMapper;
import sast.onlineexams.mbg.mapper.UmsContestsMapper;
import sast.onlineexams.mbg.model.CmsAnswers;
import sast.onlineexams.mbg.model.CmsAnswersExample;
import sast.onlineexams.mbg.model.UmsContests;
import sast.onlineexams.mbg.model.UmsContestsExample;
import sast.onlineexams.service.AmsAnswerService;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-13 9:18
 * @description
 */
@Service
public class AmsAnswerServiceImpl implements AmsAnswerService {
    @Autowired
    private UmsContestsMapper umsContestsMapper;
    @Autowired
    private CmsAnswersMapper answersMapper;
    @Override
    public UmsContests getDueTime() {
        UmsContestsExample example = new UmsContestsExample();
        example.createCriteria();
        List<UmsContests>contests = umsContestsMapper.selectByExample(example);
        if(contests!=null&&contests.size()>0)
            return contests.get(0);
        return null;
    }

    @Override
    public int submit(CmsAnswers answer) {
        CmsAnswersExample example = new CmsAnswersExample();
        example.createCriteria().andProblemIdEqualTo(answer.getProblemId()).andStudentIdEqualTo(answer.getStudentId());
        List<CmsAnswers> answers = answersMapper.selectByExample(example);
        if (answers!=null&&answers.size()>0){
            answer.setId(answers.get(0).getId());
            return answersMapper.updateByPrimaryKeySelective(answer);
        }
        return answersMapper.insertSelective(answer);
    }

    @Override
    public List<CmsAnswers> getSubmitted() {
        CmsAnswersExample example = new CmsAnswersExample();
        example.createCriteria();
        return answersMapper.selectByExample(example);
    }
}
