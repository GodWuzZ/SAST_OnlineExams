package sast.onlineexams.dto;

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
    private AmsProblems problem;
    private List<AmsProblemImages>images;
    private List<AmsProblemAttachments>attachments;
    private List<AmsProblemOptions>options;

}
