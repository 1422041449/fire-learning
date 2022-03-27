package cn.jlw.firelearning.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 阶段信息表
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Data
public class StageInfoEditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段号
     */
    @NotNull(message = "阶段号不能为空!")
    private Integer stageNum;

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
