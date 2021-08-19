package sast.onlineexams.mbg.model;

import com.fasterxml.jackson.annotation.JsonView;
import sast.onlineexams.common.api.CommonResult;

import java.io.Serializable;

public class AmsProblems implements Serializable {
    public interface ProblemSimpleView extends CommonResult.CommonResultView {};
    public interface ProblemDetailView extends ProblemSimpleView{};
    @JsonView(ProblemSimpleView.class)
    private Long id;

    /**
     * 题目标题
     *
     * @mbg.generated
     */
    @JsonView(ProblemSimpleView.class)
    private String title;

    /**
     * 题目类型：0是选择题，1是判断题，2是解答题
     *
     * @mbg.generated
     */
    @JsonView(ProblemSimpleView.class)
    private Integer type;

    /**
     * 题目分数
     *
     * @mbg.generated
     */
    @JsonView(ProblemSimpleView.class)
    private Integer maxScore;

    /**
     * 所属小组
     *
     * @mbg.generated
     */
    @JsonView(ProblemDetailView.class)
    private Long groupId;

    /**
     * 题目内容
     *
     * @mbg.generated
     */
    @JsonView(ProblemSimpleView.class)
    private String content;

    /**
     * 题目标准答案
     *
     * @mbg.generated
     */
    @JsonView(ProblemDetailView.class)
    private String standardAnswer;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStandardAnswer() {
        return standardAnswer;
    }

    public void setStandardAnswer(String standardAnswer) {
        this.standardAnswer = standardAnswer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", type=").append(type);
        sb.append(", maxScore=").append(maxScore);
        sb.append(", groupId=").append(groupId);
        sb.append(", content=").append(content);
        sb.append(", standardAnswer=").append(standardAnswer);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}