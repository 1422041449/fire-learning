package cn.jlw.firelearning.model.vo;

import cn.jlw.firelearning.model.dto.ExercisesOptionsAddDTO;
import lombok.Data;

import java.util.List;

/**
 * 题库题目返回参数
 */
@Data
public class ExercisesInfoListVO {
    //题目编号
    private Integer exercisesNum;

    //题目
    private String exercisesTitle;

    //题目类型
    private Integer exercisesType;

    //题目答案集合
    private List<ExercisesOptionsListVO> optionsList;
}
