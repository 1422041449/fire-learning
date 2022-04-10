package cn.jlw.firelearning.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class ListLearnCurrentTestVO {
    //id
    private Integer id;

    //阶段学习id
    private Integer stageLearnId;

    //我的答案
    private String userAnswer;

    //我的单选答案
    private String userRadioAnswer;

    //我的多选答案
    private List<String> userMultiAnswer;

    //是否回答
    private Integer ifAnswer = 2;

    //是否回答正确
    private Integer ifAnswerRight = 2;

    //正确答案
    private String rightAnswer;

    //题目号
    private Integer exercisesNum;

    //题目
    private String exercisesTitle;

    //题目类型(1单选2多选)
    private String exercisesType;

    //选项集合
    private List<ListLearnCurrentTestOptionsVO> optionsList;

}
