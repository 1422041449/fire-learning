package cn.jlw.firelearning.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 注册用户入参
 *
 * @author jlw
 * @since 2021-10-30
 */
@Data
public class UserInfoListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 真实姓名
     */
    private String realName;
}
