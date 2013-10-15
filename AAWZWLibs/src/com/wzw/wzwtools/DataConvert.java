package com.wzw.wzwtools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataConvert {

	/** default local : local.china
	 * string format MM-dd HH:mm
	 */
	public static  String parseLong2Time(Long pLong,String format){
		long timelong =pLong;
		SimpleDateFormat sdf= new SimpleDateFormat(format,Locale.CHINA);
		java.util.Date dt = new Date(timelong * 1000);  
		String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
		return sDateTime;
	}
	/**
	 * default local : local.china
	 * @param src
	 * @param format ex:MM-dd HH:mm
	 * @return
	 * @throws ParseException
	 */
	public  static String getLongFromString(String src,String format) throws ParseException{
		String re_time = null; 
		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA); 
		Date d; 
		d =  sdf.parse(src); 
		long l = d.getTime(); 
		String str = String.valueOf(l); 
		re_time = str.substring(0, 10); 
		return re_time; 
	}
}
