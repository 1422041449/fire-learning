package cn.jlw.firelearning.service;


import cn.jlw.firelearning.entity.ExercisesInfo;
import cn.jlw.firelearning.model.dto.ExercisesInfoAddDTO;
import cn.jlw.firelearning.model.dto.ExercisesInfoEditDTO;
import cn.jlw.firelearning.model.dto.ExercisesInfoListDTO;
import cn.jlw.firelearning.model.vo.ExercisesInfoListVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题目表 服务类
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
public interface ExercisesInfoService extends IService<ExercisesInfo> {

    /**
     * 新增题目
     */
    void addExercisesInfo(ExercisesInfoAddDTO content);

    /**
     * 查看题目
     */
    List<ExercisesInfoListVO> listExercisesInfo(ExercisesInfoListDTO content);

    /**
     * 编辑题目
     */
    void editExercisesInfo(ExercisesInfoEditDTO content);

    /**
     * 删除题目
     */
    void deleteExercisesInfo(Integer exercisesNum);
}
