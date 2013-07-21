/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seu.yang11.R;
import com.seu.yang11.common.util.SystemUtil;
import com.seu.yang11.core.service.DataAnalyseService;

/**
 * 本界面展示一些统计类的数据，如：
 * 1. 男女比例统计
 * 2. 年龄段统计
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: DataStatisticsActivity.java, v 0.1 2012-5-13 下午3:09:29 narutoying Exp $
 */
public class DataStatisticsActivity extends Activity {

	LinearLayout ageLayout;
	LinearLayout sexLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_statistic);
		SystemUtil.addActivity(this);
		initComponents();
		initModelAndView(DataStatisticsActivity.this);
	}

	private void initComponents() {
		ageLayout = (LinearLayout) findViewById(R.id.data_statistic_age_layout);
		sexLayout = (LinearLayout) findViewById(R.id.data_statistic_sex_layout);
	}

	private void initModelAndView(Context context) {
		DataAnalyseService dataAnalyseService = new DataAnalyseService(context);
		List<String> sexPercentage = dataAnalyseService.getSexPercentage();
		List<String> ageLevels = dataAnalyseService.getAgeLevels();
		for (String sexInfo : sexPercentage) {
			TextView tv = new TextView(context);
			tv.setText(sexInfo);
			tv.setTextColor(android.graphics.Color.BLACK);
			tv.setTextSize(20);
			sexLayout.addView(tv);
		}
		for (String ageInfo : ageLevels) {
			TextView tv = new TextView(context);
			tv.setText(ageInfo);
			tv.setTextColor(android.graphics.Color.BLACK);
			tv.setTextSize(20);
			ageLayout.addView(tv);
		}
	}
}
