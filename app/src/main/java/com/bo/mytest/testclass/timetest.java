package com.bo.mytest.testclass;

import com.bo.mytest.utils.TimeUtil;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author:jianbo
 * <p>
 * Create Time:2020/1/8 14:52
 * <p>
 * Email:1245092675@qq.com
 * <p>
 * Describe:
 */
public class timetest {
    public static void main(String[] args) {
        Date old = TimeUtil.stringToDate("2020-1-7 14:24:24", TimeUtil.yyyy_MM_dd_HH_mm_ss);
        Date now = TimeUtil.stringToDate("2020-1-9 12:24:24", TimeUtil.yyyy_MM_dd_HH_mm_ss);
        System.out.println("old:"+TimeUtil.dateToLong(old));
        System.out.println("间隔:"+TimeUtil.getDescriptionTimeFromTimestamp(TimeUtil.dateToLong(old)));

        Pattern p = Pattern.compile("^(1[^012]\\d{9})|(\\+\\d{1,5}\\s\\d{1,14})$");
        Pattern p2 = Pattern.compile(".*[oO][nN][cC][eE].*");
        Matcher m = p.matcher("+86 13245678911234");
        Matcher m2 = p2.matcher("e was There oncE was e was");
        System.out.println("手机号："+m.matches());
        System.out.println("大小写："+m2.matches());
    }
}
