package cn.jlw.firelearning.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 阶段学习题目表
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Data
public class StageTestDeleteDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 阶段考试题id
     */
    private Integer stageTestId;
    /**
     * 阶段号
     */
    private Integer stageNum;
}
