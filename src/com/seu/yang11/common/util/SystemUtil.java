/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.seu.yang11.view.DietaryExposureSystemActivity;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: SystemUtil.java, v 0.1 2012-5-13 下午12:53:43 narutoying Exp $
 */
public class SystemUtil {

	public static List<Activity> activityList = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public static void closeSystem(final Context context) {

		new AlertDialog.Builder(context)
				.setTitle("系统提示")
				.setMessage("退出系统")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						Log.d("SystemUtil", "----- exitSystem -----");
						// 关闭所有Activity
						for (Activity activity : SystemUtil.activityList) {
							if (activity != null) {
								activity.finish();
							}
						}
						ActivityManager activityManager = (ActivityManager) context
								.getSystemService(Context.ACTIVITY_SERVICE);
						activityManager.restartPackage(context.getPackageName());
						System.exit(0);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {

					}
				}).show();
	}

	public static void goToMainActivity(Context context) {
		Intent intent = new Intent(context, DietaryExposureSystemActivity.class);
		context.startActivity(intent);
	}
}
