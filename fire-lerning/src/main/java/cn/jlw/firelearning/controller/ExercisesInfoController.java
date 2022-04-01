package cn.jlw.firelearning.controller;


import cn.jlw.firelearning.model.LeRequest;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.dto.ExercisesInfoAddDTO;
import cn.jlw.firelearning.model.dto.ExercisesInfoEditDTO;
import cn.jlw.firelearning.model.dto.ExercisesInfoListDTO;
import cn.jlw.firelearning.model.vo.ExercisesInfoListVO;
import cn.jlw.firelearning.model.vo.ListStageLearnExercisesVO;
import cn.jlw.firelearning.service.ExercisesInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 题目表 前端控制器
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/exercises/info")
public class ExercisesInfoController {
    private final ExercisesInfoService exercisesInfoService;

    /**
     * 创建题目
     */
    @PostMapping("/add/exercises/info")
    public LeResponse<?> addExercisesInfo(@Valid @RequestBody LeRequest<ExercisesInfoAddDTO> leRequest) {
        ExercisesInfoAddDTO content = leRequest.getContent();
        exercisesInfoService.addExercisesInfo(content);
        return LeResponse.succ();
    }

    /**
     * 查询题目
     */
    @PostMapping("/list/exercises/info")
    public LeResponse<List<ExercisesInfoListVO>> listExercisesInfo(@RequestBody LeRequest<ExercisesInfoListDTO> leRequest) {
        ExercisesInfoListDTO content = leRequest.getContent();
        List<ExercisesInfoListVO> resultList = exercisesInfoService.listExercisesInfo(content);
        return LeResponse.succ(resultList);
    }

    /**
     * 修改题目
     */
    @PostMapping("/edit/exercises/info")
    public LeResponse<?> editExercisesInfo(@Valid @RequestBody LeRequest<ExercisesInfoEditDTO> leRequest) {
        ExercisesInfoEditDTO content = leRequest.getContent();
        exercisesInfoService.editExercisesInfo(content);
        return LeResponse.succ();
    }

    /**
     * 删除题目
     */
    @PostMapping("/delete/exercises/info")
    public LeResponse<?> deleteExercisesInfo(@Valid @RequestBody LeRequest<Integer> leRequest) {
        Integer exercisesNum = leRequest.getContent();
        exercisesInfoService.deleteExercisesInfo(exercisesNum);
        return LeResponse.succ();
    }

    /**
     * 获取题库中当前阶段未被选择的题目
     */
    @PostMapping("/list/stageLearn/exercises")
    public LeResponse<List<ListStageLearnExercisesVO>> listStageLearnExercises(@Valid @RequestBody LeRequest<Integer> leRequest) {
        Integer stageNum = leRequest.getContent();
        List<ListStageLearnExercisesVO> resultList = exercisesInfoService.listStageLearnExercises(stageNum);
        return LeResponse.succ(resultList);
    }
}
