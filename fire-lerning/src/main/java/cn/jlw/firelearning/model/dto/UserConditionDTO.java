package cn.jlw.firelearning.model.dto;

import lombok.Data;

/**
 * 用户阶段情况查询
 */
@Data
public class UserConditionDTO {
    //用户名
    private String username;

    //昵称
    private String name;

    //姓名
    private String realName;

    //阶段
    private Integer stageNum;

    //是否完成学习
    private Integer ifFinishLearn;

    //是否完成考试
    private Integer ifFinishTest;

}
