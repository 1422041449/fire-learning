package cn.jlw.firelearning.model.dto;

import lombok.Data;

/**
 * 阶段考试题查询
 */
@Data
public class StageTestListDTO {
    //题目
    private String exercisesTitle;

    //题目类型
    private String exercisesType;

    //阶段号
    private Integer stageNum;
}
