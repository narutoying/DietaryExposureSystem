/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: SecurityUtil.java, v 0.1 2012-5-13 下午1:21:52 narutoying Exp $
 */
public class SecurityUtil {

	private static boolean authority = false;

	public static void showAuthorityDlg(final Context context,
			final CallBackMethod callBack1, final CallBackMethod callBack2) {
//		final EditText inputPassword = new EditText(context);
//		inputPassword.setInputType(InputType.TYPE_CLASS_TEXT
//				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setTitle("身份验证").setIcon(android.R.drawable.ic_dialog_info)
//				.setView(inputPassword).setNegativeButton("Cancel", null);
//		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//
//			public void onClick(DialogInterface dialog, int which) {
//				String password = inputPassword.getText().toString();
//				if (StringUtil.equals("desmm2012", password)) {
//					SecurityUtil.authority = true;
//					callBack1.doSomething();
//				} else {
//					SecurityUtil.authority = false;
//					callBack2.doSomething();
//				}
//			}
//		});
//		builder.show();
		callBack1.doSomething();
	}

	public static boolean hasAuthority() {
		return authority;
	}

	public static void showNoAuthorityDlg(Context context) {
		new AlertDialog.Builder(context).setTitle("系统提示").setMessage("密码不正确")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						//按钮事件 
					}
				}).show();
	}
}
