package cn.jlw.firelearning.service;


import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.model.dto.StageInfoAddDTO;
import cn.jlw.firelearning.model.dto.StageInfoEditDTO;
import cn.jlw.firelearning.model.dto.StageInfoListDTO;
import cn.jlw.firelearning.model.vo.StageInfoListVO;
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
}
