package com.nashratbaloot.app.nashratbalot.utils;

import java.util.Calendar;

import com.nashratbaloot.app.nashratbalot.common.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class DateUtils {

	private static final String PREF_CURRENT_MONTH = "current_month";
	private static final String PREF_FIRST_TIME = "first_time";
	private static final String PREF_IS_7_DAYS_PASSED = "is_7_days_passed";
	private static final String PREF_IS_FEEDBACK_CLICKED = "is_feedback_clicked";
	
	public static String getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		String month = calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.YEAR);
		return month;
	}
		
	public static void saveCurrentMonthToPref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(PREF_CURRENT_MONTH, getCurrentMonth());
		editor.commit();
	}
	
	public static void saveFirstTimeToPref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putLong(PREF_FIRST_TIME, System.currentTimeMillis() / 1000L);
		editor.commit();
	}
	
	public static void saveIs7DaysPassed(Context context, boolean is7DaysPassed) {
		SharedPreferences prefs = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(PREF_IS_7_DAYS_PASSED, is7DaysPassed);
		editor.commit();
	}
	
	public static void saveIsFeedBackClicked(Context context, boolean isFeedbackClicked) {
		SharedPreferences prefs = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(PREF_IS_FEEDBACK_CLICKED, isFeedbackClicked);
		editor.commit();
	}
	
	public static String getCurrentMonth(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
		String month = prefs.getString(PREF_CURRENT_MONTH, "");
		return month;
	}
	
	public static long getFirstTime(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
		long first = prefs.getLong(PREF_FIRST_TIME, 0);
		return first;
	}
	
	public static boolean is7DaysPassed(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
		boolean is7DaysPassed = prefs.getBoolean(PREF_IS_7_DAYS_PASSED, false);
		return is7DaysPassed;
	}
	
	public static boolean isFeedbackClicked(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
		boolean isFeedbackClicked = prefs.getBoolean(PREF_IS_FEEDBACK_CLICKED, false);
		return isFeedbackClicked;
	}

}
