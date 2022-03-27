package cn.jlw.firelearning.model.dto;

import lombok.Data;

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
public class StageInfoListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段名称
     */
    private String stageName;

    /**
     * 阶段标题
     */
    private String stageTitle;
    /**
     * 是否发布(1是2否)
     */
    private Integer ifPublish;

}
