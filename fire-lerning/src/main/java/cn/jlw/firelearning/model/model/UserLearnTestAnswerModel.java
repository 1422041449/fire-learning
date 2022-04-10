package cn.jlw.firelearning.model.model;

import lombok.Data;

@Data
public class UserLearnTestAnswerModel {
    //用户阶段考试题id
    private Integer StageTestId;

    //答案结果
    private Integer answerResult;

    //题型
    private Integer exercisesType;
}
