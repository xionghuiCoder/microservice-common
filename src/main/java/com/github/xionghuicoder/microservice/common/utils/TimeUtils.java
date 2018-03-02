package com.github.xionghuicoder.microservice.common.utils;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能 ：
 * <p/>
 * Author: chengjinhui
 * Data:   2018/3/02
 * Time:   13:19.
 */
public class TimeUtils {


    public static String dateFormatTYpe="yyyy-MM-dd HH:mm:ss";

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(Long currentTime, String formatType) throws Exception {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        return dateToString(date, formatType);
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType) throws Exception {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        return stringToDate(sDateTime, formatType);
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) throws Exception {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            return dateToLong(date);
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 获取当前时间的 long 型值
     */
    public static long nowDateLong() {
        return new Date().getTime();
    }

    /**
     * 获取 目标时间 距 当前的时间 值
     */
    public static long timeToNow(String originTime, String formatType) throws Exception {
        return nowDateLong() - stringToLong(originTime, formatType);
    }

    /**
     * 获取 当前时间 距 目标时间 值
     */
    public static long nowToTime(String targetTime, String formatType) throws Exception {
        return stringToLong(targetTime, formatType) - nowDateLong();
    }

    /**
     * 判断 是否在当前时间 之前
     */
    public static Boolean judgeIfBeforeThanNow(String targetTime, String formatType) throws Exception {

        Date target = null;

        target = stringToDate(targetTime, formatType);


        return target.equals(new Date()) || target.before(new Date());
    }

    /**
     * 判断 是否在当前时间 之后
     */
    public static Boolean judgeIfLaterThanNow(String targetTime, String formatType) {

        Date target = null;
        try {
            target = stringToDate(targetTime, formatType);
        } catch (Exception e) {
           // LOGGER.error("stringToDate is error :{}", e);
            return false;
        }

        return target.after(new Date());
    }

    public static boolean judgeThisTimeRunOrNot(String startTime, String endTime) throws Exception {
        return judgeIfBeforeThanNow(startTime, dateFormatTYpe) && TimeUtils.judgeIfLaterThanNow(endTime, dateFormatTYpe);
    }

    public static boolean judgeThisTimeIsNotStart(String startTime) {
        return judgeIfLaterThanNow(startTime, dateFormatTYpe);
    }

    public static boolean judgeThisTimeIsOutOfDate(String endTime) throws Exception {
        return judgeIfBeforeThanNow(endTime, dateFormatTYpe);
    }


    public static void main(String[] args) {
        String s="08:00:00";
        SimpleDateFormat ss=new SimpleDateFormat();
        Date date=new Date();

        try {
            System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+ Calendar.getInstance().get(Calendar.MINUTE) );
//            System.out.println(ss.parse(s));
        } catch (Exception e){
            System.out.println("exception "+e);
        }
    }

}

