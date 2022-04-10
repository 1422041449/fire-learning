package cn.jlw.firelearning.model.dto;

import lombok.Data;

/**
 * 提交考试答案入参
 */
@Data
public class CommitTestAnswerDTO {
    //用户阶段考试题id
    private Integer id;

    //用户答案
    private String userAnswer;

    //题目类型
    private Integer exercisesType;

    //题目编号
    private String exercisesNum;
}
