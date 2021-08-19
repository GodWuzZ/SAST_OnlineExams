package sast.onlineexams.mbg.model;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

public class AmsProblemOptions implements Serializable {
    @JsonView(AmsProblems.ProblemSimpleView.class)
    private Long id;

    @JsonView(AmsProblems.ProblemSimpleView.class)
    private Long problemId;

    /**
     * 选项内容
     *
     * @mbg.generated
     */
    @JsonView(AmsProblems.ProblemSimpleView.class)
    private String content;

    /**
     * 选项，选择题是A,B,C,...;判断题是true/false
     *
     * @mbg.generated
     */
    @JsonView(AmsProblems.ProblemSimpleView.class)
    private String value;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", problemId=").append(problemId);
        sb.append(", content=").append(content);
        sb.append(", value=").append(value);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}