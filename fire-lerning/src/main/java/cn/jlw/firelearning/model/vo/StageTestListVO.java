package cn.jlw.firelearning.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 题库题目返回参数
 */
@Data
public class StageTestListVO {
    //阶段考试题id
    private Integer stageTestId;

    //阶段学习题id
    private Integer stageLearnId;

    //题目编号
    private Integer exercisesNum;

    //题目
    private String exercisesTitle;

    //题目类型
    private String exercisesType;

    //题目答案集合
    private List<ExercisesOptionsListVO> optionsList;

    //创建时间
    private LocalDateTime crtime;

}
