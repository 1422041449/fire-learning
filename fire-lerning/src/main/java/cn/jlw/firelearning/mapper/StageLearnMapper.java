package cn.jlw.firelearning.mapper;


import cn.jlw.firelearning.entity.StageLearn;
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

    @Select("select a.stage_learn_id,a.crtime,a.exercises_num,b.exercises_title,b.exercises_type from stage_learn a join exercises_info b on b.exercises_num = a.exercises_num " +
            "${ew.customSqlSegment} order by a.crtime desc")
    List<StageLearnListVO> listStageLearn(@Param(Constants.WRAPPER)QueryWrapper<StageLearn> queryWrapper);

}
