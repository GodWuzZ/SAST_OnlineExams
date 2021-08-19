package sast.onlineexams.mbg.model;

import com.fasterxml.jackson.annotation.JsonView;
import sast.onlineexams.common.api.CommonResult;

import java.io.Serializable;

public class UmsStudent implements Serializable {

    public interface StudentSimpleView extends CommonResult.CommonResultView {};
    public interface StudentDetailView extends StudentSimpleView{};
    @JsonView(StudentDetailView.class)
    private Long id;

    /**
     * 学号
     *
     * @mbg.generated
     */
    @JsonView(StudentSimpleView.class)
    private String number;

    /**
     * 姓名
     *
     * @mbg.generated
     */
    @JsonView(StudentSimpleView.class)
    private String name;

    /**
     * 用户名
     *
     * @mbg.generated
     */
    @JsonView(StudentSimpleView.class)
    private String username;

    /**
     * 密码
     *
     * @mbg.generated
     */
    private String password;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", number=").append(number);
        sb.append(", name=").append(name);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}