package cn.jlw.firelearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户阶段信息表
 * </p>
 *
 * @author jlw
 * @since 2022-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserStageTest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 阶段学习id
     */
    private Integer stageTestId;

    /**
     * 用户答案 A-B
     */
    private String userAnswer;

    /**
     * 阶段号
     */
    private Integer stageNum;

    /**
     *
     */
    private Integer answerResult;
}
