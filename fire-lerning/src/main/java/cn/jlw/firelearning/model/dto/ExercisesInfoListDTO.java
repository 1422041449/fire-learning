package cn.jlw.firelearning.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 题目新增入参
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Data
public class ExercisesInfoListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 题目
     */
    private String exercisesTitle;

    /**
     * 题目类型(1单选2多选)
     */
    private String exercisesType;

}
