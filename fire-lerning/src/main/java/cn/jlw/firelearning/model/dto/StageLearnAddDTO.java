package cn.jlw.firelearning.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 阶段学习题目表
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Data
public class StageLearnAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段号
     */
    private Integer stageNum;

    /**
     * 习题编号
     */
    private Integer exercisesNum;
}
