package cn.jlw.firelearning.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.jlw.firelearning.entity.ExercisesInfo;
import cn.jlw.firelearning.entity.ExercisesOption;
import cn.jlw.firelearning.entity.StageLearn;
import cn.jlw.firelearning.exception.LeException;
import cn.jlw.firelearning.mapper.ExercisesInfoMapper;
import cn.jlw.firelearning.mapper.ExercisesOptionMapper;
import cn.jlw.firelearning.mapper.StageLearnMapper;
import cn.jlw.firelearning.mapper.StageTestMapper;
import cn.jlw.firelearning.model.dto.*;
import cn.jlw.firelearning.model.vo.ExercisesInfoListVO;
import cn.jlw.firelearning.model.vo.ExercisesOptionsListVO;
import cn.jlw.firelearning.service.ExercisesInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 题目表 服务实现类
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Service
@RequiredArgsConstructor
public class ExercisesInfoServiceImpl extends ServiceImpl<ExercisesInfoMapper, ExercisesInfo> implements ExercisesInfoService {
    private final ExercisesOptionMapper exercisesOptionMapper;
    private final StageLearnMapper stageLearnMapper;
    private final StageTestMapper stageTestMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addExercisesInfo(ExercisesInfoAddDTO content) {
        //校验题目是否存在
        Integer checkCount = baseMapper.selectCount(Wrappers.lambdaQuery(ExercisesInfo.class)
                .eq(ExercisesInfo::getExercisesTitle, content.getExercisesTitle()));
        if (checkCount > 0) {
            throw new LeException("题目已存在");
        }
        //新增题目
        ExercisesInfo exercisesInfo = new ExercisesInfo();
        exercisesInfo.setExercisesTitle(content.getExercisesTitle());
        exercisesInfo.setExercisesType(content.getExercisesType());
        baseMapper.insert(exercisesInfo);
        for (ExercisesOptionsAddDTO optionsAddDTO : content.getOptionsList()) {
            ExercisesOption exercisesOption = new ExercisesOption();
            exercisesOption.setExercisesNum(exercisesInfo.getExercisesNum());
            exercisesOption.setOptionNum(optionsAddDTO.getOptionNum());
            exercisesOption.setIfRight(optionsAddDTO.getIfRight());
            exercisesOption.setOptionContent(optionsAddDTO.getOptionContent());
            exercisesOptionMapper.insert(exercisesOption);
        }
    }

    @Override
    public List<ExercisesInfoListVO> listExercisesInfo(ExercisesInfoListDTO content) {
        //获取题目表信息
        LambdaQueryWrapper<ExercisesInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(content.getExercisesTitle())) {
            lambdaQueryWrapper.like(ExercisesInfo::getExercisesTitle, content.getExercisesTitle());
        }
        if (null != content.getExercisesType()) {
            lambdaQueryWrapper.eq(ExercisesInfo::getExercisesType, content.getExercisesType());
        }
        List<ExercisesInfoListVO> resultList = baseMapper.listExercisesInfo(lambdaQueryWrapper);
        for (ExercisesInfoListVO exercisesInfo : resultList) {
            //根据题目获取选项
            List<ExercisesOptionsListVO> exercisesOptionsList = exercisesOptionMapper.listOptionsByExercisesNum(exercisesInfo.getExercisesNum());
            exercisesInfo.setOptionsList(exercisesOptionsList);
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editExercisesInfo(ExercisesInfoEditDTO content) {
        //校验题目
        Integer checkCount = baseMapper.selectCount(Wrappers.lambdaQuery(ExercisesInfo.class)
                .ne(ExercisesInfo::getExercisesNum, content.getExercisesNum())
                .eq(ExercisesInfo::getExercisesTitle, content.getExercisesTitle()));
        if (checkCount > 0) {
            throw new LeException("题目已存在!");
        }
        ExercisesInfo exercisesInfo = new ExercisesInfo();
        BeanUtil.copyProperties(content, exercisesInfo);
        //修改题目
        baseMapper.update(exercisesInfo, Wrappers.lambdaQuery(ExercisesInfo.class)
                .eq(ExercisesInfo::getExercisesNum, content.getExercisesNum()));
        //删除原来题目，重新新增
        exercisesOptionMapper.delete(Wrappers.lambdaQuery(ExercisesOption.class)
                .eq(ExercisesOption::getExercisesNum, content.getExercisesNum()));
        for (ExercisesOptionsEditDTO optionsAddDTO : content.getOptionsList()) {
            ExercisesOption exercisesOption = new ExercisesOption();
            exercisesOption.setExercisesNum(exercisesInfo.getExercisesNum());
            exercisesOption.setOptionNum(optionsAddDTO.getOptionNum());
            exercisesOption.setIfRight(optionsAddDTO.getIfRight());
            exercisesOption.setOptionContent(optionsAddDTO.getOptionContent());
            exercisesOptionMapper.insert(exercisesOption);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteExercisesInfo(Integer exercisesNum) {
        //阶段学习题和考试题在使用不可删除
        Integer checkStageLearn = stageLearnMapper.selectCount(Wrappers.lambdaQuery(StageLearn.class)
                .eq(StageLearn::getExercisesNum, exercisesNum));
        if (checkStageLearn > 0) {
            throw new LeException("题目已使用不可删除!");
        }

        baseMapper.delete(Wrappers.lambdaQuery(ExercisesInfo.class)
                .eq(ExercisesInfo::getExercisesNum, exercisesNum));
        exercisesOptionMapper.delete(Wrappers.lambdaQuery(ExercisesOption.class)
                .eq(ExercisesOption::getExercisesNum, exercisesNum));
    }
}
