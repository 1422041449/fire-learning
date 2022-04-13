package cn.jlw.firelearning.utils;

import cn.hutool.core.bean.BeanUtil;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的工具类
 */
public class MyUtils {
    /**
     * 复制集合对象给目标集合
     *
     * @param source      源集合
     * @param targetClass 目标集合类型
     * @param <S>         源类型
     * @param <T>         目标类型
     * @return
     */
    @SneakyThrows
    public static <S, T> List<T> copyList(List<S> source, Class<T> targetClass) {
        List<T> targetRes = new ArrayList<>();
        for (S s : source) {
            T t = targetClass.newInstance();
            BeanUtil.copyProperties(s, t);
            targetRes.add(t);
        }
        return targetRes;
    }

    /**
     * 比较两个字符串的字符是否相同
     */
    public static boolean compareTwoStrChar(String str1, String str2) {
        boolean res = false;
        //将字符串转为字符数组
        char[] str1Char = str1.toCharArray();
        char[] str2Char = str2.toCharArray();
        //对字符串进行字符排序
        Arrays.sort(str1Char);
        Arrays.sort(str2Char);
        //比较字符
        if (Arrays.equals(str1Char, str2Char)) {
            res = true;
        }
        return res;
    }

    /**
     * 判断A字符串是等于B字符串，还是包含B，还是什么都不是
     * 1是等于,2不是,3包含
     */
    public static int containStr(String source, String target) {
        int res = 2;
        //将字符串转为字符数组
        char[] sourceChar = source.toCharArray();
        char[] targetChar = target.toCharArray();
        //对字符串进行字符排序
        Arrays.sort(sourceChar);
        Arrays.sort(targetChar);
        String sourceStr = "";
        String targetStr = "";
        for (char c : sourceChar) {
            sourceStr += c;
        }
        for (char c : targetChar) {
            targetStr += c;
        }
        if (sourceStr.equals(targetStr)) {
            res = 1;
        } else if (sourceStr.contains(targetStr)) {
            res = 3;
        }
        return res;
    }


    /**
     * 测试main方法
     */
    public static void main(String[] args) {
        String a = "ABC";
        String b = "BC";
        int i = containStr(a, b);
        System.out.println(i);
    }

}
