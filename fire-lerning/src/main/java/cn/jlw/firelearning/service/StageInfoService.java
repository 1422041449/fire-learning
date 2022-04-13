package cn.jlw.firelearning.service;


import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.model.dto.StageInfoAddDTO;
import cn.jlw.firelearning.model.dto.StageInfoEditDTO;
import cn.jlw.firelearning.model.dto.StageInfoListDTO;
import cn.jlw.firelearning.model.dto.UserConditionDTO;
import cn.jlw.firelearning.model.vo.StageInfoListVO;
import cn.jlw.firelearning.model.vo.UserConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 阶段信息表 服务类
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
public interface StageInfoService extends IService<StageInfo> {

    /**
     * 新增阶段
     */
    void addStageInfo(StageInfoAddDTO content);

    /**
     * 查看阶段
     */
    List<StageInfoListVO> listStageInfo(StageInfoListDTO content);

    /**
     * 编辑阶段
     */
    void editStageInfo(StageInfoEditDTO content);

    /**
     * 删除阶段
     */
    void deleteStageInfo(Integer stageNum);

    /**
     * 发布阶段
     */
    void publishStageInfo(Integer stageNum);

    /**
     * 校验已发布的阶段，抛出异常
     */
    void checkPublish(Integer stageNum);

    /**
     * 用户成绩查询
     */
    List<UserConditionVO> userCondition(UserConditionDTO content);
}
