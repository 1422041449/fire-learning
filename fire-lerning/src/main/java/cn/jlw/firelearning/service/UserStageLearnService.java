package cn.jlw.firelearning.service;


import cn.jlw.firelearning.entity.UserStageLearn;
import cn.jlw.firelearning.model.dto.CommitLearnTestDTO;
import cn.jlw.firelearning.model.dto.ListLearnCurrentTestDTO;
import cn.jlw.firelearning.model.dto.ListStageLearnInfoDTO;
import cn.jlw.firelearning.model.vo.ListLearnCurrentTestVO;
import cn.jlw.firelearning.model.vo.ListStageLearnInfoVO;
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
public interface UserStageLearnService extends IService<UserStageLearn> {

    /**
     * 查询当前用户所有阶段进度
     */
    List<ListStageLearnInfoVO> listStageLearnInfo(ListStageLearnInfoDTO content);

    /**
     * 查询当前用户的当前阶段题目
     */
    List<ListLearnCurrentTestVO> listLearnCurrentTest(ListLearnCurrentTestDTO content);

    /**
     * 提交学习题目
     */
    void commitLearnTest(CommitLearnTestDTO content);

}
