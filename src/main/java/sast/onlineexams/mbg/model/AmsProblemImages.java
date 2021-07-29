package sast.onlineexams.mbg.model;

import java.io.Serializable;

public class AmsProblemImages implements Serializable {
    private Long id;

    /**
     * 图片对应的problem的id
     *
     * @mbg.generated
     */
    private Long problemId;

    /**
     * 可以给图片起一个好听的名字或者图片标号之类的
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 图片的地址
     *
     * @mbg.generated
     */
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