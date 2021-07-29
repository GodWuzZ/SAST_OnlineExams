package sast.onlineexams.mbg.model;

import java.io.Serializable;
import java.util.Date;

public class CmsAnswerDispatches implements Serializable {
    private Long id;

    private Long answerId;

    private Long problemId;

    private Long userId;

    /**
     * 0代表未批改，1代表已批改
     *
     * @mbg.generated
     */
    private Boolean solved;

    private Integer score;

    private Date dispatchedAt;

    private Date expiredAt;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getDispatchedAt() {
        return dispatchedAt;
    }

    public void setDispatchedAt(Date dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", answerId=").append(answerId);
        sb.append(", problemId=").append(problemId);
        sb.append(", userId=").append(userId);
        sb.append(", solved=").append(solved);
        sb.append(", score=").append(score);
        sb.append(", dispatchedAt=").append(dispatchedAt);
        sb.append(", expiredAt=").append(expiredAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}