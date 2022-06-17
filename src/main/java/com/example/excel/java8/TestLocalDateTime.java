package com.example.excel.java8;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class TestLocalDateTime {

    public static String getEndDayOfMonth(Date date,Integer month){
        date = Optional.ofNullable(date).orElse(new Date());
        month = Optional.ofNullable(month + 1).orElse(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE) - 1);
        return sdf.format(cal.getTime()) + " 23:59:59";
    }

    //MM月  01月
    public static String dateToStrMM(Date date){
        if (date == null){
            return "00月";
        }
        String str = dateToStr(date, "MM");
        return str + "月";
    }

    //MM/dd  5/23
    public static String dateToStrMd(Date date){
        if (date == null){
            return "00/00";
        }
        String str = dateToStr(date, "MM/dd");
        String sub = str.substring(0, 1);
        if (sub.equals("0")){
            str = str.substring(1, str.length());
        }
        return str;
    }

    // yyyy-MM-dd
    public static String dateToStr(Date date, String ptt){
        if (ptt == null || "".equals(ptt)){
            ptt = "yyyy-MM-dd";
        }
        if (date == null){
            return "0000-00-00";
        }
        return new SimpleDateFormat(ptt).format(date);
    }

    //yyyy-MM-dd 23:59:59
    public static String dateToStr235959(Date date){
        if (date == null){
            return "0000-00-00 23:59:59";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date) + " 23:59:59";
    }

    //String -> date
    public static Date strToDate(String date, String pattern){
        if (date == null || "".equals(date)){
            return null;
        }
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // amount 个月前时间
    public static Date lastMonth(Date t, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(t);
        calendar.add(Calendar.MONTH, amount);
        Date time = calendar.getTime();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(time);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // amount 月前 yyyy-MM-dd HH:mm:ss
    public static Date lastMonth(Date date, int amount, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        Date time = calendar.getTime();
        String format = new SimpleDateFormat(pattern).format(time);
        Date date2 = null;
        try {
            date2 = new SimpleDateFormat(pattern).parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }

    // amount 月前的月份的一号 yyyy-MM-01 00:00:00
    public static String pastFewYears(Date date, int amount) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        return sdf.format(cal.getTime()) + "-01 00:00:00";
    }

    //本月初至今
    public static List<Date> lastMonthHitherto(Date date) {
        List<Date> list = new ArrayList<>();
        String str = dateToStr(date, "yyyy-MM-dd");
        int i = 0;
        for (;;){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(strToDate(pastFewYears(date, 0), "yyyy-MM-dd HH:mm:ss"));
            calendar.add(Calendar.DAY_OF_MONTH, i);
            Date time = calendar.getTime();
            list.add(time);
            String str1 = dateToStr(time, "yyyy-MM-dd");
            if (Objects.equals(str1, str)){
                break;
            }
            i++;
        }
        return list;
    }

    //12个月前至今的每月最后一天的 yyyy-MM-dd 23:59:59
    public static List<Date> elevenMonthHitherto(Date date) {
        List<Date> list = new ArrayList<>();
        String s1 = getEndDayOfMonth(new Date(), 0);
        int i = -12;
        for (;;){
            String s = getEndDayOfMonth(date, i);
            Date date1 = strToDate(s, "yyyy-MM-dd HH:mm:ss");
            list.add(date1);
            if (s.equals(s1)){
                break;
            }
            i++;
        }
        return list;
    }

    //24 -> 12个月前 的每月最后一天的 yyyy-MM-dd 23:59:59
    public static List<Date> twentyFourMonthHitherto(Date date) {
        List<Date> list = new ArrayList<>();
        int i = -24;
        for (;;){
            String s = getEndDayOfMonth(date, i);
            Date date1 = strToDate(s, "yyyy-MM-dd HH:mm:ss");
            list.add(date1);
            if (i == -12){
                break;
            }
            i++;
        }
        return list;
    }

    //n天前至今
    public static List<Date> agoDayHitherto(Date date, int agoDay) {
        String str = dateToStr(date, "yyyy-MM-dd");
        return getListDate(str, agoDay, strToDate(str, "yyyy-MM-dd"));
    }

    //返回指定日期到现在的每一天 yyyy-MM-dd
    public static List<Date> everyDay(Date date) {
        String str = dateToStr(new Date(), "yyyy-MM-dd");
        return getListDate(str, 0, date);
    }

    public static List<Date> getListDate(String str, int i, Date date){
        List<Date> list = new ArrayList<>();
        for (;;){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, i);
            Date time = calendar.getTime();
            list.add(time);
            String str1 = dateToStr(time, "yyyy-MM-dd");
            if (Objects.equals(str1, str)){
                break;
            }
            i++;
        }
        return list;
    }

    public static Integer getDays(Date nowDate, Date overDate){
        String odates = new SimpleDateFormat("yyyy-MM").format(overDate) + "-01";
        Date odate = strToDate(odates, "yyyy-MM-dd");
        Date ndate = strToDate(dateToStr(nowDate, "yyyy-MM-dd"), "yyyy-MM-dd");
        long days = (ndate.getTime() / 1000 - odate.getTime() / 1000) / 24 / 60 / 60 + 1;
        return (int)days;
    }

    @Test
    public void test9(){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();
        String str1 = DateTimeFormatter.ofPattern("yyyy-MM").format(start) + "-01";
        LocalDateTime date1 = LocalDateTime.parse(str1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    }

    //6.ZonedDate   ZonedTime   ZonedDateTime
    @Test
    public void test8(){
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("America/Sitka"));//该时区下的当前时间
        System.out.println(ldt);

        LocalDateTime ldt1 = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime ldt2 = ldt1.atZone(ZoneId.of("Asia/Shanghai"));
        //  2020-11-08T18:07:04.727+  08:00(与UTC相差8小时)  [Asia/Shanghai]
        System.out.println(ldt2);
    }

    @Test
    public void test7(){    //所有时区
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }

    //5.DateTimeFormatter 格式化时间和日期
    @Test
    public void test6(){
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        LocalDateTime now = LocalDateTime.now();
        String str = now.format(dtf);
        System.out.println(str);

        DateTimeFormatter dft2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String str1 = dft2.format(now);
        System.out.println(str1);

        LocalDateTime time = LocalDateTime.parse(str1, dft2);
        System.out.println(time);
    }

    //4.TemporalAdjuster 时间矫正器
    @Test
    public void test5(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt1 = ldt.withDayOfMonth(10);
        System.out.println(ldt1);

        LocalDateTime ldt2 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt2);

        //自定义，下一个工作日
        LocalDateTime time = ldt.with((t) -> {
            LocalDateTime ldt3 = (LocalDateTime) t;
            DayOfWeek dow = ldt3.getDayOfWeek();
            if (dow.equals(DayOfWeek.FRIDAY)) {
                return ldt3.plusDays(3);
            } else if (dow.equals(DayOfWeek.SATURDAY)) {
                return ldt3.plusDays(2);
            } else {
                return ldt3.plusDays(1);
            }
        });
        System.out.println(time);
    }

    //3.Duration 计算两个时间之间的间隔  Period  计算两个日期之间的间隔
    @Test
    public void test4() {
        LocalDate date = LocalDate.of(2020, 10, 6);
        LocalDate now = LocalDate.now();
        Period period = Period.between(date, now);
        System.out.println(period.getDays());
        System.out.println(period.getMonths());
        System.out.println(period.getYears());
    }
    @Test
    public void test3() {
        Instant instant = Instant.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        Instant instant1 = Instant.now();
        Duration between = Duration.between(instant, instant1);
        System.out.println(between.toMillis());
        LocalDateTime now = LocalDateTime.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        LocalDateTime now1 = LocalDateTime.now();
        Duration between1 = Duration.between(now, now1);
        System.out.println(between1.toMillis());
    }

    //2.Instant 时间戳
    //Instant 类还是 Java8 中 提供的新的 日期时间类LocalDateTime 与 原来的 java.util.Date 类之间转换的桥梁
    @Test
    public void test2(){
        Instant instant = Instant.now();    //默认获取 UTC 时区
        System.out.println(instant);
        OffsetDateTime time = instant.atOffset(ZoneOffset.ofHours(8));  //偏移 8 个小时
        System.out.println(time);
        long milli = instant.toEpochMilli();    //毫秒
        System.out.println(milli);
        System.out.println(new Date().getTime());

        Instant instant1 = Instant.ofEpochSecond(new Date().getTime()/1000);   //在元年，1970年上进行时间运算
        System.out.println(instant1);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant1, ZoneId.systemDefault());
        System.out.println(localDateTime);
    }

    //1.LocalDate  LocalTime  LocalDateTime
    @Test
    public void test1(){
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time);

        LocalDateTime time1 = LocalDateTime.of(2020, 11, 6, 16, 44, 32);
        System.out.println(time1);

        LocalDateTime time2 = time.plusYears(2);
        System.out.println(time2);

        LocalDateTime time3 = time.minusMonths(2);
        System.out.println(time3);

        System.out.println(time.getYear());
        System.out.println(time.getMonthValue());
        System.out.println(time.getDayOfMonth());
        System.out.println(time.getHour());
        System.out.println(time.getMinute());
        System.out.println(time.getSecond());

    }


}
