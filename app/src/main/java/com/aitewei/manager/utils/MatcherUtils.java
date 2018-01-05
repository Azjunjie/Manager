package com.aitewei.manager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式匹配工具类
 * Created by zjj on 2017/10/30.
 */

public class MatcherUtils {

    /**
     * 匹配手机号是否正确
     *
     * @param phoneNum 手机号
     * @return true-正确  false-错误
     */
    public static boolean onMatcherPhone(String phoneNum) {
        String pattern = "(13\\d|14[57]|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[0589]\\d{7}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    /**
     * 匹配密码是否正确
     *
     * @param pwd 密码
     * @return true-正确  false-错误
     */
    public static boolean onMatcherPwd(String pwd) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

}
