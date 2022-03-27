package cn.jlw.firelearning.mapper;


import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.model.vo.StageInfoListVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 阶段信息表 Mapper 接口
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
public interface StageInfoMapper extends BaseMapper<StageInfo> {

    @Select("select stage_num,stage_name,stage_title,if_publish from stage_info ${ew.customSqlSegment}")
    List<StageInfoListVO> listStageInfo(@Param(Constants.WRAPPER) LambdaQueryWrapper<StageInfo> lambdaQueryWrapper);

}
