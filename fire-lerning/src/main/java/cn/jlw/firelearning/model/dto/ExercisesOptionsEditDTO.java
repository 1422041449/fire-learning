package cn.jlw.firelearning.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 答案选项表
 */
@Data
public class ExercisesOptionsEditDTO {
    /**
     * id
     */
    @NotNull(message = "选项id不能为空!")
    private Integer id;

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
    @NotNull(message = "是否为正确答案不能为空!")
    private String ifRight;
}
