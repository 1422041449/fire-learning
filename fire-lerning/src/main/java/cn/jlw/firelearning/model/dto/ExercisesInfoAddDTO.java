package cn.jlw.firelearning.model.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 题目新增入参
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Data
public class ExercisesInfoAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 题目
     */
    @NotEmpty(message = "题目不能为空!")
    private String exercisesTitle;

    /**
     * 题目类型(1单选2多选)
     */
    @NotNull(message = "题目类型不能为空!")
    private String exercisesType;

    /**
     * 答案
     */
    @Valid
    private List<ExercisesOptionsAddDTO> optionsList;
}
