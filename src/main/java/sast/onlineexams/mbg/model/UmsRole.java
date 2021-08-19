package sast.onlineexams.mbg.model;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.Date;

public class UmsRole implements Serializable {
    @JsonView(UmsAdmin.AdminSimpleView.class)
    private Long id;

    /**
     * 角色名称
     *
     * @mbg.generated
     */
    @JsonView(UmsAdmin.AdminSimpleView.class)
    private String name;

    /**
     * 角色描述
     *
     * @mbg.generated
     */
    @JsonView(UmsAdmin.AdminDetailView.class)
    private String description;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    @JsonView(UmsAdmin.AdminDetailView.class)
    private Date createTime;

    /**
     * 启用状态：0->禁用；1->启用
     *
     * @mbg.generated
     */
    @JsonView(UmsAdmin.AdminDetailView.class)
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}