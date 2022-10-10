
package com.amp.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * DateUtils.
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils{

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 一个月
     */
    public static final int ONE_MONTH = 30;

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDD = "yyyyMMdd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * parseLocalDateTime.
     * out put format:yyyy-MM-dd HH:mm:ss
     *
     * @param str date String
     * @return yyyy -MM-dd HH:mm:ss
     * @see LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(final String str) {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * acquireMinutesBetween.
     *
     * @param start this is start date.
     * @param end   this is start date.
     * @return The number of days between start and end, if end is after start, returns a positive number, otherwise returns a negative number
     */
    public static long acquireMinutesBetween(final LocalDateTime start, final LocalDateTime end) {
        return start.until(end, ChronoUnit.MINUTES);
    }

    /**
     * Format local date time from timestamp local date time.
     *
     * @param timestamp the timestamp
     * @return the local date time
     */
    public static LocalDateTime formatLocalDateTimeFromTimestamp(final Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8));
    }

    /**
     * long 转时间字符串
     * @param format
     * @param time
     * @return
     */
    public static String fromLongToDate(String format, Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(time);
        return sdf.format(date);
    }

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前时间戳
     * @return
     */
    public static long now(){
        return getNowDate().getTime();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateNow()
    {
        return dateTimeNow(YYYYMMDD);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取当前时间
     * @return
     */
    public static Timestamp getTimestamp() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        return currentTime;
    }

    public static String getCurrentTime() {
        Date date = new Date();
        String stringDate = String.format("%tF %<tT", date);
        return stringDate;
    }

    public static Date getDateByFormatString(String stringDate, String formatString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(formatString);
        Date date = dateFormat.parse(stringDate);
        return date;
    }

    public static int getDifferentDays(Date preDate, Date afterDate) {
        int preYear = getYear(preDate);
        int afterYear = getYear(afterDate);
        int preDayOfYear = getDayOfYear(preDate);
        int afterDayOfYear = getDayOfYear(afterDate);
        if (afterYear - preYear == 0) {
            return afterDayOfYear - preDayOfYear;
        } else {
            int diffDay;
            for(diffDay = 0; preYear < afterYear; ++preYear) {
                if (diffDay == 0 && isLeapYear(preYear)) {
                    diffDay = 366 - preDayOfYear;
                } else if (diffDay == 0 && !isLeapYear(preYear)) {
                    diffDay = 365 - preDayOfYear;
                } else if (isLeapYear(preYear)) {
                    diffDay += 366;
                } else {
                    diffDay += 365;
                }
            }

            diffDay += afterDayOfYear;
            return diffDay;
        }
    }

    public static int getDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(6);
        return day;
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(1);
        return year;
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static List<String> getRecent30DateList() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String date = fmt.format(today);
        String maxDateStr = date;
        String minDateStr = "";
        Calendar calc = Calendar.getInstance();
        ArrayList datefor30List = new ArrayList();

        try {
            for(int i = 0; i < 30; ++i) {
                calc.setTime(fmt.parse(maxDateStr));
                calc.add(5, -i);
                Date minDate = calc.getTime();
                minDateStr = fmt.format(minDate);
                datefor30List.add(minDateStr);
            }
        } catch (ParseException var9) {
            var9.printStackTrace();
        }

        return datefor30List;
    }

    /**
     * 时间加减 获取 Timestamp
     *
     * @param day 负数，之后的时间；正数，之前的时间
     * @author Emil.Wu
     * @date 2021-11-12 16:36
     * @version 1.0
     * @return  Timestamp
     */
    public static Timestamp getDayCount(int day) {
        long time = System.currentTimeMillis() - day * MILLIS_PER_DAY;
        return new Timestamp(time);
    }

}
