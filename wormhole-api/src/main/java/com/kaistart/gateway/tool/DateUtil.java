package com.kaistart.gateway.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author chenhailong
 * @date 2019年2月14日 上午10:45:41
 */
public class DateUtil {
  public final static String DATE_FORMAT_PARTNER_1 = "yyyy-MM-dd HH:mm:ss";
  public final static String DATE_FORMAT_PARTNER_2 = "yyyyMMddHHmmss";
  public final static String DATE_FORMAT_SHORT = "yyyy-MM-dd";

  /**
   * 获取yyyyMM格式日期
   * 
   * @return
   */
  public static String getFormatMonth() {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    return sdf.format(c.getTime());
  }

  /**
   * 获取yyyy-MM-dd格式日期
   * 
   * @return
   */
  public static String getFormatDate() {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(c.getTime());
  }

  public static String format(Date date,String pattern){
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(date);
  }

  /**
   * 获取前一天yyyy-MM-dd格式日期
   * 
   * @return
   */
  public static String getPreFormatDate() {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DAY_OF_MONTH, -1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(c.getTime());
  }
  
  public static Date getPreHoursDate(Date date, int hours){
    Calendar calendar = Calendar.getInstance();    
    calendar.setTime(date); 
    calendar.add(Calendar.HOUR, 0-hours);    
    return calendar.getTime();
  }
  
  public static Date getPreMintuesDate(Date date, int min){
    Calendar calendar = Calendar.getInstance();    
    calendar.setTime(date); 
    calendar.add(Calendar.MINUTE, 0-min);    
    return calendar.getTime();
  }

  /**
   * 获取yyyy-MM-dd hh24:mi:ss格式日期
   * 
   * @return
   */
  public static String getFormatTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(System.currentTimeMillis());
  }

  public static String getCurrentTimeDaySpan(int day) {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 当天一天后
    c.add(Calendar.DATE, day);
    return sdf.format(c.getTime());
  }

  /**
   * 返回值格式：yyyyMMddHH
   * 
   * @param hours
   * @return
   */
  public static String getCurrentTimeHourSpan(int hours) {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
    // 当天一天后
    c.add(Calendar.HOUR, hours);
    return sdf.format(c.getTime());
  }
  /**
   * 返回格式 yyyy-MM-dd HH:mm:ss
   * 如果是null 返回""
   * @param date
   * @return
   */
  public static String getFormatTime(Date date) {
    if (date == null) {
      return "";
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(date.getTime());
  }

  public static Date getDateFromFormat(String formatDay) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      return sdf.parse(formatDay);
    } catch (Exception e) {
      return new Date();
    }
  }
  
  public static String getFormatDate(Date date) {
    if (date == null) {
      return "";
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date.getTime());
  }


  public static String getYestodayFormatDate() {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DAY_OF_MONTH, -1);
    SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
    return sdf.format(c.getTime());
  }

  public static int compareTo(Date date1, Date date2) {
    try {
      Calendar cal1 = Calendar.getInstance();
      Calendar cal2 = Calendar.getInstance();
      cal1.setTime(date1);
      cal2.setTime(date2);
      cal1.set(Calendar.SECOND, 0);
      cal1.set(Calendar.MILLISECOND, 0);
      cal2.set(Calendar.SECOND, 0);
      cal2.set(Calendar.MILLISECOND, 0);
      return cal1.compareTo(cal2);
    } catch (Exception e) {
      return -1;
    }

  }
  
  /**
   * 时间比较
   * @param date1
   * @param date2
   * @return 
   * date1 在 date2 前返回 -1
   * date1 在 date2 后返回 1
   * date1 和 date2 相同返回0 
   */
  public static int compareTimeTo(Date date1, Date date2) {
    try {
      Calendar cal1 = Calendar.getInstance();
      Calendar cal2 = Calendar.getInstance();
      cal1.setTime(date1);
      cal2.setTime(date2);
      return cal1.compareTo(cal2);
    } catch (Exception e) {
      return -1;
    }

  }

  /**
   * 获取前一天yyyy-MM-dd格式日期
   * 
   * @return
   */
  public static String getNextFormatDateByDays(int days) {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DAY_OF_MONTH, days);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(c.getTime());
  }

  public static Date parseDateTime(String dateString) {
    return parseDate(dateString, "yyyy-MM-dd HH:mm:ss");
  }

  public static Date parseShortDateTime(String dateString){
    return parseDate(dateString,DATE_FORMAT_SHORT);
  }

  public static Date parseDate(String dateString, String format) {
    if (isEmpty(format)) {
      return null;
    } else {
      try {
        return (new SimpleDateFormat(format)).parse(dateString);
      } catch (Exception var3) {
        return null;
      }
    }
  }

  public static String getAfterFormatDate(Date date, int days) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DAY_OF_MONTH, days);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(c.getTime());
  }

  private static boolean isEmpty(String str) {
    return str == null || str.trim().length() == 0;
  }

  /**
   * 检查预计发货时间是否失效
   * 
   * @param deliveryTime
   * @param expireDays
   * @return
   */
  public static Boolean isExpireTime(String deliveryTime, int expireDays) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date dt1 = sdf.parse(deliveryTime);
      Calendar cal1 = Calendar.getInstance();
      cal1.setTime(dt1);
      Date dt2 = new Date();
      Calendar cal2 = Calendar.getInstance();
      cal2.setTime(sdf.parse(sdf.format(dt2)));
      cal1.add(Calendar.DATE, expireDays);
      if (cal1.compareTo(cal2) < 0) {
        return true;
      }

    } catch (Exception e) {
      return true;
    }
    return false;
  }
  
  /** 
   * 校验时间格式（仅格式） 
   */  
  public static boolean checkHHMM(String time) {  
      SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");  
       try {  
           @SuppressWarnings("unused")  
          Date t = dateFormat.parse(time);  
       }  
       catch (Exception ex) {  
           return false;  
       }  
      return true;  
  }  
    
  /** 
   * 校验时间格式HH:MM（精确） 
   */  
  public static boolean checkTime(String time) {  
      int hour = 24;
      int second = 60;
      if (checkHHMM(time)) {  
          String[] temp = time.split(":");  
          boolean condition = (temp[0].length() == 2 || temp[0].length() == 1) && temp[1].length() == 2;
          if (condition) {  
              int h,m;  
              try {  
                  h = Integer.parseInt(temp[0]);  
                  m = Integer.parseInt(temp[1]);  
              } catch (NumberFormatException e) {  
                  return false;  
              }     
              if (h >= 0 && h <= hour && m <= second && m >= 0) {  
                  return true;  
              }  
          }  
      }  
      return false;  
  }  
  
  public static String getDateShow(Date date){
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(Calendar.YEAR) +"年" + (c.get(Calendar.MONTH)+1) + "月"+c.get(Calendar.DAY_OF_MONTH)+"日";
  }

  public static String getLastDayOfMonth(int year,int month) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR,year);
    cal.set(Calendar.MONTH, month-1);
    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    cal.set(Calendar.DAY_OF_MONTH, lastDay);
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SHORT);
    return sdf.format(cal.getTime());

  }
  
  
  
  /**
   * 增加日期的天数。失败返回null。
   * 
   * @param date 日期
   * @param dayAmount 增加数量。可为负数
   * @return 增加天数后的日期
   */
  public static Date addDay(Date date, int dayAmount) {
    return addInteger(date, Calendar.DATE, dayAmount);
  }
  
  
  /**
   * 增加日期中某类型的某数值。如增加日期
   * 
   * @param date 日期
   * @param dateType 类型
   * @param amount 数值
   * @return 计算后日期
   */
  private static Date addInteger(Date date, int dateType, int amount) {
    if (null == date) {
      date = new Date();
    }
    Calendar calendar = getCalendar(date);
    calendar.add(dateType, amount);
    return calendar.getTime();
  }
  
  
  /**
   * Date类型转换到Calendar类型
   * 
   * @param d
   * @return
   */
  public static Calendar getCalendar(Date d) {
    Calendar c = Calendar.getInstance();
    c.setTime(d);
    return c;
  }

  /**
   * 将日期格式化成前一天最大时间，精确到秒
   * @return
   */
  public static Date formatMaxSecond(Date srcDate) {
    if (srcDate == null) {
      return null;
    }
    return null;
  }
 
  
}
