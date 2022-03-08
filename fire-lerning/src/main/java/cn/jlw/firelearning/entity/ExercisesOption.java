package cn.jlw.firelearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 题目选项表
 * </p>
 *
 * @author jlw
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExercisesOption implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 题目编号
     */
    private Integer exercisesNum;

    /**
     * 选项编号(A,B,C,D)
     */
    private String optionNum;

    /**
     * 选项内容
     */
    private String optionContent;

    /**
     * 是否是正确答案(1是2否)
     */
    private Integer ifRight;


}
