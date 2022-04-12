package cn.jlw.firelearning.controller;


import cn.jlw.firelearning.entity.UserStageLearn;
import cn.jlw.firelearning.model.LeRequest;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.dto.CommitLearnTestDTO;
import cn.jlw.firelearning.model.dto.ListLearnCurrentTestDTO;
import cn.jlw.firelearning.model.dto.ListStageLearnInfoDTO;
import cn.jlw.firelearning.model.vo.ListLearnCurrentTestVO;
import cn.jlw.firelearning.model.vo.ListStageLearnInfoVO;
import cn.jlw.firelearning.service.UserStageLearnService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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

    /**
     * 提交学习题目
     */
    @PostMapping("/commit/learn/test")
    public LeResponse<?> commitLearnTest(@RequestBody LeRequest<CommitLearnTestDTO> leRequest) {
        CommitLearnTestDTO content = leRequest.getContent();
        userStageLearnService.commitLearnTest(content);
        return LeResponse.succ();
    }

    /**
     * 校验阶段学习进度
     */
    @PostMapping("/check/learn/progress")
    public LeResponse<?> checkLearnProgress(@RequestBody LeRequest<ListLearnCurrentTestDTO> leRequest) {
        ListLearnCurrentTestDTO content = leRequest.getContent();
        int checkCount = userStageLearnService.count(Wrappers.lambdaQuery(UserStageLearn.class)
                .eq(UserStageLearn::getUsername, content.getUsername())
                .eq(UserStageLearn::getStageNum, content.getStageNum())
                .isNull(UserStageLearn::getUserAnswer));
        if (checkCount > 0) {
            return LeResponse.fail("当前阶段学习未完成，请先完成学习!");
        }
        return LeResponse.succ();
    }
}
