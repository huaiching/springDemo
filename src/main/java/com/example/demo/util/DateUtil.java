package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtil {
	private final static String FORMAT_YYYY_MM_DD = "yyyy/MM/dd";
	private final static String FORMAT_HH_MM_SS = "HH:mm:ss";
	
	/**
	 * 民國年 的 天數增減
	 * @param TwDate 民國年
	 * @param addDate 增減天數
	 * @return 計算後的民國年
	 */
	public static String addDate(String TwDate, Integer addDate) {
		if (TwDate == null) return "";
		
		String AdDate = twToAdDate(TwDate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD);
		LocalDate localDate = LocalDate.parse(AdDate, formatter).plusDays(addDate);
		
		String calAdDate = localDate.format(DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD));
		
		return adToTwDate(calAdDate);
	}
	
	/**
	 * 民國年 的 月份增減
	 * @param TwDate 民國年
	 * @param addMonth 增減月份
	 * @return 計算後的民國年
	 */
	public static String addMonth(String TwDate, Integer addMonth) {
		if (TwDate == null) return "";
		
		String AdDate = twToAdDate(TwDate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD);
		LocalDate localDate = LocalDate.parse(AdDate, formatter).plusMonths(addMonth);
		
		String calAdDate = localDate.format(DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD));
		
		return adToTwDate(calAdDate);
	}

	/**
	 * 民國年 的 年份增減
	 * @param TwDate 民國年
	 * @param addYear 增減年份
	 * @return 計算後的民國年
	 */
	public static String addYear(String TwDate, Integer addYear) {
		if (TwDate == null) return "";
		
		String AdDate = twToAdDate(TwDate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD);
		LocalDate localDate = LocalDate.parse(AdDate, formatter).plusYears(addYear);
		
		String calAdDate = localDate.format(DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD));
		
		return adToTwDate(calAdDate);
	}
	
	/**
	 * 民國年 轉 西元年
	 * @param TwDate 民國年
	 * @return 西元年
	 */
	public static String twToAdDate(String TwDate) {
		if (TwDate == null) return "";
		
		String AdDate = (Integer.valueOf(TwDate.substring(0,3)) + 1911) + TwDate.substring(3);
		
		return AdDate;
	}

	/**
	 * 西元年 轉 民國年
	 * @param AdDate 西元年
	 * @return 民國年
	 */
	public static String adToTwDate(String AdDate) {
		if (AdDate == null) return "";
		
		String TwDate = (Integer.valueOf(AdDate.substring(0,4)) - 1911) + AdDate.substring(4);
		
		return TwDate;
	}
	
	/**
	 * 民國年 計算天數差
	 * @param beginDate 起日(民國年)
	 * @param endDate 迄日(民國年)
	 * @return 天數差
	 */
	public static long diffDay(String beginDate, String endDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD);

		LocalDate beginLocalDate = LocalDate.parse(twToAdDate(beginDate), formatter);
		LocalDate endLocalDate = LocalDate.parse(twToAdDate(endDate), formatter);
		
		return ChronoUnit.DAYS.between(beginLocalDate, endLocalDate);
	}
	
	/**
	 * 民國年 計算月份差
	 * @param beginDate 起日(民國年)
	 * @param endDate 迄日(民國年)
	 * @return 月份差
	 */
	public static long diffMonth(String beginDate, String endDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD);

		LocalDate beginLocalDate = LocalDate.parse(twToAdDate(beginDate), formatter);
		LocalDate endLocalDate = LocalDate.parse(twToAdDate(endDate), formatter);
		
		return ChronoUnit.MONTHS.between(beginLocalDate, endLocalDate);
	}
	
	/**
	 * 民國年 計算年份差
	 * @param beginDate 起日(民國年)
	 * @param endDate 迄日(民國年)
	 * @return 年份差
	 */
	public static long diffYear(String beginDate, String endDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD);

		LocalDate beginLocalDate = LocalDate.parse(twToAdDate(beginDate), formatter);
		LocalDate endLocalDate = LocalDate.parse(twToAdDate(endDate), formatter);
		
		return ChronoUnit.YEARS.between(beginLocalDate, endLocalDate);
	}

	/**
	 * 民國年 日期比較：起日 > 迄日
	 * @param beginDate 起日
	 * @param endDate 迄日
	 * @return
	 */
	public static Boolean compareGT(String beginDate, String endDate) {
		if (beginDate.compareTo(endDate) > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * 民國年 日期比較：起日 >= 迄日
	 * @param beginDate 起日
	 * @param endDate 迄日
	 * @return TRUE / FALSE
	 */
	public static Boolean compareGE(String beginDate, String endDate) {
		if (beginDate.compareTo(endDate) >= 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * 民國年 日期比較：起日 < 迄日
	 * @param beginDate 起日
	 * @param endDate 迄日
	 * @return TRUE / FALSE
	 */
	public static Boolean compareLT(String beginDate, String endDate) {
		if (beginDate.compareTo(endDate) < 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * 民國年 日期比較：起日 <= 迄日
	 * @param beginDate 起日
	 * @param endDate 迄日
	 * @return TRUE / FALSE
	 */
	public static Boolean compareLG(String beginDate, String endDate) {
		if (beginDate.compareTo(endDate) <= 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	/**
	 * 取得系統日 (民國年)
	 * @return 系統日 (民國年)
	 */
	public static String getToday() {
		String adDate = new SimpleDateFormat(FORMAT_YYYY_MM_DD).format(new Date());
		
		return adToTwDate(adDate);
	}

	/**
	 * 取得系統時間
	 * @return 系統時間
	 */
	public static String getTime() {
		
		return new SimpleDateFormat(FORMAT_HH_MM_SS).format(new Date());
	}
}
