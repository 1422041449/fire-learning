package cn.jlw.firelearning.mapper;


import cn.jlw.firelearning.entity.StageTest;
import cn.jlw.firelearning.model.vo.StageTestListVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 阶段考试题目表 Mapper 接口
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
public interface StageTestMapper extends BaseMapper<StageTest> {
    /**
     * 查询阶段考试题
     *
     * @param queryWrapper
     * @return
     */
    @Select("select a.*,c.* from stage_test a join stage_learn b on a.stage_learn_id = b.stage_learn_id join exercises_info c on c.exercises_num = b.exercises_num " +
            "${ew.customSqlSegment} ORDER BY c.exercises_type,a.crtime desc")
    List<StageTestListVO> listStageTest(@Param(Constants.WRAPPER) QueryWrapper<StageTest> queryWrapper);

    /**
     * 查询阶段考试题中单选多选的个数
     */
    @Select("select count(*) from stage_test a join stage_learn b on a.stage_learn_id = b.stage_learn_id join exercises_info c on c.exercises_num = b.exercises_num " +
            "${ew.customSqlSegment}")
    Integer checkDanxuanNum(@Param(Constants.WRAPPER) QueryWrapper<StageTest> queryWrapper);

    /**
     * 校验考试题目总分
     */
    @Select("select sum(case c.exercises_type  when 1 then 3 when  2 then 4 else 0 END) totalScore  from stage_test a join stage_learn b on a.stage_learn_id = b.stage_learn_id join exercises_info c on c.exercises_num = b.exercises_num where a.stage_num = #{stageNum}")
    Integer publishStageInfo(Integer stageNum);
}
