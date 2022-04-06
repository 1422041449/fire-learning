package cn.jlw.firelearning.controller;


import cn.jlw.firelearning.model.LeRequest;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.dto.StageInfoAddDTO;
import cn.jlw.firelearning.model.dto.StageInfoEditDTO;
import cn.jlw.firelearning.model.dto.StageInfoListDTO;
import cn.jlw.firelearning.model.vo.StageInfoListVO;
import cn.jlw.firelearning.service.StageInfoService;
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
 * 阶段信息表 前端控制器
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stage/info")
public class StageInfoController {
    private final StageInfoService stageInfoService;

    /**
     * 创建阶段
     */
    @PostMapping("/add/stage/info")
    public LeResponse<?> addStageInfo(@Valid @RequestBody LeRequest<StageInfoAddDTO> leRequest) {
        StageInfoAddDTO content = leRequest.getContent();
        stageInfoService.addStageInfo(content);
        return LeResponse.succ();
    }

    /**
     * 查询阶段
     */
    @PostMapping("/list/stage/info")
    public LeResponse<List<StageInfoListVO>> listStageInfo(@RequestBody LeRequest<StageInfoListDTO> leRequest) {
        StageInfoListDTO content = leRequest.getContent();
        List<StageInfoListVO> resultList = stageInfoService.listStageInfo(content);
        return LeResponse.succ(resultList);
    }

    /**
     * 修改阶段
     */
    @PostMapping("/edit/stage/info")
    public LeResponse<?> editStageInfo(@Valid @RequestBody LeRequest<StageInfoEditDTO> leRequest) {
        StageInfoEditDTO content = leRequest.getContent();
        stageInfoService.editStageInfo(content);
        return LeResponse.succ();
    }

    /**
     * 删除阶段
     */
    @PostMapping("/delete/stage/info")
    public LeResponse<?> deleteStageInfo(@Valid @RequestBody LeRequest<Integer> leRequest) {
        Integer stageNum = leRequest.getContent();
        stageInfoService.deleteStageInfo(stageNum);
        return LeResponse.succ();
    }

    /**
     * 发布阶段
     */
    @PostMapping("/publish")
    public LeResponse<?> publishStageInfo(@Valid @RequestBody LeRequest<Integer> leRequest) {
        Integer stageNum = leRequest.getContent();
        stageInfoService.publishStageInfo(stageNum);
        return LeResponse.succ();
    }

}
