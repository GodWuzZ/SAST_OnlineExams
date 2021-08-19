package sast.onlineexams.mbg.model;

import com.fasterxml.jackson.annotation.JsonView;
import sast.onlineexams.common.api.CommonResult;

import java.io.Serializable;
import java.util.Date;

public class UmsAdmin implements Serializable {
    public interface AdminSimpleView extends CommonResult.CommonResultView {};
    public interface AdminDetailView extends AdminSimpleView{};
    public interface AdminMoreDetailView extends AdminDetailView{};
    @JsonView({AdminSimpleView.class})
    private Long id;

    /**
     * 用户名
     *
     * @mbg.generated
     */
    @JsonView({AdminSimpleView.class})
    private String username;

    /**
     * 密码
     *
     * @mbg.generated
     */
    private String password;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    @JsonView({AdminDetailView.class})
    private Date createTime;

    /**
     * 最后登录时间
     *
     * @mbg.generated
     */
    @JsonView({AdminDetailView.class})
    private Date loginTime;

    /**
     * 账号启用状态,0->禁用;1->启用
     *
     * @mbg.generated
     */
    @JsonView({AdminSimpleView.class})
    private Integer status;

    /**
     * 所属小组的id
     *
     * @mbg.generated
     */
    @JsonView({AdminSimpleView.class})
    private Long groupId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", createTime=").append(createTime);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", status=").append(status);
        sb.append(", groupId=").append(groupId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}