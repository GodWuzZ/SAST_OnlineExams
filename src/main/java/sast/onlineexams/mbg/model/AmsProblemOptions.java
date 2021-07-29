package sast.onlineexams.mbg.model;

import java.io.Serializable;

public class AmsProblemOptions implements Serializable {
    private Long id;

    private Long problemId;

    /**
     * 选项内容
     *
     * @mbg.generated
     */
    private String content;

    /**
     * 选项，选择题是A,B,C,...;判断题是true/false
     *
     * @mbg.generated
     */
    private String option;

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

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
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
        sb.append(", option=").append(option);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}