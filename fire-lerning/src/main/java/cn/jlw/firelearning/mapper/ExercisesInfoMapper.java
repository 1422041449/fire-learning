package cn.jlw.firelearning.mapper;


import cn.jlw.firelearning.entity.ExercisesInfo;
import cn.jlw.firelearning.model.vo.ExercisesInfoListVO;
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
    @Select("select * from exercises_info ${ew.customSqlSegment}")
    List<ExercisesInfoListVO> listExercisesInfo(@Param(Constants.WRAPPER) LambdaQueryWrapper<ExercisesInfo> lambdaQueryWrapper);

}
