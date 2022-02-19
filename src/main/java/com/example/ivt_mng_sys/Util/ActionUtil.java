package com.example.ivt_mng_sys.Util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
public class ActionUtil {
	//	public static Logger logger = LoggerFactory.getLogger("JsonUtil java util");
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
	public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMddHHmmss");


	//将Object转成Json在传
	public static void sendJson(HttpServletResponse response, Object object) {
//		logger.info("sendJson传值");
		String json = JSON.toJSONString(object);
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json);
			System.out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 前端返回信息
	public static void sendMessege(HttpServletResponse response, String messege) {
		System.out.println("前端返回信息="+messege);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out= response.getWriter();
			out.print("<script>alert('" + messege + "');history.go(-1);</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 传输Json数组
	public static void sendJson(HttpServletResponse response, String json) {
//		logger.info("sendJson传值");
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// Date转换为字符串
	public static String getDateStrForString(Date date) {
		if(date!=null) {
			return sdf4.format(date);
		}
		else {
			return null;
		}
	}

	// Date转换为字符串
	public static String getDateStrForStringwithNoLiine(Date date) {
		if(date!=null) {
			return sdf5.format(date);
		}
		else {
			return null;
		}
	}
	// Date转换为字符串
	public static String getDateStr(Date date) {
		if(date!=null) {
			return sdf2.format(date);
		}
		else {
			return null;
		}
	}

	// Date转换为字符串
	public static String getDateStrForDay(Date date) {
		if(date!=null) {
			return sdf3.format(date);
		}
		else {
			return null;
		}
	}

	// Date转换为字符串
	public static String getDateStrForDayChinese(Date date) {
		if(date!=null) {
			return sdf.format(date);
		}
		else {
			return null;
		}
	}

	/**
	 * 比较时间
	 * @Date:2021/8/10
	 * name:zhangtao
	 * */
	public static String getTime(Date currentTime,Date firstTime){
		long diff = currentTime.getTime() - firstTime.getTime();//这样得到的差值是微秒级别
		Calendar currentTimes =dataToCalendar(currentTime);//当前系统时间转Calendar类型
		Calendar firstTimes =dataToCalendar(firstTime);//查询的数据时间转Calendar类型
		int year = currentTimes.get(Calendar.YEAR) - firstTimes.get(Calendar.YEAR);//获取年
		int month = currentTimes.get(Calendar.MONTH) - firstTimes.get(Calendar.MONTH);
		int day = currentTimes.get(Calendar.DAY_OF_MONTH) - firstTimes.get(Calendar.DAY_OF_MONTH);
		if (day < 0){
			month-=1;
			currentTimes.add(Calendar.MONTH, -1);
			day = day + currentTimes.getActualMaximum(Calendar.DAY_OF_MONTH);//获取日
		}
		if (month<0){
			month = (month + 12) % 12;//获取月
			year--;
		}
		long days = diff / (1000 * 60 * 60 * 24);
		long hours=(diff-days*(1000*60*60*24))/(1000* 60*60);//获取时
		long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);//获取分钟
		long s=(diff/1000-days*24*60*60-hours*60*60-minutes*60);//获取秒
		//String CountTime=""+year+"年"+month+"月"+day+"天"+hours+"小时"+minutes+"分"+s+"秒";
		String CountTime=Integer.toString(day);
		return CountTime;
	}

	// Date类型转Calendar类型
	public static Calendar dataToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	//获取随机字符串(1-10)
	public static String getRandomString(int length) {
		String randomNumber =  ""+(int)((Math.random()*9+1)* Math.pow(10,length-1));
		return randomNumber;
	}

	//获取随机字符串(1-10)
	public static String getRandom4String(int length) {
		String randomNumber =  ""+(int)((Math.random()*3+1)* Math.pow(4,length-1));
		return randomNumber;
	}

	/**
	 * 安装设备状态的初始化工具类
	 * */
	public static String getInitializeStatus(int[] numbers){
		/*
		 * 初始化设备状态
		 * */
		System.out.println("一维数组的数据="+numbers.length);
		//需进行length-1次冒泡
		for(int i=0;i<numbers.length-1;i++) {
			//System.out.println(numbers[i]);
			for(int j=0;j<numbers.length-1-i;j++) {
				System.out.println("j="+numbers[j]);
				System.out.println("j+1="+numbers[j+1]);
				if(numbers[j]>numbers[j+1]) {
					int temp=numbers[j];
					numbers[j]=numbers[j+1];
					numbers[j+1]=temp;
				}
			}
		}
		System.out.println("从小到大排序后的结果是:");
		for(int i=0;i<numbers.length;i++){
			System.out.print(numbers[i]+" ");

		}
		int initializeStatus = numbers[0];//初始化状态
		return Integer.toString(initializeStatus);
	}


}
