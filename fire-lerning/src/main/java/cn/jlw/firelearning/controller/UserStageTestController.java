package cn.jlw.firelearning.controller;


import cn.jlw.firelearning.entity.UserStageTest;
import cn.jlw.firelearning.model.LeRequest;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.dto.CommitLearnTestDTO;
import cn.jlw.firelearning.model.dto.CommitTestAnswerDTO;
import cn.jlw.firelearning.model.dto.ListLearnCurrentTestDTO;
import cn.jlw.firelearning.model.dto.ListStageLearnInfoDTO;
import cn.jlw.firelearning.model.vo.ListLearnCurrentTestVO;
import cn.jlw.firelearning.model.vo.ListStageLearnInfoVO;
import cn.jlw.firelearning.model.vo.ListStageTestInfoVO;
import cn.jlw.firelearning.model.vo.ListTestCurrentTestVO;
import cn.jlw.firelearning.service.UserStageTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户阶段信息表 前端控制器
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/stage/test")
public class UserStageTestController {
    private final UserStageTestService userStageTestService;
    /**
     * 查询当前用户的各阶段考试情况
     */
    @PostMapping("/list/stage/info")
    public LeResponse<List<ListStageTestInfoVO>> listStageTestInfo(@RequestBody LeRequest<ListStageLearnInfoDTO> leRequest) {
        ListStageLearnInfoDTO content = leRequest.getContent();
        List<ListStageTestInfoVO> resultList = userStageTestService.listStageTestInfo(content);
        return LeResponse.succ(resultList);
    }

    /**
     * 查询当前用户的当前阶段题目
     */
    @PostMapping("/list/test/current/test")
    public LeResponse<List<ListTestCurrentTestVO>> listTestCurrentTest(@RequestBody LeRequest<ListLearnCurrentTestDTO> leRequest) {
        ListLearnCurrentTestDTO content = leRequest.getContent();
        List<ListTestCurrentTestVO> resultList = userStageTestService.listTestCurrentTest(content);
        return LeResponse.succ(resultList);
    }

    /**
     * 提交考试答案
     */
    @PostMapping("/commit/test/answer")
    public LeResponse<?> commitTestAnswer(@RequestBody LeRequest<List<CommitTestAnswerDTO>> leRequest) {
        List<CommitTestAnswerDTO> contentList = leRequest.getContent();
        userStageTestService.commitTestAnswer(contentList);
        return LeResponse.succ();
    }
}
