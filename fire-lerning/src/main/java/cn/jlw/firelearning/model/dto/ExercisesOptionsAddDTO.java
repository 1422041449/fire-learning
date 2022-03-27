package cn.jlw.firelearning.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 答案选项表
 */
@Data
public class ExercisesOptionsAddDTO {
    /**
     * 选项编号(A,B,C,D)
     */
    @NotEmpty(message = "选项编号不能为空!")
    private String optionNum;

    /**
     * 选项内容
     */
    @NotEmpty(message = "选项内容不能为空!")
    private String optionContent;

    /**
     * 是否是正确答案(1是2否)
     */
    private String ifRight;
}
