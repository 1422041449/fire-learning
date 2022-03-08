package cn.jlw.firelearning.mapper;


import cn.jlw.firelearning.entity.ExercisesOption;
import cn.jlw.firelearning.model.vo.ExercisesOptionsListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 题目选项表 Mapper 接口
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
public interface ExercisesOptionMapper extends BaseMapper<ExercisesOption> {

    //根据题目编号获取选项集合
    @Select("select * from exercises_option where exercises_num = #{exercisesNum}")
    List<ExercisesOptionsListVO> listOptionsByExercisesNum(Integer exercisesNum);

}
