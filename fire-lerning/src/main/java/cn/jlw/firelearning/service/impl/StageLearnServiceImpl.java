package cn.jlw.firelearning.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.entity.StageLearn;
import cn.jlw.firelearning.entity.StageTest;
import cn.jlw.firelearning.exception.LeException;
import cn.jlw.firelearning.mapper.ExercisesOptionMapper;
import cn.jlw.firelearning.mapper.StageInfoMapper;
import cn.jlw.firelearning.mapper.StageLearnMapper;
import cn.jlw.firelearning.mapper.StageTestMapper;
import cn.jlw.firelearning.model.dto.StageLearnAddDTO;
import cn.jlw.firelearning.model.dto.StageLearnDeleteDTO;
import cn.jlw.firelearning.model.dto.StageLearnEditDTO;
import cn.jlw.firelearning.model.dto.StageLearnListDTO;
import cn.jlw.firelearning.model.vo.ExercisesOptionsListVO;
import cn.jlw.firelearning.model.vo.ListStageLearnExercisesVO;
import cn.jlw.firelearning.model.vo.StageLearnListVO;
import cn.jlw.firelearning.service.StageLearnService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 阶段学习题目表 服务实现类
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Service
@RequiredArgsConstructor
public class StageLearnServiceImpl extends ServiceImpl<StageLearnMapper, StageLearn> implements StageLearnService {
    private final StageInfoMapper stageInfoMapper;
    private final StageTestMapper stageTestMapper;
    private final ExercisesOptionMapper exercisesOptionMapper;

    @Override
    public void addStageLearn(StageLearnAddDTO content) {
        //检验题目是否存在
        Integer check = baseMapper.selectCount(Wrappers.lambdaQuery(StageLearn.class)
                .eq(StageLearn::getStageNum, content.getStageNum())
                .eq(StageLearn::getExercisesNum, content.getExercisesNum()));
        if (check > 0) {
            throw new LeException("题目已存在!");
        }
        StageLearn stageLearn = new StageLearn();
        BeanUtil.copyProperties(content, stageLearn);
        baseMapper.insert(stageLearn);
    }

    @Override
    public List<StageLearnListVO> listStageLearn(StageLearnListDTO content) {
        QueryWrapper<StageLearn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.stage_num", content.getStageNum());
        if (StrUtil.isNotBlank(content.getExercisesTitle())) {
            queryWrapper.like("b.exercises_title", content.getExercisesTitle());
        }
        if (StrUtil.isNotBlank(content.getExercisesType())) {
            queryWrapper.eq("b.exercises_type", content.getExercisesType());
        }
        List<StageLearnListVO> resultList = baseMapper.listStageLearn(queryWrapper);
        for (StageLearnListVO stageLearnListVO : resultList) {
            //根据题目获取选项
            List<ExercisesOptionsListVO> exercisesOptionsListVOS = exercisesOptionMapper.listOptionsByExercisesNum(stageLearnListVO.getExercisesNum());
            stageLearnListVO.setOptionsList(exercisesOptionsListVOS);
        }
        return resultList;
    }

    @Override
    public void editStageLearn(StageLearnEditDTO content) {
        //检验题目是否存在
        Integer check = baseMapper.selectCount(Wrappers.lambdaQuery(StageLearn.class)
                .eq(StageLearn::getStageNum, content.getStageNum())
                .eq(StageLearn::getExercisesNum, content.getExercisesNum())
                .ne(StageLearn::getStageLearnId, content.getStageLearnId()));
        if (check > 0) {
            throw new LeException("题目已存在!");
        }
        StageLearn stageLearn = new StageLearn();
        BeanUtil.copyProperties(content, stageLearn);
        baseMapper.update(null, Wrappers.lambdaUpdate(StageLearn.class)
                .eq(StageLearn::getStageLearnId, content.getStageLearnId())
                .set(StageLearn::getExercisesNum, content.getExercisesNum()));
    }

    @Override
    public void deleteStageLearn(StageLearnDeleteDTO content) {
        //校验阶段是否发布，校验题目是否在考试中出现
        StageInfo stageInfo = stageInfoMapper.selectOne(Wrappers.lambdaQuery(StageInfo.class)
                .eq(StageInfo::getStageNum, content.getStageNum()));
        if (stageInfo.getIfPublish() == 1) {
            throw new LeException("阶段已发布，题目不可删除!");
        }
        Integer check = stageTestMapper.selectCount(Wrappers.lambdaQuery(StageTest.class)
                .eq(StageTest::getStageLearnId, content.getStageLearnId()));
        if (check > 0) {
            throw new LeException("已作为阶段考试题目，不可删除!");
        }
        baseMapper.delete(Wrappers.lambdaQuery(StageLearn.class)
                .eq(StageLearn::getStageLearnId, content.getStageLearnId()));
    }

    @Override
    public List<ListStageLearnExercisesVO> listStageTestExercises(Integer stageNum) {
        return baseMapper.listStageTestExercises(stageNum);
    }

}
