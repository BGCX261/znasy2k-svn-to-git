package znasy2k.gmail.com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static boolean CompleTime(String time1, String time2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		try { 
            Date dt1 = df.parse(time1); 
            Date dt2 = df.parse(time2); 
            if (dt1.getTime() > dt2.getTime()) { 
                return false; 
            } 
        } catch (Exception exception) { 
            exception.printStackTrace(); 
        } 
		return true;
	}
	
	/**  
	 * 得到本月的第一天  
	 * @return  
	 */  
	public static String getMonthFirstDay() {   
		Calendar cal = Calendar.getInstance();
        // 当前月＋1，即下个月
        cal.add(cal.MONTH, 1);
        // 将下个月1号作为日期初始值
        cal.set(cal.DATE, 1);
        // 下个月1号减去一天，即得到当前月最后一天
        cal.add(cal.DATE, -1);
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(c.DATE, 1);

	    return df.format(c.getTime());   
	}   
	  
	/**  
	 * 得到本月的最后一天  
	 *   
	 * @return  
	 */  
	public static String getMonthLastDay() {   
		Calendar cal = Calendar.getInstance();
        // 当前月＋1，即下个月
        cal.add(cal.MONTH, 1);
        // 将下个月1号作为日期初始值
        cal.set(cal.DATE, 1);
        // 下个月1号减去一天，即得到当前月最后一天
        cal.add(cal.DATE, -1);
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String day_end = df.format(cal.getTime());
        Calendar c = Calendar.getInstance();
        c.set(c.DATE, 1);
	    return day_end;   
	}   
}
