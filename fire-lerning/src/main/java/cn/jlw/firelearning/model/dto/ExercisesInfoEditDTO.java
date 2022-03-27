package cn.jlw.firelearning.model.dto;

import cn.jlw.firelearning.model.vo.ExercisesOptionsListVO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 题库题目返回参数
 */
@Data
public class ExercisesInfoEditDTO {
    //题目编号
    @NotNull(message = "题目编号不可为空!")
    private Integer exercisesNum;

    //题目
    @NotEmpty(message = "题目不能为空!")
    private String exercisesTitle;

    //题目类型
    @NotNull(message = "题目类型不能为空!")
    private String exercisesType;

    //题目答案集合
    @Valid
    private List<ExercisesOptionsEditDTO> optionsList;
}
