package cn.jlw.firelearning.utils;

import cn.hutool.core.bean.BeanUtil;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的工具类
 */
public class MyUtils {
    /**
     * 复制集合对象给目标集合
     * @param source    源集合
     * @param targetClass   目标集合类型
     * @param <S>   源类型
     * @param <T>   目标类型
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
}
