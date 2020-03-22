package com.adi.fsmemory.jwt.utils;

import java.time.*;
import java.util.Date;

public class DateTimeUtils {


    public static Date getNowDefault() {
        ZonedDateTime zoneDateTime = getZonedDateTime(LocalTime.now(), LocalDate.now());
        Instant instant = getInstant(zoneDateTime);
        return Date.from(instant);
    }

    /**
     * 获取指定分钟数后的时间
     *
     * @param plus
     * @return
     */
    public static Date plusOfMinutesForNow(long plus) {
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(plus);
        ZonedDateTime zoneDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = getInstant(zoneDateTime);
        return Date.from(instant);
    }

    /**
     * 获取指定秒数后的时间
     *
     * @param plus
     * @return
     */
    public static Date plusOfSecondsForNow(long plus) {
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(plus);
        ZonedDateTime zoneDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = getInstant(zoneDateTime);
        return Date.from(instant);
    }

    /**
     * 获取某个时间在指定秒数间隔后的时间
     * @param target
     * @param plus
     * @return
     */
    public static Date plusOfSecondsForTarget(Date target, long plus) {
        Instant targetInstant = target.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(targetInstant, ZoneId.systemDefault())
                                                   .plusSeconds(plus);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = getInstant(zonedDateTime);
        return Date.from(instant);
    }

    /**
     * 获取某个指定时间在指定分钟间隔后的时间
     * @param target
     * @param plus
     * @return
     */
    public static Date plusOfMinutesForTarget(Date target, long plus) {
        Instant targetInstant = target.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(targetInstant, ZoneId.systemDefault())
                                                   .plusMinutes(plus);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = getInstant(zonedDateTime);
        return Date.from(instant);
    }

    public static LocalDate changeDateToLocalDate(Date target) {
        LocalDateTime localDateTime = changeDateToLocalDateTime(target);
        return localDateTime.toLocalDate();
    }

    public static LocalTime changeDateToLocalTime(Date target) {
        LocalDateTime localDateTime = changeDateToLocalDateTime(target);
        return localDateTime.toLocalTime();
    }

    public static LocalDateTime changeDateToLocalDateTime(Date target) {
        Instant instant = target.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static ZonedDateTime getZonedDateTime(LocalTime localTime, LocalDate localDate) {
        LocalDateTime localDateTime = localTime.atDate(localDate);
        return ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
    }

    public static Instant getInstant(ZonedDateTime zoneDateTime) {
        return zoneDateTime.toInstant();
    }


    /**
     * 如果当前和目标时间相等，{@code compareDateTime()} 返回0
     * @param target
     * @return
     */
    public static boolean isItEquals(Date target) {
        return compareDateTime(target) == 0;
    }

    /**
     * 如果当前大于目标时间，{@code compareDateTime()} 返回大于零的整型
     * @param target
     * @return
     */
    public static boolean isItPrevious(Date target) {
        return compareDateTime(target) > 0;
    }

    /**
     * 如果当前小于目标时间，{@code compareDateTime()} 返回小于零的整型
     * @param target
     * @return
     */
    public static boolean isItAfter(Date target) {
        return compareDateTime(target) < 0;
    }

    private static int compareDateTime(Date target) {
        LocalDateTime targetLocalDateTime = changeDateToLocalDateTime(target);
        LocalDateTime now = LocalDateTime.now();
        return now.compareTo(targetLocalDateTime);
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1 = localDateTime.plusMinutes(900);
        int i = localDateTime.compareTo(localDateTime1);
        System.out.println(localDateTime + "|" +localDateTime1 + "|" + i);
    }
}