package cn.jlw.firelearning.service;

import cn.jlw.firelearning.entity.UserInfo;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.dto.UserInfoAddDTO;
import cn.jlw.firelearning.model.dto.UserInfoListDTO;
import cn.jlw.firelearning.model.dto.UserInfoLoginDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jlw
 * @since 2021-10-23
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 登录
     *
     * @param content
     * @return
     */
    LeResponse<?> login(UserInfoLoginDTO content);

    /**
     * 根据token获取用户信息
     *
     * @return
     */
    LeResponse<UserInfo> getUserInfo(String token);

    /**
     * 注册用户
     */
    LeResponse<?> registerUser(UserInfoAddDTO content);

    /**
     * 获取用户信息
     */
    List<UserInfo> listUserInfo(UserInfoListDTO content);

    /**
     * 根据用户名删除用户
     */
    void deleteUserInfo(String username);

    /**
     * 根据用户名修改用户信息
     */
    void updateUserInfo(UserInfo content);
}
