/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.view.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.seu.yang11.common.util.CallBackMethod;

/**
 * 自定义视图层帮助类
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: MyViewHelper.java, v 0.1 2012-7-28 下午1:38:14 narutoying Exp $
 */
public class MyViewHelper {
	/**
	 * 显示含有标题和消息的普通对话框，只起到提示作用
	 * 
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void showSimpleDlg(Context context, String title,
			String message, final CallBackMethod callbackPositive,
			final CallBackMethod callbackNegative) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						if (callbackPositive != null) {
							callbackPositive.doSomething();
						}
					}
				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(callbackNegative != null) {
							callbackNegative.doSomething();
						}
					}
				}).show();
	}

	public static String getDefaultDlgTitle() {
		return "系统提示";
	}
}
