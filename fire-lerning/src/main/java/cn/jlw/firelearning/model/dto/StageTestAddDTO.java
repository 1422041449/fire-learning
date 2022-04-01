package cn.jlw.firelearning.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 阶段考试题目表
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Data
public class StageTestAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段学习id
     */
    private Integer stageLearnId;

    /**
     * 阶段号
     */
    private Integer stageNum;
}
