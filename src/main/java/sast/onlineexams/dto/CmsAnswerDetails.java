package sast.onlineexams.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sast.onlineexams.mbg.model.CmsAnswers;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-13 13:48
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
public class CmsAnswerDetails {
    private AmsProblemDetails problemDetails;
    private List<CmsAnswers> answers;
}
