package sast.onlineexams.dao;

import org.apache.ibatis.annotations.Mapper;
import sast.onlineexams.mbg.model.CmsAnswers;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-13 14:42
 * @description
 */
@Mapper
public interface CmsAnswerDispatchDao {
    List<CmsAnswers>getAnswers(Long problem_id,int num);
}
