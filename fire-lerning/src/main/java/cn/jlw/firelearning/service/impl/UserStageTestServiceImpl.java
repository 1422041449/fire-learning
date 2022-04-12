package cn.jlw.firelearning.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.jlw.firelearning.entity.ExercisesOption;
import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.entity.UserStageLearn;
import cn.jlw.firelearning.entity.UserStageTest;
import cn.jlw.firelearning.mapper.ExercisesOptionMapper;
import cn.jlw.firelearning.mapper.StageInfoMapper;
import cn.jlw.firelearning.mapper.UserStageTestMapper;
import cn.jlw.firelearning.model.dto.CommitTestAnswerDTO;
import cn.jlw.firelearning.model.dto.ListLearnCurrentTestDTO;
import cn.jlw.firelearning.model.dto.ListStageLearnInfoDTO;
import cn.jlw.firelearning.model.model.UserLearnTestAnswerModel;
import cn.jlw.firelearning.model.vo.*;
import cn.jlw.firelearning.service.UserStageTestService;
import cn.jlw.firelearning.utils.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户阶段信息表 服务实现类
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Service
@RequiredArgsConstructor
public class UserStageTestServiceImpl extends ServiceImpl<UserStageTestMapper, UserStageTest> implements UserStageTestService {
    private final StageInfoMapper stageInfoMapper;
    private final ExercisesOptionMapper exercisesOptionMapper;

    @Override
    public List<ListStageTestInfoVO> listStageTestInfo(ListStageLearnInfoDTO content) {
        List<ListStageTestInfoVO> resultList = new ArrayList<>();
        //获取所有阶段
        List<StageInfo> stageInfoList = stageInfoMapper.selectList(Wrappers.lambdaQuery(StageInfo.class).eq(StageInfo::getIfPublish, 1));
        for (StageInfo stageInfo : stageInfoList) {
            ListStageTestInfoVO result = new ListStageTestInfoVO();
            BeanUtil.copyProperties(stageInfo, result);
            //计算当前用户当前阶段考试成绩
            QueryWrapper<UserStageTest> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("a.stage_num", stageInfo.getStageNum())
                    .eq("a.username", content.getUsername());
            List<UserLearnTestAnswerModel> userLearnTestAnswerModelList = baseMapper.listUserAnswerRes(queryWrapper);
            Integer grade = 0;
            Integer ifFinish = 2;
            for (UserLearnTestAnswerModel userLearnTestAnswerModel : userLearnTestAnswerModelList) {
                if (null == userLearnTestAnswerModel.getAnswerResult()) {
                    break;
                }
                ifFinish = 1;
                //单选+3分
                if (userLearnTestAnswerModel.getExercisesType() == 1) {
                    grade += 3;
                }//多选全对+4分
                else if (userLearnTestAnswerModel.getExercisesType() == 2 && userLearnTestAnswerModel.getAnswerResult() == 1) {
                    grade += 4;
                }//多选部分对+2分
                else if (userLearnTestAnswerModel.getExercisesType() == 2 && userLearnTestAnswerModel.getAnswerResult() == 3) {
                    grade += 2;
                }
            }
            result.setIfFinish(ifFinish);
            result.setGrade(grade);
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public List<ListTestCurrentTestVO> listTestCurrentTest(ListLearnCurrentTestDTO content) {
        QueryWrapper<UserStageTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.username", content.getUsername())
                .eq("a.stage_num", content.getStageNum());
        List<ListTestCurrentTestVO> resultList = baseMapper.listTestCurrentTest(queryWrapper);

        //处理单选答案和多选答案返回
        for (ListTestCurrentTestVO listTestCurrentTestVO : resultList) {
            //获取选项
            List<ExercisesOptionsListVO> exercisesOptionsListVOS = exercisesOptionMapper.listOptionsByExercisesNum(listTestCurrentTestVO.getExercisesNum());
            List<ListTestCurrentTestOptionsVO> optionsList = MyUtils.copyList(exercisesOptionsListVOS, ListTestCurrentTestOptionsVO.class);
            listTestCurrentTestVO.setOptionsList(optionsList);
        }
        return resultList;
    }

    @Override
    public void commitTestAnswer(List<CommitTestAnswerDTO> contentList) {
        for (CommitTestAnswerDTO content : contentList) {
            //单选
            Integer answerResult = 2;
            if (content.equals("1")) {
                //根据题目编号获取此题的正确答案
                ExercisesOption exercisesOption = exercisesOptionMapper.selectOne(Wrappers.lambdaQuery(ExercisesOption.class)
                        .eq(ExercisesOption::getExercisesNum, content.getExercisesNum())
                        .eq(ExercisesOption::getIfRight, 1));
                if (exercisesOption.getOptionNum().equals(content.getUserAnswer())) {
                    answerResult = 1;
                }
            }//多选
            else {
                //获取多选正确答案选项字符串集合
                List<ExercisesOption> exercisesOptionList = exercisesOptionMapper.selectList(Wrappers.lambdaQuery(ExercisesOption.class)
                        .eq(ExercisesOption::getExercisesNum, content.getExercisesNum())
                        .eq(ExercisesOption::getIfRight, 1));
                String rightStr = "";
                for (ExercisesOption exercisesOption : exercisesOptionList) {
                    rightStr += exercisesOption.getOptionNum();
                }
                //获取用户答案字符串集合
                String userAnswerStr = content.getUserAnswer().replace("-", "");
                answerResult = MyUtils.containStr(rightStr, userAnswerStr);
            }
            //更改用户阶段考试表信息
            baseMapper.update(null, Wrappers.lambdaUpdate(UserStageTest.class)
                    .eq(UserStageTest::getId, content.getId())
                    .set(UserStageTest::getUserAnswer, content.getUserAnswer())
                    .set(UserStageTest::getAnswerResult, answerResult));
        }
    }

    @Override
    public List<ListTestCurrentTestVO> listTestCurrentTestDetail(ListLearnCurrentTestDTO content) {
        QueryWrapper<UserStageTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.username", content.getUsername())
                .eq("a.stage_num", content.getStageNum());
        List<ListTestCurrentTestVO> resultList = baseMapper.listTestCurrentTest(queryWrapper);

        //处理单选答案和多选答案返回
        for (ListTestCurrentTestVO listTestCurrentTestVO : resultList) {
            String userAnswer = listTestCurrentTestVO.getUserAnswer();
            if (listTestCurrentTestVO.getExercisesType().equals("1")) {
                listTestCurrentTestVO.setUserRadioAnswer(userAnswer);
            } else if (listTestCurrentTestVO.getExercisesType().equals("2")) {
                if (StrUtil.isNotBlank(userAnswer)) {
                    //拆解答案 多选是A-B
                    List<String> userMultiAnswer = new ArrayList<>();
                    String[] split = userAnswer.split("-");
                    for (String s : split) {
                        userMultiAnswer.add(s);
                    }
                    listTestCurrentTestVO.setUserMultiAnswer(userMultiAnswer);
                }
            }

            //获取选项
            List<ExercisesOptionsListVO> exercisesOptionsListVOS = exercisesOptionMapper.listOptionsByExercisesNum(listTestCurrentTestVO.getExercisesNum());
            List<ListTestCurrentTestOptionsVO> optionsList = MyUtils.copyList(exercisesOptionsListVOS, ListTestCurrentTestOptionsVO.class);
            listTestCurrentTestVO.setOptionsList(optionsList);

            //返回正确答案
            String rightAnswer = "";
            for (ListTestCurrentTestOptionsVO listTestCurrentTestOptionsVO : optionsList) {
                if (listTestCurrentTestOptionsVO.getIfRight().equals("1")) {
                    rightAnswer += listTestCurrentTestOptionsVO.getOptionNum();
                }
            }
            if (StrUtil.isNotBlank(userAnswer)) {
                listTestCurrentTestVO.setIfAnswer(1);
            }
            listTestCurrentTestVO.setRightAnswer(rightAnswer);

            //比较回答是否是正确答案
            if (StrUtil.isNotBlank(userAnswer) && StrUtil.isNotBlank(rightAnswer)) {
                String tempAnswer = userAnswer.replace("-", "");
                if (MyUtils.compareTwoStrChar(tempAnswer, rightAnswer)) {
                    listTestCurrentTestVO.setIfAnswerRight(1);
                }
            }
        }
        return resultList;
    }
}
