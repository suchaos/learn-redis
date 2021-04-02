package com.suchao.learn.redis.util;

/**
 * 生成短链接的工具类
 * <p>
 * 10进制转 r 进制 -->  Integer.toString(100, r);
 * 但是 r 的范围是 2-36
 * <p>
 * 参考：https://blog.csdn.net/m0_37961948/article/details/80438113
 *
 * @author suchao
 * @date 2021/3/29
 */
public class ShortUrlUtil {

    public static final char[] X36_ARRAY = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();

    /*
        就是 转化为 36 进制而已
     */
    public static String getShortUrl(int number) {
//        StringBuilder result = new StringBuilder();
//        // 模拟计算进制的过程
//        while (number > 0) {
//            result.insert(0, X36_ARRAY[(int) (number % 36)]);
//            number /= 36;
//        }
//        return result.toString();
        return Integer.toString(number, 36);
    }
}
