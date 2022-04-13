package cn.jlw.firelearning.mapper;


import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.entity.UserStageTest;
import cn.jlw.firelearning.model.model.UserLearnTestAnswerModel;
import cn.jlw.firelearning.model.vo.ListTestCurrentTestVO;
import cn.jlw.firelearning.model.vo.UserConditionVO;
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
    @Select("select a.id,a.stage_test_id,a.username,a.answer_result,d.exercises_num,d.exercises_title,d.exercises_type,a.user_answer from user_stage_test a " +
            "join stage_test b on a.stage_test_id = b.stage_test_id join stage_learn c on c.stage_learn_id = b.stage_learn_id " +
            "join exercises_info d on d.exercises_num = c.exercises_num ${ew.customSqlSegment} order by d.exercises_type")
    List<ListTestCurrentTestVO> listTestCurrentTest(@Param(Constants.WRAPPER) QueryWrapper<UserStageTest> queryWrapper);

    //查询用户阶段考试成绩
    @Select("select a.username,a.stage_num,e.stage_name,e.stage_title,f.`name`,f.real_name," +
            "sum(case when d.exercises_type = 1 && a.answer_result = 1 then 3 when d.exercises_type = 2 && a.answer_result = 1 then 4 when d.exercises_type = 2 && a.answer_result = 3 then 2 else 0 end) score " +
            "from user_stage_test a join stage_test b on a.stage_test_id = b.stage_test_id join stage_learn c on c.stage_learn_id = b.stage_learn_id " +
            "join exercises_info d on d.exercises_num = c.exercises_num join stage_info e on e.stage_num = a.stage_num " +
            "join user_info f on f.username = a.username ${ew.customSqlSegment}" +
            "GROUP BY a.username,a.stage_num")
    List<UserConditionVO> listUserScore(@Param(Constants.WRAPPER)QueryWrapper<StageInfo> queryWrapper);
}
