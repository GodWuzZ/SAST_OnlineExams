package sast.onlineexams.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sast.onlineexams.mbg.model.AmsProblemAttachments;
import sast.onlineexams.mbg.model.AmsProblemImages;
import sast.onlineexams.mbg.model.AmsProblemOptions;
import sast.onlineexams.mbg.model.AmsProblems;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-12 13:48
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
public class AmsProblemDetails {
    @JsonView({AmsProblems.ProblemSimpleView.class, AmsProblems.ProblemDetailView.class})
    private AmsProblems problem;
    @JsonView(AmsProblems.ProblemSimpleView.class)
    private List<AmsProblemImages>images;
    @JsonView(AmsProblems.ProblemSimpleView.class)
    private List<AmsProblemAttachments>attachments;
    @JsonView(AmsProblems.ProblemSimpleView.class)
    private List<AmsProblemOptions>options;

}
