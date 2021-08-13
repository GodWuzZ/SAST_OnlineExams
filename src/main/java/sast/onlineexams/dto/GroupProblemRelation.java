package sast.onlineexams.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sast.onlineexams.mbg.model.UmsGroups;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-13 13:27
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
public class GroupProblemRelation {
    private UmsGroups group;
    private List<Long>problem_id;
}
