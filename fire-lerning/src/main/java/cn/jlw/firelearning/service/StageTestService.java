package cn.jlw.firelearning.service;


import cn.jlw.firelearning.entity.StageTest;
import cn.jlw.firelearning.model.dto.StageTestAddDTO;
import cn.jlw.firelearning.model.dto.StageTestDeleteDTO;
import cn.jlw.firelearning.model.dto.StageTestEditDTO;
import cn.jlw.firelearning.model.dto.StageTestListDTO;
import cn.jlw.firelearning.model.vo.StageTestListVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 阶段考试题目表 服务类
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
public interface StageTestService extends IService<StageTest> {
    /**
     * 新增题目
     */
    void addStageTest(StageTestAddDTO content);

    /**
     * 查看题目
     */
    List<StageTestListVO> listStageTest(StageTestListDTO content);

    /**
     * 编辑题目
     */
    void editStageTest(StageTestEditDTO content);

    /**
     * 删除题目
     */
    void deleteStageTest(StageTestDeleteDTO content);
}
