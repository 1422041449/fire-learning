package cn.jlw.firelearning.mapper;


import cn.jlw.firelearning.entity.UserStageLearn;
import cn.jlw.firelearning.model.vo.ListLearnCurrentTestVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户阶段信息表 Mapper 接口
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
public interface UserStageLearnMapper extends BaseMapper<UserStageLearn> {

    /**
     *
     */
    @Select("select a.id,a.stage_learn_id,a.user_answer,b.exercises_num,c.exercises_title,c.exercises_type from user_stage_learn a " +
            "join stage_learn b on b.stage_learn_id = a.stage_learn_id join exercises_info c on c.exercises_num = b.exercises_num " +
            "${ew.customSqlSegment}")
    List<ListLearnCurrentTestVO> listLearnCurrentTest(@Param(Constants.WRAPPER)QueryWrapper<UserStageLearn> queryWrapper);

}
