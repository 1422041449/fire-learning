package cn.jlw.firelearning.controller;


import cn.jlw.firelearning.model.LeRequest;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.dto.StageTestAddDTO;
import cn.jlw.firelearning.model.dto.StageTestDeleteDTO;
import cn.jlw.firelearning.model.dto.StageTestEditDTO;
import cn.jlw.firelearning.model.dto.StageTestListDTO;
import cn.jlw.firelearning.model.vo.ListStageLearnExercisesVO;
import cn.jlw.firelearning.model.vo.StageTestListVO;
import cn.jlw.firelearning.service.StageTestService;
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
 * 阶段考试题目表 前端控制器
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stage/test")
public class StageTestController {
    private final StageTestService stageTestService;

    /**
     * 创建题目
     */
    @PostMapping("/add")
    public LeResponse<?> addStageTest(@Valid @RequestBody LeRequest<StageTestAddDTO> leRequest) {
        StageTestAddDTO content = leRequest.getContent();
        stageTestService.addStageTest(content);
        return LeResponse.succ();
    }

    /**
     * 查询题目
     */
    @PostMapping("/list")
    public LeResponse<List<StageTestListVO>> listStageTest(@RequestBody LeRequest<StageTestListDTO> leRequest) {
        StageTestListDTO content = leRequest.getContent();
        List<StageTestListVO> resultList = stageTestService.listStageTest(content);
        return LeResponse.succ(resultList);
    }

    /**
     * 修改题目
     */
    @PostMapping("/edit")
    public LeResponse<?> editStageTest(@Valid @RequestBody LeRequest<StageTestEditDTO> leRequest) {
        StageTestEditDTO content = leRequest.getContent();
        stageTestService.editStageTest(content);
        return LeResponse.succ();
    }

    /**
     * 删除题目
     */
    @PostMapping("/delete")
    public LeResponse<?> deleteStageTest(@Valid @RequestBody LeRequest<StageTestDeleteDTO> leRequest) {
        StageTestDeleteDTO content = leRequest.getContent();
        stageTestService.deleteStageTest(content);
        return LeResponse.succ();
    }

}
