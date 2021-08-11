package sast.onlineexams.mbg.model;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.Date;

public class UmsAdmin implements Serializable {
    public interface AdminSimpleView{};
    public interface AdminDetailView extends AdminSimpleView{};
    public interface AdminMoreDetailView extends AdminDetailView{};
    @JsonView(AdminDetailView.class)
    private Long id;

    /**
     * 用户名
     *
     * @mbg.generated
     */
    @JsonView(AdminSimpleView.class)
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
    @JsonView(AdminMoreDetailView.class)
    private Date createTime;

    /**
     * 最后登录时间
     *
     * @mbg.generated
     */
    @JsonView(AdminMoreDetailView.class)
    private Date loginTime;

    /**
     * 账号启用状态,0->禁用;1->启用
     *
     * @mbg.generated
     */
    @JsonView(AdminSimpleView.class)
    private Integer status;

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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}