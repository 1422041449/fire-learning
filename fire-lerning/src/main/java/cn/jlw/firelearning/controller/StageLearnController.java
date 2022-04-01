package cn.jlw.firelearning.controller;


import cn.jlw.firelearning.model.LeRequest;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.dto.StageLearnAddDTO;
import cn.jlw.firelearning.model.dto.StageLearnDeleteDTO;
import cn.jlw.firelearning.model.dto.StageLearnEditDTO;
import cn.jlw.firelearning.model.dto.StageLearnListDTO;
import cn.jlw.firelearning.model.vo.ListStageLearnExercisesVO;
import cn.jlw.firelearning.model.vo.StageLearnListVO;
import cn.jlw.firelearning.service.StageLearnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 阶段学习阶段学习题表 前端控制器
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stage/learn")
public class StageLearnController {
    private final StageLearnService stageLearnService;

    /**
     * 创建阶段学习题
     */
    @PostMapping("/add/stageLearn")
    public LeResponse<?> addStageLearn(@Valid @RequestBody LeRequest<StageLearnAddDTO> leRequest) {
        StageLearnAddDTO content = leRequest.getContent();
        stageLearnService.addStageLearn(content);
        return LeResponse.succ();
    }

    /**
     * 查询阶段学习题
     */
    @PostMapping("/list/stageLearn")
    public LeResponse<List<StageLearnListVO>> listStageLearn(@RequestBody LeRequest<StageLearnListDTO> leRequest) {
        StageLearnListDTO content = leRequest.getContent();
        List<StageLearnListVO> resultList = stageLearnService.listStageLearn(content);
        return LeResponse.succ(resultList);
    }

    /**
     * 修改阶段学习题
     */
    @PostMapping("/edit/stageLearn")
    public LeResponse<?> editStageLearn(@Valid @RequestBody LeRequest<StageLearnEditDTO> leRequest) {
        StageLearnEditDTO content = leRequest.getContent();
        stageLearnService.editStageLearn(content);
        return LeResponse.succ();
    }

    /**
     * 删除阶段学习题
     */
    @PostMapping("/delete/stageLearn")
    public LeResponse<?> deleteStageLearn(@Valid @RequestBody LeRequest<StageLearnDeleteDTO> leRequest) {
        StageLearnDeleteDTO content = leRequest.getContent();
        stageLearnService.deleteStageLearn(content);
        return LeResponse.succ();
    }
}
