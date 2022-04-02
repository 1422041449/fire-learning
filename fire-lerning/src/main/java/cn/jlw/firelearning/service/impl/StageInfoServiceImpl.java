package cn.jlw.firelearning.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.exception.LeException;
import cn.jlw.firelearning.mapper.StageInfoMapper;
import cn.jlw.firelearning.mapper.StageTestMapper;
import cn.jlw.firelearning.model.dto.StageInfoAddDTO;
import cn.jlw.firelearning.model.dto.StageInfoEditDTO;
import cn.jlw.firelearning.model.dto.StageInfoListDTO;
import cn.jlw.firelearning.model.vo.StageInfoListVO;
import cn.jlw.firelearning.service.StageInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 阶段信息表 服务实现类
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Service
@RequiredArgsConstructor
public class StageInfoServiceImpl extends ServiceImpl<StageInfoMapper, StageInfo> implements StageInfoService {
    private final StageTestMapper stageTestMapper;

    @Override
    public void addStageInfo(StageInfoAddDTO content) {
        StageInfo stageInfo = new StageInfo();
        BeanUtil.copyProperties(content, stageInfo);
        baseMapper.insert(stageInfo);
    }

    @Override
    public List<StageInfoListVO> listStageInfo(StageInfoListDTO content) {
        LambdaQueryWrapper<StageInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(content.getStageName())) {
            lambdaQueryWrapper.like(StageInfo::getStageName, content.getStageName());
        }
        if (StrUtil.isNotBlank(content.getStageTitle())) {
            lambdaQueryWrapper.like(StageInfo::getStageTitle, content.getStageTitle());
        }
        if (null != content.getIfPublish()) {
            lambdaQueryWrapper.eq(StageInfo::getIfPublish, content.getIfPublish());
        }
        return baseMapper.listStageInfo(lambdaQueryWrapper);
    }

    @Override
    public void editStageInfo(StageInfoEditDTO content) {
        StageInfo stageInfo = new StageInfo();
        BeanUtil.copyProperties(content, stageInfo);
        baseMapper.update(stageInfo, Wrappers.lambdaQuery(StageInfo.class)
                .eq(StageInfo::getStageNum, content.getStageNum()));
    }

    @Override
    public void deleteStageInfo(Integer stageNum) {
        baseMapper.delete(Wrappers.lambdaQuery(StageInfo.class)
                .eq(StageInfo::getStageNum, stageNum));
    }

    @Override
    public void publishStageInfo(Integer stageNum) {
        //校验考试题目是否创建完毕
        int checkScore = stageTestMapper.publishStageInfo(stageNum);
        if (checkScore != 100) {
            throw new LeException("考试题目为创建完成，不可发布!");
        }

        //发布阶段
        baseMapper.update(null, Wrappers.lambdaUpdate(StageInfo.class)
                .eq(StageInfo::getStageNum, stageNum)
                .set(StageInfo::getIfPublish, 1));
    }
}
