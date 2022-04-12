package cn.jlw.firelearning.service;


import cn.jlw.firelearning.entity.UserStageTest;
import cn.jlw.firelearning.model.dto.CommitTestAnswerDTO;
import cn.jlw.firelearning.model.dto.ListLearnCurrentTestDTO;
import cn.jlw.firelearning.model.dto.ListStageLearnInfoDTO;
import cn.jlw.firelearning.model.vo.ListStageTestInfoVO;
import cn.jlw.firelearning.model.vo.ListTestCurrentTestVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户阶段信息表 服务类
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
public interface UserStageTestService extends IService<UserStageTest> {
    /**
     * 查询当前用户的各阶段考试情况
     */
    List<ListStageTestInfoVO> listStageTestInfo(ListStageLearnInfoDTO content);

    /**
     * 查询当前用户的当前阶段考试题目
     */
    List<ListTestCurrentTestVO> listTestCurrentTest(ListLearnCurrentTestDTO content);

    /**
     * 提交阶段考试答案
     */
    void commitTestAnswer(List<CommitTestAnswerDTO> contentList);

    /**
     * 查询当前用户的当前阶段考试详情
     */
    List<ListTestCurrentTestVO> listTestCurrentTestDetail(ListLearnCurrentTestDTO content);

}
