package cn.jlw.firelearning.mapper;


import cn.jlw.firelearning.entity.UserStageTest;
import cn.jlw.firelearning.model.model.UserLearnTestAnswerModel;
import cn.jlw.firelearning.model.vo.ListTestCurrentTestVO;
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
public interface UserStageTestMapper extends BaseMapper<UserStageTest> {

    //查询用户阶段题答案结果
    @Select("select a.stage_test_id,a.answer_result,d.exercises_type from user_stage_test a join stage_test b on a.stage_test_id = b.stage_test_id " +
            "join stage_learn c on c.stage_learn_id = b.stage_learn_id join exercises_info d on d.exercises_num = c.exercises_num " +
            "${ew.customSqlSegment}")
    List<UserLearnTestAnswerModel> listUserAnswerRes(@Param(Constants.WRAPPER) QueryWrapper<UserStageTest> queryWrapper);

    //查询阶段考试题题目信息
    @Select("select a.id,a.stage_test_id,a.username,a.answer_result,d.exercises_num,d.exercises_title,d.exercises_type from user_stage_test a " +
            "join stage_test b on a.stage_test_id = b.stage_test_id join stage_learn c on c.stage_learn_id = b.stage_learn_id " +
            "join exercises_info d on d.exercises_num = c.exercises_num ${ew.customSqlSegment}")
    List<ListTestCurrentTestVO> listTestCurrentTest(@Param(Constants.WRAPPER) QueryWrapper<UserStageTest> queryWrapper);

}
