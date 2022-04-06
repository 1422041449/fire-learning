package cn.jlw.firelearning.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.jlw.firelearning.entity.*;
import cn.jlw.firelearning.exception.LeException;
import cn.jlw.firelearning.mapper.StageInfoMapper;
import cn.jlw.firelearning.mapper.StageLearnMapper;
import cn.jlw.firelearning.mapper.StageTestMapper;
import cn.jlw.firelearning.mapper.UserInfoMapper;
import cn.jlw.firelearning.model.dto.StageInfoAddDTO;
import cn.jlw.firelearning.model.dto.StageInfoEditDTO;
import cn.jlw.firelearning.model.dto.StageInfoListDTO;
import cn.jlw.firelearning.model.vo.StageInfoListVO;
import cn.jlw.firelearning.service.StageInfoService;
import cn.jlw.firelearning.service.UserStageLearnService;
import cn.jlw.firelearning.service.UserStageTestService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * <p>
 * 阶段信息表 服务实现类
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Service
@RequiredArgsConstructor
public class StageInfoServiceImpl extends ServiceImpl<StageInfoMapper, StageInfo> implements StageInfoService {
    private final StageLearnMapper stageLearnMapper;
    private final StageTestMapper stageTestMapper;
    private final UserStageLearnService userStageLearnService;
    private final UserStageTestService userStageTestService;
    private final UserInfoMapper userInfoMapper;


    @Override
    public void addStageInfo(StageInfoAddDTO content) {
        StageInfo stageInfo = new StageInfo();
        BeanUtil.copyProperties(content, stageInfo);
        baseMapper.insert(stageInfo);
    }

    @Override
    public List<StageInfoListVO> listStageInfo(StageInfoListDTO content) {
        LambdaQueryWrapper<StageInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(content.getStageName())) {
            lambdaQueryWrapper.like(StageInfo::getStageName, content.getStageName());
        }
        if (StrUtil.isNotBlank(content.getStageTitle())) {
            lambdaQueryWrapper.like(StageInfo::getStageTitle, content.getStageTitle());
        }
        if (null != content.getIfPublish()) {
            lambdaQueryWrapper.eq(StageInfo::getIfPublish, content.getIfPublish());
        }
        return baseMapper.listStageInfo(lambdaQueryWrapper);
    }

    @Override
    public void editStageInfo(StageInfoEditDTO content) {
        StageInfo stageInfo = new StageInfo();
        BeanUtil.copyProperties(content, stageInfo);
        baseMapper.update(stageInfo, Wrappers.lambdaQuery(StageInfo.class)
                .eq(StageInfo::getStageNum, content.getStageNum()));
    }

    @Override
    public void deleteStageInfo(Integer stageNum) {
        baseMapper.delete(Wrappers.lambdaQuery(StageInfo.class)
                .eq(StageInfo::getStageNum, stageNum));
    }

    @Override
    public void publishStageInfo(Integer stageNum) {
        //校验考试题目是否创建完毕
        int checkScore = stageTestMapper.publishStageInfo(stageNum);
        if (checkScore != 100) {
            throw new LeException("考试题目为创建完成，不可发布!");
        }

        //发布阶段
        baseMapper.update(null, Wrappers.lambdaUpdate(StageInfo.class)
                .eq(StageInfo::getStageNum, stageNum)
                .set(StageInfo::getIfPublish, 1));

        ExecutorService executorService = ThreadUtil.newSingleExecutor();
        executorService.execute(() -> {
            //异步处理-给当前所有用户添加用户阶段学习表和用户阶段考试表数据
            List<UserStageLearn> userStageLearnList = new ArrayList<>();
            List<UserStageTest> userStageTestList = new ArrayList<>();
            //获取当前所有用户
            List<UserInfo> userInfoList = userInfoMapper.selectList(Wrappers.lambdaQuery(UserInfo.class)
                    .eq(UserInfo::getRole, "user"));
            //获取当前阶段所有阶段学习题
            List<StageLearn> stageLearnList = stageLearnMapper.selectList(Wrappers.lambdaQuery(StageLearn.class)
                    .eq(StageLearn::getStageNum, stageNum));
            //获取当前阶段所有阶段考试题
            List<StageTest> stageTestList = stageTestMapper.selectList(Wrappers.lambdaQuery(StageTest.class)
                    .eq(StageTest::getStageNum, stageNum));
            //为所有用户赋值
            for (UserInfo userInfo : userInfoList) {
                String username = userInfo.getUsername();
                for (StageLearn stageLearn : stageLearnList) {
                    UserStageLearn userStageLearn = new UserStageLearn();
                    userStageLearn.setUsername(username);
                    userStageLearn.setStageLearnId(stageLearn.getStageLearnId());
                    userStageLearnList.add(userStageLearn);
                }
                for (StageTest stageTest : stageTestList) {
                    UserStageTest userStageTest = new UserStageTest();
                    userStageTest.setUsername(username);
                    userStageTest.setStageTestId(stageTest.getStageTestId());
                    userStageTestList.add(userStageTest);
                }
            }
            userStageLearnService.saveBatch(userStageLearnList);
            userStageTestService.saveBatch(userStageTestList);
        });
    }

    @Override
    public void checkPublish(Integer stageNum) {
        Integer checkPublish = baseMapper.selectCount(Wrappers.lambdaQuery(StageInfo.class)
                .eq(StageInfo::getStageNum, stageNum)
                .eq(StageInfo::getIfPublish, 1));
        if (checkPublish > 0) {
            throw new LeException("阶段已发布，不可操作!");
        }
    }
}
