package cn.jlw.firelearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段号
     */
    @TableId(value = "stage_num", type = IdType.AUTO)
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
