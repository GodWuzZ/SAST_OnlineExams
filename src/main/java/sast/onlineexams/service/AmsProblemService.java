package sast.onlineexams.service;

import sast.onlineexams.dto.AmsProblemDetails;

import java.util.List;
import java.util.Map;

/**
 * @author sherman
 * @create 2021-08-12 13:34
 * @description
 */
public interface AmsProblemService {
    void updateProblemDetails(AmsProblemDetails amsProblemDetails);
    void deleteProblemDetails(Map<String,Long>idList);
    List<AmsProblemDetails>getProblemList();
    AmsProblemDetails getProblem(Long id);
    void updateProblemsMassively(List<AmsProblemDetails>problemDetails);
}
