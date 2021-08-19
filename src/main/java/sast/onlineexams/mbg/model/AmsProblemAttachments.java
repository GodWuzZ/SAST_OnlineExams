package sast.onlineexams.mbg.model;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

public class AmsProblemAttachments implements Serializable {
    @JsonView(AmsProblems.ProblemSimpleView.class)
    private Long id;

    /**
     * 附件对应的problem的id
     *
     * @mbg.generated
     */
    @JsonView(AmsProblems.ProblemSimpleView.class)
    private Long problemId;

    /**
     * 附件的名称
     *
     * @mbg.generated
     */
    @JsonView(AmsProblems.ProblemSimpleView.class)
    private String name;

    /**
     * 附件的位置
     *
     * @mbg.generated
     */
    @JsonView(AmsProblems.ProblemSimpleView.class)
    private String url;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", problemId=").append(problemId);
        sb.append(", name=").append(name);
        sb.append(", url=").append(url);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}