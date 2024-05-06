package com.mfsys.comm.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Service
public class DateTimeUtil {
    private static String timezone;

    public static LocalDate noSQLToLocalDateForSchedule(Map<String, Object> mongoDate) {

        Object dateTime = mongoDate.get(JsonPropertyConst.NOSQL_DATE_PREFIX);
        String date = "";
        if (dateTime instanceof Map) {
            date = String.valueOf(LocalDateTime.parse(String.valueOf(
                            new Date(Long.parseLong(String.valueOf(((Map<?, ?>) dateTime).get(JsonPropertyConst.NOSQL_NUMBER_LONG_PREFIX))))),
                    DateTimeFormatter.ofPattern(DateFormatterConstant.DATETIME_FORMAT)));
        } else {
            date = (String) dateTime;
        }
        LocalDate localDate = null;
        try {
            localDate = changeDateTimeForSchedule(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDate;

    }

    public static LocalDate changeDateTimeForSchedule(String dt) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(dt, formatter);
        int hour = localDateTime.getHour();
        Instant instant = localDateTime.atZone(ZoneId.of("UTC")).toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
        int timezoneHour = zonedDateTime.getHour();
//        if (timezoneHour >= hour) {
//        	localDateTime = localDateTime.plusDays(0);
//        }
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    public static LocalDate noSQLToLocalDate(Map<String, Object> mongoDate) {

        Object dateTime = mongoDate.get(JsonPropertyConst.NOSQL_DATE_PREFIX);
        String date = "";
        if (dateTime instanceof Map) {
            date = String.valueOf(LocalDateTime.parse(String.valueOf(
                            new Date(Long.parseLong(String.valueOf(((Map<?, ?>) dateTime).get(JsonPropertyConst.NOSQL_NUMBER_LONG_PREFIX))))),
                    DateTimeFormatter.ofPattern(DateFormatterConstant.DATETIME_FORMAT)));
        } else {
            date = (String) dateTime;
        }
        LocalDate localDate = null;
        try {
            date = changeDateTime(date);
            localDate = LocalDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDate;

    }

    public static String changeDateTime(String dt) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDate = LocalDateTime.parse(dt, formatter);
        Instant instant = localDate.atZone(ZoneId.of("UTC")).toInstant();
        Date date1 = Date.from(instant);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone(DateTimeUtil.timezone));
        return sdf.format(calendar.getTime());
    }

    public static String localDateAndTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String strDateTime = formatter.format(date);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(strDateTime);
        return "new ISODate('" + zonedDateTime + "')";

    }
    public  static String ucolocalDateAndTime() {
        Date date = new Date();
        // Update the format to match the desired output
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        return formatter.format(date);
    }

    public static String localDateToNoSql(LocalDate localDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String strDateTime = formatter.format(date);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(strDateTime);
        return "new ISODate('" + zonedDateTime + "')";

    }

    public static int calculateMonths(LocalDate startDate, LocalDate endDate) {
        Period period = Period.between(startDate, endDate);
        return period.getYears() * 12 + period.getMonths();
    }

    @Value("${app.server.timezone}")
    public void setTimezone(String time) {
        DateTimeUtil.timezone = time;
    }

}
