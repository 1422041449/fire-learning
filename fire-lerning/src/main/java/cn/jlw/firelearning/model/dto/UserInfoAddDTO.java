package cn.jlw.firelearning.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 注册用户入参
 *
 * @author jlw
 * @since 2021-10-30
 */
@Data
public class UserInfoAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空!")
    private String password;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空!")
    private String name;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空!")
    private String phone;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar;
}
