package cn.jlw.firelearning.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListStageLearnInfoVO {
    //阶段号
    private Integer stageNum;

    //阶段名称
    private String stageName;

    //阶段标题
    private String stageTitle;

    //完成进度
    private BigDecimal progress;
}
