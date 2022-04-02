package cn.jlw.firelearning.mapper;


import cn.jlw.firelearning.entity.ExercisesInfo;
import cn.jlw.firelearning.model.vo.ExercisesInfoListVO;
import cn.jlw.firelearning.model.vo.ListStageLearnExercisesVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 题目表 Mapper 接口
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
public interface ExercisesInfoMapper extends BaseMapper<ExercisesInfo> {

    //获取题目信息
    @Select("select * from exercises_info ${ew.customSqlSegment} order by exercises_type")
    List<ExercisesInfoListVO> listExercisesInfo(@Param(Constants.WRAPPER) LambdaQueryWrapper<ExercisesInfo> lambdaQueryWrapper);

    //获取阶段学习未被绑定题目信息
    @Select("select a.exercises_num,a.exercises_title,a.exercises_type from exercises_info a " +
            "LEFT JOIN stage_learn b on a.exercises_num = b.exercises_num and b.stage_num = #{stageNum} " +
            "where b.stage_learn_id is null order by a.exercises_type,b.crtime desc")
    List<ListStageLearnExercisesVO> listStageLearnExercises(Integer stageNum);
}
