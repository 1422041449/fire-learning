package cn.jlw.firelearning.model.dto;

import lombok.Data;

/**
 * 阶段学习题查询
 */
@Data
public class StageLearnListDTO {
    //题目
    private String exercisesTitle;

    //题目类型
    private String exercisesType;

    //阶段号
    private Integer stageNum;
}
