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
 * 题目表
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExercisesInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 习题编号
     */
    @TableId(value = "exercises_num", type = IdType.AUTO)
    private Integer exercisesNum;

    /**
     * 题目
     */
    private String exercisesTitle;

    /**
     * 题目类型(1单选2多选)
     */
    private String exercisesType;
    /**
     * 创建时间
     */
    private LocalDateTime crtime;

}
