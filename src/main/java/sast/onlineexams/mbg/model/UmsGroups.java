package sast.onlineexams.mbg.model;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

public class UmsGroups implements Serializable {
    @JsonView(UmsAdmin.AdminSimpleView.class)
    private Long id;

    /**
     * 所属组别或团队名称
     *
     * @mbg.generated
     */
    @JsonView(UmsAdmin.AdminSimpleView.class)
    private String name;

    /**
     * 小组描述
     *
     * @mbg.generated
     */
    private String description;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}