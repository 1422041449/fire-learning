package cn.jlw.firelearning.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 阶段信息返回
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Data
public class StageInfoListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段号
     */
    private Integer stageNum;

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
