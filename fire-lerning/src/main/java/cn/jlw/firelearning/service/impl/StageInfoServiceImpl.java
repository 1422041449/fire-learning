package cn.jlw.firelearning.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.jlw.firelearning.entity.StageInfo;
import cn.jlw.firelearning.mapper.StageInfoMapper;
import cn.jlw.firelearning.model.dto.StageInfoAddDTO;
import cn.jlw.firelearning.model.dto.StageInfoEditDTO;
import cn.jlw.firelearning.model.dto.StageInfoListDTO;
import cn.jlw.firelearning.model.vo.StageInfoListVO;
import cn.jlw.firelearning.service.StageInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class StageInfoServiceImpl extends ServiceImpl<StageInfoMapper, StageInfo> implements StageInfoService {

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
}
