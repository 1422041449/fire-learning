package cn.jlw.firelearning.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.jlw.firelearning.entity.*;
import cn.jlw.firelearning.exception.LeException;
import cn.jlw.firelearning.mapper.StageInfoMapper;
import cn.jlw.firelearning.mapper.StageLearnMapper;
import cn.jlw.firelearning.mapper.StageTestMapper;
import cn.jlw.firelearning.mapper.UserInfoMapper;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.dto.UserInfoAddDTO;
import cn.jlw.firelearning.model.dto.UserInfoListDTO;
import cn.jlw.firelearning.model.dto.UserInfoLoginDTO;
import cn.jlw.firelearning.service.TokenService;
import cn.jlw.firelearning.service.UserInfoService;
import cn.jlw.firelearning.service.UserStageLearnService;
import cn.jlw.firelearning.service.UserStageTestService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jlw
 * @since 2021-10-23
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    private final TokenService tokenService;
    private final StageLearnMapper stageLearnMapper;
    private final StageTestMapper stageTestMapper;
    private final UserStageLearnService userStageLearnService;
    private final UserStageTestService userStageTestService;
    private final StageInfoMapper stageInfoMapper;

    @Override
    public LeResponse<?> login(UserInfoLoginDTO content) {
        UserInfo userInfo = baseMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
                .eq(UserInfo::getUsername, content.getUsername())
                .eq(UserInfo::getPassword, content.getPassword()));
        if (ObjectUtil.isEmpty(userInfo)) {
            return LeResponse.fail("用户名或密码错误!");
        }
        String token = tokenService.getToken(userInfo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        return LeResponse.succ(jsonObject);
    }

    @Override
    public LeResponse<UserInfo> getUserInfo(String token) {
        //获取token中的id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new LeException("token获取用户失败!");
        }
        UserInfo userInfo = baseMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class).eq(UserInfo::getId, userId));
        if (ObjectUtil.isEmpty(userInfo)) {
            return LeResponse.fail("用户不存在!");
        }
        return LeResponse.succ(userInfo);
    }

    @Override
    public LeResponse<?> registerUser(UserInfoAddDTO content) {
        //检验该手机号是否已存在
        UserInfo checkUser = baseMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
                .eq(UserInfo::getPhone, content.getPhone())
                .select(UserInfo::getPhone));
        if (ObjectUtil.isNotEmpty(checkUser)) {
            return LeResponse.fail("该手机号已被注册!");
        }
        //校验昵称
        UserInfo checkUser2 = baseMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
                .eq(UserInfo::getPhone, content.getName())
                .select(UserInfo::getName));
        if (ObjectUtil.isNotEmpty(checkUser2)) {
            return LeResponse.fail("该昵称已存在!");
        }
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(content, userInfo);
        userInfo.setUsername(content.getPhone());
        userInfo.setRole("user");
        baseMapper.insert(userInfo);

        //异步-注册用户的同时会生成用户学习阶段题和用户阶段考试题
        ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(8, 16);
        threadPoolExecutor.execute(() -> {
            List<UserStageLearn> userStageLearnList = new ArrayList<>();
            List<UserStageTest> userStageTestList = new ArrayList<>();
            //获取当前所有已发布阶段
            List<StageInfo> stageInfoList = stageInfoMapper.selectList(Wrappers.lambdaQuery(StageInfo.class)
                    .eq(StageInfo::getIfPublish, 1)
                    .select(StageInfo::getStageNum));
            List<Integer> stageNumList = stageInfoList.stream().map(StageInfo::getStageNum).collect(Collectors.toList());
            List<StageLearn> stageLearnList = stageLearnMapper.selectList(Wrappers.lambdaQuery(StageLearn.class)
                    .in(StageLearn::getStageNum, stageNumList));
            for (StageLearn stageLearn : stageLearnList) {
                UserStageLearn userStageLearn = new UserStageLearn();
                BeanUtil.copyProperties(stageLearn, userStageLearn);
                userStageLearn.setUsername(userInfo.getUsername());
                userStageLearnList.add(userStageLearn);
            }
            List<StageTest> stageTestList = stageTestMapper.selectList(Wrappers.lambdaQuery(StageTest.class)
                    .in(StageTest::getStageNum, stageNumList));
            for (StageTest stageTest : stageTestList) {
                UserStageTest userStageTest = new UserStageTest();
                BeanUtil.copyProperties(stageTest, userStageTest);
                userStageTest.setUsername(userInfo.getUsername());
                userStageTestList.add(userStageTest);
            }
            userStageLearnService.saveBatch(userStageLearnList);
            userStageTestService.saveBatch(userStageTestList);
        });
        return LeResponse.succ();
    }

    @Override
    public List<UserInfo> listUserInfo(UserInfoListDTO content) {
        //查询所有用户
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getRole, "user");
        if (StrUtil.isNotBlank(content.getName())) {
            lambdaQueryWrapper.like(UserInfo::getName, content.getName());
        }
        if (StrUtil.isNotBlank(content.getRealName())) {
            lambdaQueryWrapper.like(UserInfo::getRealName, content.getRealName());
        }
        if (StrUtil.isNotBlank(content.getPhone())) {
            lambdaQueryWrapper.eq(UserInfo::getPhone, content.getPhone());
        }
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public void deleteUserInfo(String username) {
        baseMapper.delete(Wrappers.lambdaQuery(UserInfo.class)
                .eq(UserInfo::getUsername, username));
    }

    @Override
    public void updateUserInfo(UserInfo content) {
        baseMapper.update(content, Wrappers.lambdaUpdate(UserInfo.class)
                .eq(UserInfo::getUsername, content.getUsername()));
    }
}
