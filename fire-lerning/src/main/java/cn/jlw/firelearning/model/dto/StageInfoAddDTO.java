package cn.jlw.firelearning.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 创建阶段
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Data
public class StageInfoAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段名称
     */
    @NotBlank(message = "阶段名称不能为空！")
    private String stageName;

    /**
     * 阶段标题
     */
    @NotBlank(message = "阶段标题不能为空")
    private String stageTitle;

}
