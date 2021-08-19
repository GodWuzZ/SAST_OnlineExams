package sast.onlineexams.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sast.onlineexams.dto.AmsProblemDetails;
import sast.onlineexams.mbg.mapper.AmsProblemAttachmentsMapper;
import sast.onlineexams.mbg.mapper.AmsProblemImagesMapper;
import sast.onlineexams.mbg.mapper.AmsProblemOptionsMapper;
import sast.onlineexams.mbg.mapper.AmsProblemsMapper;
import sast.onlineexams.mbg.model.*;
import sast.onlineexams.service.AmsProblemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sherman
 * @create 2021-08-12 13:34
 * @description
 */
@Service
public class AmsProblemServiceImpl implements AmsProblemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmsProblemServiceImpl.class);
    @Autowired
    private AmsProblemsMapper problemsMapper;
    @Autowired
    private AmsProblemImagesMapper imagesMapper;
    @Autowired
    private AmsProblemAttachmentsMapper attachmentsMapper;
    @Autowired
    private AmsProblemOptionsMapper optionsMapper;

    @Override
    public void updateProblemDetails(AmsProblemDetails amsProblemDetails) {
        AmsProblems problem = amsProblemDetails.getProblem();
        List<AmsProblemImages> images=amsProblemDetails.getImages();
        List<AmsProblemAttachments>attachments=amsProblemDetails.getAttachments();
        List<AmsProblemOptions>options=amsProblemDetails.getOptions();
        if(problem.getId()==null){
            problemsMapper.insertSelective(problem);
        }else {
            problemsMapper.updateByPrimaryKeySelective(problem);
        }
        if(images!=null&&images.size()>0)
            for(AmsProblemImages image:images){
                if (image.getId()==null){
                    image.setProblemId(problem.getId());
                    imagesMapper.insertSelective(image);
                }else{
                    imagesMapper.updateByPrimaryKeySelective(image);
                }
            }
        if(attachments!=null&&attachments.size()>0)
            for(AmsProblemAttachments attachment:attachments){
                if (attachment.getId()==null){
                    attachment.setProblemId(problem.getId());
                    attachmentsMapper.insertSelective(attachment);
                }else {
                    attachmentsMapper.updateByPrimaryKeySelective(attachment);
                }
            }
        if(options!=null&&options.size()>0)
            for(AmsProblemOptions option:options){
                if (option.getId()==null){
                    option.setProblemId(problem.getId());
                    optionsMapper.insertSelective(option);
                }else{
                    optionsMapper.updateByPrimaryKeySelective(option);
                }
            }
    }
    @Override
    public void deleteProblemDetails(Map<String, Long> idList) {
        if (idList.containsKey("image_id")){
            Long image_id=idList.get("image_id");
            if (image_id!=null)
                imagesMapper.deleteByPrimaryKey(image_id);
        }
        if (idList.containsKey("attachment_id")){
            Long attachment_id=idList.get("attachment_id");
            if (attachment_id!=null)
                attachmentsMapper.deleteByPrimaryKey(attachment_id);
        }
        if(idList.containsKey("option_id")){
            Long option_id=idList.get("option_id");
            if(option_id!=null)
                optionsMapper.deleteByPrimaryKey(option_id);
        }
        if (idList.containsKey("problem_id")){
            Long problem_id=idList.get("problem_id");
            if (problem_id!=null)
                problemsMapper.deleteByPrimaryKey(problem_id);
        }
    }

    @Override
    public List<AmsProblemDetails> getProblemList() {
        AmsProblemsExample example = new AmsProblemsExample();
        example.createCriteria();
        List<AmsProblems> problems= problemsMapper.selectByExampleWithBLOBs(example);
        List<AmsProblemDetails>results=new ArrayList<>();
        if(problems!=null&&problems.size()>0){
            List<AmsProblemImages>images;
            List<AmsProblemAttachments>attachments;
            List<AmsProblemOptions>options;
            for(AmsProblems problem:problems){
                Long id=problem.getId();
                AmsProblemImagesExample image_example=new AmsProblemImagesExample();
                AmsProblemAttachmentsExample attachments_example = new AmsProblemAttachmentsExample();
                AmsProblemOptionsExample options_example = new AmsProblemOptionsExample();
                image_example.createCriteria().andProblemIdEqualTo(id);
                attachments_example.createCriteria().andProblemIdEqualTo(id);
                options_example.createCriteria().andProblemIdEqualTo(id);
                images=imagesMapper.selectByExample(image_example);
                attachments=attachmentsMapper.selectByExample(attachments_example);
                options=optionsMapper.selectByExample(options_example);
                AmsProblemDetails amsProblemDetails=new AmsProblemDetails(problem,images,attachments,options);
                results.add(amsProblemDetails);
            }
        }
        return results;
    }

    @Override
    public AmsProblemDetails getProblem(Long id) {
        AmsProblems problem = problemsMapper.selectByPrimaryKey(id);
        AmsProblemImagesExample image_example=new AmsProblemImagesExample();
        AmsProblemAttachmentsExample attachments_example = new AmsProblemAttachmentsExample();
        AmsProblemOptionsExample options_example = new AmsProblemOptionsExample();
        image_example.createCriteria().andProblemIdEqualTo(id);
        attachments_example.createCriteria().andProblemIdEqualTo(id);
        options_example.createCriteria().andProblemIdEqualTo(id);
        List<AmsProblemImages>images=imagesMapper.selectByExample(image_example);
        List<AmsProblemAttachments>attachments=attachmentsMapper.selectByExample(attachments_example);
        List<AmsProblemOptions>options=optionsMapper.selectByExample(options_example);
        AmsProblemDetails result=new AmsProblemDetails(problem,images,attachments,options);
        return result;
    }

    @Override
    public void updateProblemsMassively(List<AmsProblemDetails>problemDetails) {
        for(AmsProblemDetails problemDetail:problemDetails){
            updateProblemDetails(problemDetail);
        }
    }
}
