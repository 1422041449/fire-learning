package cn.jlw.firelearning.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.entity.UserStageLearn;
import cn.jlw.firelearning.mapper.StageInfoMapper;
import cn.jlw.firelearning.mapper.UserStageLearnMapper;
import cn.jlw.firelearning.model.dto.ListStageLearnInfoDTO;
import cn.jlw.firelearning.model.vo.ListStageLearnInfoVO;
import cn.jlw.firelearning.service.UserStageLearnService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户阶段信息表 服务实现类
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Service
@RequiredArgsConstructor
public class UserStageLearnServiceImpl extends ServiceImpl<UserStageLearnMapper, UserStageLearn> implements UserStageLearnService {
    private final StageInfoMapper stageInfoMapper;

    @Override
    public List<ListStageLearnInfoVO> listStageLearnInfo(ListStageLearnInfoDTO content) {
        List<ListStageLearnInfoVO> resultList = new ArrayList<>();
        //获取所有阶段
        List<StageInfo> stageInfoList = stageInfoMapper.selectList(Wrappers.lambdaQuery(StageInfo.class).eq(StageInfo::getIfPublish, 1));
        for (StageInfo stageInfo : stageInfoList) {
            ListStageLearnInfoVO result = new ListStageLearnInfoVO();
            BeanUtil.copyProperties(stageInfo, result);
            //计算当前用户当前阶段进度:不为空的数量除以30(题目数量)
            Integer notNullNum = baseMapper.selectCount(Wrappers.lambdaQuery(UserStageLearn.class)
                    .eq(UserStageLearn::getStageNum, stageInfo.getStageNum())
                    .eq(UserStageLearn::getUsername, content.getUsername())
                    .isNotNull(UserStageLearn::getUserAnswer));
            BigDecimal progress = new BigDecimal(notNullNum).divide(new BigDecimal(30), 2, BigDecimal.ROUND_HALF_UP);
            result.setProgress(progress);
            resultList.add(result);
        }
        return resultList;
    }
}
