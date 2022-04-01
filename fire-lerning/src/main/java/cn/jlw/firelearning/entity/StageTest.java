package cn.jlw.firelearning.entity;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StageTest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段考试id
     */
    @TableId(value = "stage_test_id", type = IdType.AUTO)
    private Integer stageTestId;

    /**
     * 创建时间
     */
    private LocalDateTime crtime;

    /**
     * 阶段学习id
     */
    private Integer stageLearnId;


}
