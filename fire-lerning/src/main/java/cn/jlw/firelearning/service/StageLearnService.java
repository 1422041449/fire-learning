package cn.jlw.firelearning.service;

import cn.jlw.firelearning.entity.StageLearn;
import cn.jlw.firelearning.model.dto.StageLearnAddDTO;
import cn.jlw.firelearning.model.dto.StageLearnDeleteDTO;
import cn.jlw.firelearning.model.dto.StageLearnEditDTO;
import cn.jlw.firelearning.model.dto.StageLearnListDTO;
import cn.jlw.firelearning.model.vo.ListStageLearnExercisesVO;
import cn.jlw.firelearning.model.vo.StageLearnListVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 阶段学习阶段学习题表 服务类
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
public interface StageLearnService extends IService<StageLearn> {
    /**
     * 新增阶段学习题
     */
    void addStageLearn(StageLearnAddDTO content);

    /**
     * 查看阶段学习题
     */
    List<StageLearnListVO> listStageLearn(StageLearnListDTO content);

    /**
     * 编辑阶段学习题
     */
    void editStageLearn(StageLearnEditDTO content);

    /**
     * 删除阶段学习题
     */
    void deleteStageLearn(StageLearnDeleteDTO content);
}
