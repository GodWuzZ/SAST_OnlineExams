package sast.onlineexams.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sherman
 * @create 2021-08-13 16:12
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
public class CmsProgress {
    private String GroupName;
    private int totalNum;
    private int markedNum;
    private double progress;
}
