package cn.jlw.firelearning.mapper;


import cn.jlw.firelearning.entity.StageLearn;
import cn.jlw.firelearning.model.vo.ListStageLearnExercisesVO;
import cn.jlw.firelearning.model.vo.StageLearnListVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 阶段学习题目表 Mapper 接口
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
public interface StageLearnMapper extends BaseMapper<StageLearn> {

    @Select("select a.stage_learn_id,a.crtime,a.exercises_num,b.exercises_title,b.exercises_type,a.stage_num from stage_learn a join exercises_info b on b.exercises_num = a.exercises_num " +
            "${ew.customSqlSegment} order by a.crtime desc")
    List<StageLearnListVO> listStageLearn(@Param(Constants.WRAPPER) QueryWrapper<StageLearn> queryWrapper);

    /**
     * 查询阶段学习题中未被阶段考试题使用的题目
     */
    @Select("select c.exercises_num,c.exercises_type,c.exercises_title,a.stage_learn_id from stage_learn a " +
            "LEFT JOIN stage_test b on a.stage_learn_id = b.stage_learn_id " +
            "join exercises_info c on c.exercises_num = a.exercises_num " +
            "where a.stage_num = #{stageNum} and b.stage_test_id is null " +
            "order by c.exercises_type,b.crtime desc")
    List<ListStageLearnExercisesVO> listStageTestExercises(Integer stageNum);
}
