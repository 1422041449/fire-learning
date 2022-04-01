package cn.jlw.firelearning.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.entity.StageTest;
import cn.jlw.firelearning.exception.LeException;
import cn.jlw.firelearning.mapper.ExercisesOptionMapper;
import cn.jlw.firelearning.mapper.StageInfoMapper;
import cn.jlw.firelearning.mapper.StageTestMapper;
import cn.jlw.firelearning.model.dto.StageTestAddDTO;
import cn.jlw.firelearning.model.dto.StageTestDeleteDTO;
import cn.jlw.firelearning.model.dto.StageTestEditDTO;
import cn.jlw.firelearning.model.dto.StageTestListDTO;
import cn.jlw.firelearning.model.vo.ExercisesOptionsListVO;
import cn.jlw.firelearning.model.vo.StageTestListVO;
import cn.jlw.firelearning.service.StageTestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 阶段考试题目表 服务实现类
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Service
@RequiredArgsConstructor
public class StageTestServiceImpl extends ServiceImpl<StageTestMapper, StageTest> implements StageTestService {
    private final ExercisesOptionMapper exercisesOptionMapper;
    private final StageInfoMapper stageInfoMapper;

    @Override
    public void addStageTest(StageTestAddDTO content) {
        //校验是否存在
        Integer check = baseMapper.selectCount(Wrappers.lambdaQuery(StageTest.class)
                .eq(StageTest::getStageLearnId, content.getStageLearnId())
                .eq(StageTest::getStageNum, content.getStageNum()));
        if (check > 0) {
            throw new LeException("题目已存在");
        }
        //校验单选数量20题
        QueryWrapper<StageTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c.exercises_type", 1)
                .eq("a.stage_num", content.getStageNum());
        Integer checkOne = baseMapper.checkDanxuanNum(queryWrapper);
        if (checkOne >= 20) {
            throw new LeException("单选题已满");
        }

        //校验多选题数量10题
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c.exercises_type", 2)
                .eq("a.stage_num", content.getStageNum());
        Integer checkTwo = baseMapper.checkDanxuanNum(queryWrapper);
        if (checkTwo >= 10) {
            throw new LeException("多选题已满");
        }

        StageTest stageTest = new StageTest();
        BeanUtil.copyProperties(content, stageTest);
        baseMapper.insert(stageTest);
    }

    @Override
    public List<StageTestListVO> listStageTest(StageTestListDTO content) {
        QueryWrapper<StageTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.stage_num", content.getStageNum());
        if (StrUtil.isNotBlank(content.getExercisesTitle())) {
            queryWrapper.like("c.exercises_title", content.getExercisesTitle());
        }
        if (StrUtil.isNotBlank(content.getExercisesType())) {
            queryWrapper.eq("c.exercises_type", content.getExercisesType());
        }
        List<StageTestListVO> resultList = baseMapper.listStageTest(queryWrapper);
        for (StageTestListVO stageTest : resultList) {
            //根据题目获取选项
            List<ExercisesOptionsListVO> exercisesOptionsListVOS = exercisesOptionMapper.listOptionsByExercisesNum(stageTest.getExercisesNum());
            stageTest.setOptionsList(exercisesOptionsListVOS);
        }
        return resultList;
    }

    @Override
    public void editStageTest(StageTestEditDTO content) {
        //校验是否存在
        Integer check = baseMapper.selectCount(Wrappers.lambdaQuery(StageTest.class)
                .eq(StageTest::getStageLearnId, content.getStageLearnId())
                .eq(StageTest::getStageNum, content.getStageNum())
                .ne(StageTest::getStageTestId, content.getStageTestId()));
        if (check > 0) {
            throw new LeException("题目已存在");
        }
        //校验单选数量20题
        QueryWrapper<StageTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c.exercises_type", 1)
                .eq("a.stage_num", content.getStageNum());
        Integer checkOne = baseMapper.checkDanxuanNum(queryWrapper);
        if (checkOne >= 20 && content.getExercisesType() == 1) {
            throw new LeException("单选题已满");
        }

        //校验多选题数量10题
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c.exercises_type", 2)
                .eq("a.stage_num", content.getStageNum());
        Integer checkTwo = baseMapper.checkDanxuanNum(queryWrapper);
        if (checkTwo >= 10 && content.getExercisesType() == 2) {
            throw new LeException("多选题已满");
        }

        baseMapper.update(null, Wrappers.lambdaUpdate(StageTest.class)
                .eq(StageTest::getStageTestId, content.getStageTestId())
                .set(StageTest::getStageLearnId, content.getStageLearnId()));
    }

    @Override
    public void deleteStageTest(StageTestDeleteDTO content) {
        //校验阶段是否发布，校验题目是否在考试中出现
        StageInfo stageInfo = stageInfoMapper.selectOne(Wrappers.lambdaQuery(StageInfo.class)
                .eq(StageInfo::getStageNum, content.getStageNum()));
        if (stageInfo.getIfPublish() == 1) {
            throw new LeException("阶段已发布，题目不可删除!");
        }

        baseMapper.delete(Wrappers.lambdaQuery(StageTest.class)
                .eq(StageTest::getStageTestId, content.getStageTestId())
                .eq(StageTest::getStageNum, content.getStageNum()));
    }
}
