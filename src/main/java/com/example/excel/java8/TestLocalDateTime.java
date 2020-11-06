package com.example.excel.java8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class TestLocalDateTime {
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

        LocalDateTime time = now.parse(str1, dft2);
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
        System.out.println(period.getMonths());
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
    @Test
    public void test2(){
        Instant instant = Instant.now();    //默认获取 UTC 时区
        System.out.println(instant);
        OffsetDateTime time = instant.atOffset(ZoneOffset.ofHours(8));  //偏移 8 个小时
        System.out.println(time);
        long milli = instant.toEpochMilli();    //毫秒
        System.out.println(milli);
        Instant instant1 = Instant.ofEpochSecond(60);   //在元年，1970年上进行时间运算
        System.out.println(instant1);
    }

    //1.LocalDate  LocalTime  LocalDateTime
    @Test
    public void test1(){
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time);

        LocalDateTime time1 = LocalDateTime.of(2020, 11, 06, 16, 44, 32);
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
