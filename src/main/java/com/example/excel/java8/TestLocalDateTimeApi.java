package com.example.excel.java8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public class TestLocalDateTimeApi {

    /**
     * LocalDateTime 类分别提供以下API来创建当前日期时间对象和特定日期时间对象。
     * ·  static LocalDateTime now():从默认时区中的系统时钟中获取当前日期时间。
     * ·  static LocalDateTime now(Clock clock):从指定时钟获取当前日期时间。
     * ·  static LocalDateTime now(ZoneId zone)：从指定时区中的系统时钟中获取当前日期时间.
     */
    @Test
    public void test01(){
        LocalDateTime time1 = LocalDateTime.now();
        System.out.println(time1);
        LocalDateTime time2 = LocalDateTime.now(Clock.systemUTC());
        System.out.println(time2);
        LocalDateTime time3 = LocalDateTime.now(Clock.systemDefaultZone());
        System.out.println(time3);
        LocalDateTime time4 = LocalDateTime.now(ZoneId.of("UTC"));
        System.out.println(time4);
        LocalDateTime time5 = LocalDateTime.now(ZoneId.systemDefault());
        System.out.println(time5);
        LocalDateTime time6 = LocalDateTime.of(2022, 6, 16, 18, 9, 30);
        System.out.println(time6);
    }

    /**
     *  int getYear()：获取年份字段。
     *  Month getMonth()：使用Month获取。
     *  int getDayOfMonth()：获取每月一天的字段。
     *  DayOfWeek getDayOfWeek()：获取每周一天的字段，即枚举。DayOfWeek.
     *  int getDayOfYear()：获取一年一天的字段。
     *  时间戳
     */
    @Test
    public void test02(){
        LocalDateTime time = LocalDateTime.now();
        System.out.println("Year : " + time.getYear());
        System.out.println("Month : " + time.getMonth().getValue());
        System.out.println("DayOfMonth : " + time.getDayOfMonth());
        System.out.println("DayOfWeek : " + time.getDayOfWeek());
        System.out.println("DayOfYear : " + time.getDayOfYear());
        System.out.println("Hour : " + time.getHour());
        System.out.println("Minute : " + time.getMinute());
        System.out.println("Second : " + time.getSecond());

        System.out.println("UTC 秒: " + time.toEpochSecond(ZoneOffset.UTC));
        System.out.println("+8 秒: " + time.toEpochSecond(ZoneOffset.of("+8")));
        System.out.println("+8 毫秒: " + time.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println("Date 毫秒: " + new Date().getTime());
        System.out.println("Instant 毫秒: " + Instant.now().toEpochMilli());
        System.out.println("纳秒: " + Instant.now().getNano());
        System.out.println("纳秒: " + Instant.now().getNano());
        System.out.println("纳秒没啥用 毫秒开始后面补0: " + Instant.now().getNano());
    }

    /**
     *  添加或减去到LocalDateTime
     */
    @Test
    public void test03(){
        LocalDateTime time = LocalDateTime.now();
        System.out.println("plusYears : " + time.plusYears(1));
        System.out.println("plusMonths : " + time.plusMonths(1));
        System.out.println("plusWeeks : " + time.plusWeeks(1));
        System.out.println("plusDays : " + time.plusDays(1));

        System.out.println("minusYears : " + time.minusYears(1));
        System.out.println("minusMonths : " + time.minusMonths(1));
        System.out.println("minusWeeks : " + time.minusWeeks(1));
        System.out.println("minusDays : " + time.minusDays(1));
    }

    /**
     *  boolean isAfter(ChronoLocalDateTime other)：检查此日期时间是否在指定日期时间之后。
     *  boolean isBefore(ChronoLocalDateTime other)
     *  boolean isEqual(ChronoLocalDateTime other)
     *  int compareTo(ChronoLocalDateTime other) 将此日期时间与其他日期时间进行比较。
     */
    @Test
    public void test04(){
        LocalDateTime time1 = LocalDateTime.of(2022, 6, 16, 18, 9, 30);
        LocalDateTime time2 = LocalDateTime.of(2022, 7, 16, 18, 9, 30);
        LocalDateTime time3 = LocalDateTime.of(2022, 6, 16, 18, 9, 30);
        LocalDateTime time4 = LocalDateTime.of(2022, 5, 16, 18, 9, 30);
        if (time1.isBefore(time2)){
            System.out.println("之前");
        }
        if (time4.isBefore(time3)){
            System.out.println("之后");
        }
        if (time3.isEqual(time1)){
            System.out.println("相等");
        }
        if (time3.compareTo(time1) == 0){
            System.out.println("相等");
        }
    }

    /**
     * LocalDate toLocalDate() 获取 LocalDate  LocalTime
     */
    @Test
    public void test05(){
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time.toLocalDate());
        System.out.println(time.toLocalTime());
    }


    /**
     * 构造
     */
    @Test
    public void test06(){
        System.out.println("当前时区的本地时间：" + LocalDateTime.now());
        System.out.println("当前时区的本地时间：" + LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        System.out.println("纽约时区的本地时间：" + LocalDateTime.now(ZoneId.of("America/New_York")));
    }

    /**
     * 格式化
     */
    @Test
    public void test07(){
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        // System.out.println("格式化输出：" + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(now));
        System.out.println("格式化输出（本地化输出，中文环境）：" +
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).format(now));

        String dateTimeStrParam = "2022-06-16 18:00:00";
        System.out.println("解析后输出：" +
                LocalDateTime.parse(dateTimeStrParam, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US)));
    }

    /**
     * OffsetDateTime = LocalDate + LocalTime + ZoneOffset(偏移量)
     *
     * OffsetDateTime、ZonedDateTime和Instant它们三都能在时间线上以纳秒精度存储一个瞬间
     * (请注意：LocalDateTime是不行的)，也可理解为某个时刻。
     * OffsetDateTime和Instant可用于模型的字段类型，因为它们都表示瞬间值并且还不可变，所以适合网络传输或者数据库持久化。
     *
     * ZonedDateTime 不适合网络传输/持久化，因为即使同一个ZoneId时区，不同地方获取到瞬时值也有可能不一样❞
     */
    @Test
    public void test08(){
        System.out.println("当前位置偏移量的本地时间：" + OffsetDateTime.now());
        System.out.println("偏移量-4（纽约）的本地时间：：" + OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("-4")));
        System.out.println("纽约时区的本地时间：" + OffsetDateTime.now(ZoneId.of("America/New_York")));
    }

    /**
     * 转换：LocalDateTime -> OffsetDateTime
     *      OffsetDateTime -> LocalDateTime
     */
    @Test
    public void test09(){
        LocalDateTime localDateTime = LocalDateTime.of(2022, 6, 16, 18, 0, 0);
        System.out.println("当前时区（北京）时间为：" + localDateTime);

        // 转换为偏移量为 -4的OffsetDateTime时间
        // 1、-4地方的晚上18点
        System.out.println("-4偏移量地方的晚上18点：" + OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(-4)));
        System.out.println("-4偏移量地方的晚上18点（方式二）：" + localDateTime.atOffset(ZoneOffset.ofHours(-4)));

        System.out.println("ofInstant: 实现本地时间到其它偏移量的对应的时间只能通过其ofInstant()系列构造方法");
        // 2、北京时间晚上18:00 对应的-4地方的时间点
        System.out.println("当前地区对应的-4地方的时间：" +
                OffsetDateTime.ofInstant(localDateTime.toInstant(ZoneOffset.ofHours(8)), ZoneOffset.ofHours(-4)));

        System.out.println("---------------------------------------");

        OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.ofHours(-4));
        System.out.println("-4偏移量时间为：" + offsetDateTime);
        // 转为LocalDateTime 注意：时间还是未变
        System.out.println("LocalDateTime的表示形式：" + offsetDateTime.toLocalDateTime());
    }

    /**
     *  ZonedDateTime = LocalDate +      LocalTime +     ZoneOffset(偏移量) + ZoneId(时区)
     *                  |                   |               |                   |
     *                 2022-06-16T      21:35:34.744    -04:00              [America/New_York]
     *  偏移量并非写死而是根据规则计算出来的
     *  获取本地日期时间的偏移量：三种情况
     *  正常情况：有一个有效的偏移量。对于一年中的绝大多数时间，适用正常情况，即本地日期时间只有一个有效的偏移量
     *  时间间隙情况：没有有效偏移量。这是由于夏令时开始时从“冬季”改为“夏季”而导致时钟向前拨的时候。在间隙中，没有有效偏移量
     *  重叠情况：有两个有效偏移量。这是由于秋季夏令时从“夏季”到“冬季”的变化，时钟会向后拨。在重叠部分中，有两个有效偏移量
     *
     *  ZonedDateTime可简单认为是LocalDateTime和ZoneId的组合。
     *  而ZoneOffset是其内置的动态计算出来的一个次要信息，以确保输出一个瞬时值而存在，某个瞬间偏移量ZoneOffset肯定是确定的。
     *  ZonedDateTime也可以理解为保存的状态相当于三个独立的对象：
     *  LocalDateTime、ZoneId和ZoneOffset。某个瞬间 = LocalDateTime + ZoneOffset。
     *  ZoneId确定了偏移量如何改变的规则。
     *  所以偏移量我们并不能自由设置(不提供set方法，构造时也不行)，因为它由ZoneId来控制的
     */
    @Test
    public void test10(){
        System.out.println("当前位置偏移量的本地时间：" + ZonedDateTime.now());
        System.out.println("纽约时区的本地时间：" +
                ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/New_York")));
        System.out.println("北京时间对应的纽约时区的本地时间：" +
                ZonedDateTime.now(ZoneId.of("America/New_York")));
    }

    /**
     * 转换 LocalDateTime -> ZonedDateTime
     *      OffsetDateTime -> ZonedDateTime
     *
     * OffsetDateTime和ZonedDateTime的区别
     *
     * OffsetDateTime = LocalDateTime + 偏移量ZoneOffset;  ZonedDateTime = LocalDateTime + 时区ZoneId
     * OffsetDateTime可以随意设置偏移值，但ZonedDateTime无法自由设置偏移值，因为此值是由时区ZoneId控制的
     * OffsetDateTime无法支持夏令时等规则，但ZonedDateTime可以很好的处理夏令时调整
     * OffsetDateTime得益于不变性一般用于数据库存储、网络通信;  而ZonedDateTime得益于其时区特性，一般在指定时区里显示时间非常方便，无需认为干预规则
     * OffsetDateTime代表一个瞬时值，而ZonedDateTime的值是不稳定的，需要在某个瞬时根据当时的规则计算出来偏移量从而确定实际值
     * 总的来说，OffsetDateTime和ZonedDateTime的区别主要在于ZoneOffset和ZoneId的区别。
     * 如果只是用来传递数据，使用OffsetDateTime，若想在特定时区里做时间显示那么请务必使用ZonedDateTime。
     */
    @Test
    public void test11() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 6, 17, 18, 0, 0);
        System.out.println("当前时区（北京）时间为：" + localDateTime);

        // 转换为偏移量为 -4的OffsetDateTime时间
        // 1、-4地方的晚上18点
        System.out.println("纽约时区晚上18点：" + ZonedDateTime.of(localDateTime, ZoneId.of("America/New_York")));
        System.out.println("纽约时区晚上18点（方式二）：" + localDateTime.atZone(ZoneId.of("America/New_York")));
        // 2、北京时间晚上18:00 对应的-4地方的时间点
        System.out.println("北京地区此时间对应的纽约的时间：" +
                ZonedDateTime.ofInstant(localDateTime.toInstant(ZoneOffset.ofHours(8)), ZoneOffset.ofHours(-4)));
        System.out.println("北京地区此时间对应的纽约的时间：" +
                ZonedDateTime.ofInstant(localDateTime, ZoneOffset.ofHours(8), ZoneOffset.ofHours(-4)));

        System.out.println("-----------------------------------------------");

        OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.of(2021, 3, 17, 18, 0, 0), ZoneOffset.ofHours(-4));
        System.out.println("-4偏移量时间为：" + offsetDateTime);
        // 转换为ZonedDateTime的表示形式
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.toZonedDateTime());
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.atZoneSameInstant(ZoneId.of("America/New_York")));
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.atZoneSimilarLocal(ZoneId.of("America/New_York")));

        System.out.println("----------------------纽约夏令时时间---偏移量动态转变------------------");

        OffsetDateTime offsetDate = OffsetDateTime.of(LocalDateTime.of(2021, 5, 5, 18, 0, 0), ZoneOffset.ofHours(-5));
        System.out.println("-5偏移量时间为：" + offsetDate);
        // 转换为ZonedDateTime的表示形式
        System.out.println("ZonedDateTime的表示形式：" + offsetDate.toZonedDateTime());
        System.out.println("ZonedDateTime的表示形式：" + offsetDate.atZoneSameInstant(ZoneId.of("America/New_York")));
        System.out.println("ZonedDateTime的表示形式：" + offsetDate.atZoneSimilarLocal(ZoneId.of("America/New_York")));
    }

}
