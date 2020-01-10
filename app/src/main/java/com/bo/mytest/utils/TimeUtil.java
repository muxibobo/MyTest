package com.bo.mytest.utils;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TimeUtil {

    public final static String yyyy = "yyyy";
    public final static String yyyyMMdd = "yyyyMMdd";
    public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public final static String yyyy_MM_dd = "yyyy-MM-dd";
    public final static String HH_mm = "HH:mm";
    public final static String HH_mm_ss = "HH:mm:ss";
    public final static String MM_dd_HH_mm = "MM-dd HH:mm";

    public final static String MM月dd日_HH时_mm分 = "MM月dd日 HH时:mm分";
    public final static String MM月dd日 = "MM月dd日";

    public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    public static final int YEAR = 365 * 24 * 60 * 60;// 年
    public static final int MONTH = 30 * 24 * 60 * 60;// 月
    public static final int DAY = 24 * 60 * 60;// 天
    public static final int HOUR = 60 * 60;// 小时
    public static final int MINUTE = 60;// 分钟

    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     */
    public static String getNowFormatTime(String format) {
        return dateToString(new Date(), format);

    }

    public static String getNowYMDHMS() {
        return getNowFormatTime(yyyy_MM_dd_HH_mm_ss);
    }

    public static String getNowYMDHMS2() {
        return getNowFormatTime(yyyyMMddHHmmss);
    }

    public static String getNowYMD() {
        return getNowFormatTime(yyyy_MM_dd);
    }

    public static String getNowYMD2() {
        return getNowFormatTime(yyyyMMdd);
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        if (data == null)
            return "";
        return new SimpleDateFormat(formatType).format(data);
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        if (isEmpty(strTime))
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        try {
            return formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType) {
        if (isEmpty(formatType))
            return new Date(currentTime);
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        return stringToDate(sDateTime, formatType); // 把String类型转换为Date类型

    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        if (date == null)
            return 0;
        return date.getTime();
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType) {
        return dateToString(new Date(currentTime), formatType); // date类型转成String
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            return dateToLong(date); // date类型转成long类型
        }
    }

    public static String getMMddHHmm(long time) {
        return longToString(time, "MM-dd HH:mm");
    }

    public static String getHHmm(long time) {
        return longToString(time, HH_mm);
    }

    public static boolean isContains(String stime, String etime) {
        String format = yyyy_MM_dd_HH_mm_ss;
        if (stime == null || stime.length() < 12)
            format = yyyy_MM_dd;
        long sL = stringToLong(stime, format);
        long eL = stringToLong(etime, format);
        long nL = System.currentTimeMillis();
        return nL >= sL & nL <= eL;
    }

    public static boolean isOut(String etime) {
        String format = yyyy_MM_dd_HH_mm_ss;
        if (etime == null || etime.length() < 12)
            format = yyyy_MM_dd;
        long eL = stringToLong(etime, format);
        long nL = System.currentTimeMillis();
        return nL >= eL;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * 获取聊天时间
     */
    public static String getChatTime(long timesamp) {
        long clearTime = timesamp;// *1000;
        String result = "";
        SimpleDateFormat sdf;
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(clearTime);

        sdf = new SimpleDateFormat("yyyy");
        int tempYear = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        if (tempYear != 0)
            return longToString(clearTime, yyyy_MM_dd_HH_mm);

        sdf = new SimpleDateFormat("MM");
        int tempMouth = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        if (tempMouth != 0)
            return getMMddHHmm(clearTime);

        sdf = new SimpleDateFormat("dd");
        int tempDay = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        switch (tempDay) {
            case 0:
                result = "今天 " + getHHmm(clearTime);
                break;
            case 1:
                result = "昨天 " + getHHmm(clearTime);
                break;
            case 2:
                result = "前天 " + getHHmm(clearTime);
                break;

            default:
                result = getMMddHHmm(clearTime);
                break;
        }
        return result;
    }

    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {

        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        System.out.println("timeGap: " + timeGap);
        String timeStr = null;
        if (timeGap < MINUTE) {
            timeStr = "刚刚";
        } else if (timeGap < HOUR) {
            timeStr = timeGap / MINUTE + "分钟前";
        } else if (timeGap < DAY) {
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap < MONTH) {
            // 天数不按照满24小时算 现实生活是按照隔了多少天来算的
            try {
                // timeGap -= System.currentTimeMillis() % DAY; 这个不准确
                String[] arr = getNowFormatTime("HH:mm:ss").split(":");
                timeGap -= (Integer.valueOf(arr[0]) * 3600 + Integer.valueOf(arr[1]) * 60 + Integer.valueOf(arr[2]));
                timeStr = timeGap / DAY + 1 + "天前";
            } catch (Exception e) {
                timeStr = timeGap / DAY + "天前";
            }
        } else if (timeGap < YEAR) {

            if (timeGap % MONTH / DAY > 15)
                timeStr = timeGap / MONTH + "个半月前";
            else
                timeStr = timeGap / MONTH + "月前";
        } else {
            timeStr = timeGap / YEAR + "年前";
        }
        return timeStr;
    }

    //=========================================

    /**
     * 时间格式转换
     *
     * @param secondsTime 秒
     * @param timeType    时间类型
     * @param mReturn     时间回调
     * @return
     */
    public static String getTimeFormatChange(long secondsTime, TimeType timeType, TimeChangeCallBack mReturn) {
        long days = secondsTime / (DAY);
        long hours = (secondsTime % (DAY)) / HOUR;
        long minutes = (secondsTime % (HOUR)) / MINUTE;
        long seconds = secondsTime % MINUTE;
        //完成状态时间格式
        StringBuilder sb = new StringBuilder();
        if (timeType == TimeType.TYPE_HHMMSS) {
            DecimalFormat decFormat = new DecimalFormat("00");
            if (days > 0 || hours > 0) {
                sb.append(decFormat.format(hours)).append(":").append(decFormat.format(minutes)).append(":").append(decFormat.format(seconds));
            } else {
                sb.append("00").append(":").append(decFormat.format(minutes)).append(":").append(decFormat.format(seconds));
            }
        } else if (timeType == TimeType.TYPE_NUMBER) {
            //充电中状态时间格式
            if (days > 0 || hours > 0) {
                sb.append(new BigDecimal(secondsTime)
                        .divide(new BigDecimal(HOUR), 1, BigDecimal.ROUND_HALF_UP).toPlainString());
                if (mReturn != null)
                    mReturn.timeDataBack("H");
            } else if (minutes > 0) {
                sb.append(new BigDecimal(secondsTime)
                        .divide(new BigDecimal(MINUTE), 1, BigDecimal.ROUND_HALF_UP).toPlainString());
                if (mReturn != null)
                    mReturn.timeDataBack("min");
            } else {
                sb.append(secondsTime);
                if (mReturn != null)
                    mReturn.timeDataBack("S");
            }
        }
        return sb.toString();
    }

    public interface TimeChangeCallBack {
        void timeDataBack(Object timeBack);
    }

    public enum TimeType {
        /**
         * 时分秒格式：HH:MM:SS
         */
        TYPE_HHMMSS,
        /**
         * 小数点格式：36.6min
         */
        TYPE_NUMBER
    }
}