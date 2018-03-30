package com.leifeng.lib.utils;

import com.leifeng.lib.constants.RegexConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 */
public final class RegexUtils {

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 验证手机号（简单）
     *
     * @param phone 手机号
     */
    public static boolean isMobileSimple(final CharSequence phone) {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, phone);
    }

    /**
     * 验证手机号（精确）
     *
     * @param phone 手机号
     */
    public static boolean isMobileExact(final CharSequence phone) {
        return isMatch(RegexConstants.REGEX_MOBILE_EXACT, phone);
    }

    /**
     * 验证电话号码
     *
     * @param telephone 电话号
     */
    public static boolean isTelePhone(final CharSequence telephone) {
        return isMatch(RegexConstants.REGEX_TEL, telephone);
    }

    /**
     * 验证身份证号码 15 位
     *
     * @param idCard 身份证号
     */
    public static boolean isIDCard15(final CharSequence idCard) {
        return isMatch(RegexConstants.REGEX_ID_CARD15, idCard);
    }

    /**
     * 验证身份证号码 18 位
     *
     * @param idCard 身份证号
     */
    public static boolean isIDCard18(final CharSequence idCard) {
        return isMatch(RegexConstants.REGEX_ID_CARD18, idCard);
    }

    /**
     * 验证邮箱
     *
     * @param email 邮箱
     */
    public static boolean isEmail(final CharSequence email) {
        return isMatch(RegexConstants.REGEX_EMAIL, email);
    }

    /**
     * 验证 URL
     *
     * @param url url地址
     */
    public static boolean isURL(final CharSequence url) {
        return isMatch(RegexConstants.REGEX_URL, url);
    }

    /**
     * 验证汉字
     *
     * @param chinese 中文汉字
     */
    public static boolean isZh(final CharSequence chinese) {
        return isMatch(RegexConstants.REGEX_ZH, chinese);
    }

    /**
     * 验证用户名
     * <p>scope for "a-z", "A-Z", "0-9", "_", "Chinese character"</p>
     * <p>can't end with "_"</p>
     * <p>length is between 6 to 20</p>.
     *
     * @param userName 用户名
     */
    public static boolean isUsername(final CharSequence userName) {
        return isMatch(RegexConstants.REGEX_USERNAME, userName);
    }

    /**
     * 验证 yyyy-MM-dd 格式的日期校验，已考虑平闰年
     *
     * @param date 日期字符串
     */
    public static boolean isDate(final CharSequence date) {
        return isMatch(RegexConstants.REGEX_DATE, date);
    }

    /**
     * 验证 IP 地址
     * @param ipAddress IP 地址
     */
    public static boolean isIpAddress(final CharSequence ipAddress) {
        return isMatch(RegexConstants.REGEX_IP, ipAddress);
    }

    /**
     * 判断是否匹配正则
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 获取正则匹配的部分
     */
    public static List<String> getMatches(final String regex, final CharSequence input) {
        if (input == null) return null;
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     *
     * @param input The input.
     * @param regex The regex.
     * @return the array of strings computed by splitting input around matches of regex
     */
    public static String[] getSplits(final String input, final String regex) {
        if (input == null) return null;
        return input.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     *
     * @param input       The input.
     * @param regex       The regex.
     * @param replacement The replacement string.
     * @return the string constructed by replacing the first matching
     * subsequence by the replacement string, substituting captured
     * subsequences as needed
     */
    public static String getReplaceFirst(final String input,
                                         final String regex,
                                         final String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input       The input.
     * @param regex       The regex.
     * @param replacement The replacement string.
     * @return the string constructed by replacing each matching subsequence
     * by the replacement string, substituting captured subsequences
     * as needed
     */
    public static String getReplaceAll(final String input,
                                       final String regex,
                                       final String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}
