package cn.jlw.firelearning.controller;


import cn.jlw.firelearning.model.LeRequest;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.dto.ListLearnCurrentTestDTO;
import cn.jlw.firelearning.model.dto.ListStageLearnInfoDTO;
import cn.jlw.firelearning.model.vo.ListLearnCurrentTestVO;
import cn.jlw.firelearning.model.vo.ListStageLearnInfoVO;
import cn.jlw.firelearning.service.UserStageLearnService;
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
@RequestMapping("/user/stage/learn")
public class UserStageLearnController {
    private final UserStageLearnService userStageLearnService;

    /**
     * 查询当前用户的各阶段学习情况
     */
    @PostMapping("/list/stage/info")
    public LeResponse<List<ListStageLearnInfoVO>> listStageLearnInfo(@RequestBody LeRequest<ListStageLearnInfoDTO> leRequest) {
        ListStageLearnInfoDTO content = leRequest.getContent();
        List<ListStageLearnInfoVO> resultList = userStageLearnService.listStageLearnInfo(content);
        return LeResponse.succ(resultList);
    }

    /**
     * 查询当前用户的当前阶段题目
     */
    @PostMapping("/list/learn/current/test")
    public LeResponse<List<ListLearnCurrentTestVO>> listLearnCurrentTest(@RequestBody LeRequest<ListLearnCurrentTestDTO> leRequest) {
        ListLearnCurrentTestDTO content = leRequest.getContent();
        List<ListLearnCurrentTestVO> resultList = userStageLearnService.listLearnCurrentTest(content);
        return LeResponse.succ(resultList);
    }


}
