package cn.jlw.firelearning.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.entity.UserStageLearn;
import cn.jlw.firelearning.mapper.ExercisesOptionMapper;
import cn.jlw.firelearning.mapper.StageInfoMapper;
import cn.jlw.firelearning.mapper.UserStageLearnMapper;
import cn.jlw.firelearning.model.dto.CommitLearnTestDTO;
import cn.jlw.firelearning.model.dto.ListLearnCurrentTestDTO;
import cn.jlw.firelearning.model.dto.ListStageLearnInfoDTO;
import cn.jlw.firelearning.model.vo.ExercisesOptionsListVO;
import cn.jlw.firelearning.model.vo.ListLearnCurrentTestOptionsVO;
import cn.jlw.firelearning.model.vo.ListLearnCurrentTestVO;
import cn.jlw.firelearning.model.vo.ListStageLearnInfoVO;
import cn.jlw.firelearning.service.UserStageLearnService;
import cn.jlw.firelearning.utils.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class UserStageLearnServiceImpl extends ServiceImpl<UserStageLearnMapper, UserStageLearn> implements UserStageLearnService {
    private final StageInfoMapper stageInfoMapper;
    private final ExercisesOptionMapper exercisesOptionMapper;

    @Override
    public List<ListStageLearnInfoVO> listStageLearnInfo(ListStageLearnInfoDTO content) {
        List<ListStageLearnInfoVO> resultList = new ArrayList<>();
        //获取所有阶段
        List<StageInfo> stageInfoList = stageInfoMapper.selectList(Wrappers.lambdaQuery(StageInfo.class).eq(StageInfo::getIfPublish, 1));
        for (StageInfo stageInfo : stageInfoList) {
            ListStageLearnInfoVO result = new ListStageLearnInfoVO();
            BeanUtil.copyProperties(stageInfo, result);
            //计算当前用户当前阶段进度:不为空的数量除以30(题目数量)
            Integer notNullNum = baseMapper.selectCount(Wrappers.lambdaQuery(UserStageLearn.class)
                    .eq(UserStageLearn::getStageNum, stageInfo.getStageNum())
                    .eq(UserStageLearn::getUsername, content.getUsername())
                    .isNotNull(UserStageLearn::getUserAnswer));
            BigDecimal progress = new BigDecimal(notNullNum).divide(new BigDecimal(30), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            result.setProgress(progress);
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public List<ListLearnCurrentTestVO> listLearnCurrentTest(ListLearnCurrentTestDTO content) {
        QueryWrapper<UserStageLearn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.username", content.getUsername())
                .eq("a.stage_num", content.getStageNum());
        List<ListLearnCurrentTestVO> resultList = baseMapper.listLearnCurrentTest(queryWrapper);

        //处理单选答案和多选答案返回
        for (ListLearnCurrentTestVO listLearnCurrentTestVO : resultList) {
            String userAnswer = listLearnCurrentTestVO.getUserAnswer();
            if (listLearnCurrentTestVO.getExercisesType().equals("1")) {
                listLearnCurrentTestVO.setUserRadioAnswer(userAnswer);
            } else if (listLearnCurrentTestVO.getExercisesType().equals("2")) {
                if (StrUtil.isNotBlank(userAnswer)) {
                    //拆解答案 多选是A-B
                    List<String> userMultiAnswer = new ArrayList<>();
                    String[] split = userAnswer.split("-");
                    for (String s : split) {
                        userMultiAnswer.add(s);
                    }
                    listLearnCurrentTestVO.setUserMultiAnswer(userMultiAnswer);
                }
            }

            //获取选项
            List<ExercisesOptionsListVO> exercisesOptionsListVOS = exercisesOptionMapper.listOptionsByExercisesNum(listLearnCurrentTestVO.getExercisesNum());
            List<ListLearnCurrentTestOptionsVO> optionsList = MyUtils.copyList(exercisesOptionsListVOS, ListLearnCurrentTestOptionsVO.class);
            listLearnCurrentTestVO.setOptionsList(optionsList);

            //返回正确答案
            String rightAnswer = "";
            for (ListLearnCurrentTestOptionsVO listLearnCurrentTestOptionsVO : optionsList) {
                if (listLearnCurrentTestOptionsVO.getIfRight().equals("1")) {
                    rightAnswer += listLearnCurrentTestOptionsVO.getOptionNum();
                }
            }
            if (StrUtil.isNotBlank(userAnswer)) {
                listLearnCurrentTestVO.setIfAnswer(1);
            }
            listLearnCurrentTestVO.setRightAnswer(rightAnswer);

            //比较回答是否是正确答案
            if (StrUtil.isNotBlank(userAnswer) && StrUtil.isNotBlank(rightAnswer)) {
                String tempAnswer = userAnswer.replace("-", "");
                if (MyUtils.compareTwoStrChar(tempAnswer, rightAnswer)) {
                    listLearnCurrentTestVO.setIfAnswerRight(1);
                }
            }
        }
        return resultList;
    }

    @Override
    public void commitLearnTest(CommitLearnTestDTO content) {
        baseMapper.update(null, Wrappers.lambdaUpdate(UserStageLearn.class)
                .eq(UserStageLearn::getId, content.getId())
                .set(UserStageLearn::getUserAnswer, content.getUserAnswer()));
    }


}
