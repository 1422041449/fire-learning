package cn.jlw.firelearning.model.vo;

import lombok.Data;

/**
 * 阶段学习题目返回参数
 */
@Data
public class ListStageTestExercisesVO {
    //题目编号
    private Integer exercisesNum;

    //题目
    private String exercisesTitle;

    //题目类型
    private String exercisesType;

}
