package cn.jlw.firelearning.model.vo;

import lombok.Data;

/**
 * 答案选项表
 */
@Data
public class ExercisesOptionsListVO {
    /**
     * id
     */
    private Integer id;

    /**
     * 选项编号(A,B,C,D)
     */
    private String optionNum;

    /**
     * 选项内容
     */
    private String optionContent;

    /**
     * 是否是正确答案(1是2否)
     */
    private Integer ifRight;
}
