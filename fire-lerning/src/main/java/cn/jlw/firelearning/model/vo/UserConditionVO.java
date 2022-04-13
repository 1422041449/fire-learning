package cn.jlw.firelearning.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserConditionVO {
    //阶段号
    private String stageNum;

    //阶段名称
    private String stageName;

    //阶段标题
    private String stageTitle;

    //用户名
    private String username;

    //昵称
    private String name;

    //姓名
    private String realName;

    //学习进度
    private Integer progress;

    //考试成绩
    private Integer score;

}
