/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.view;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.seu.yang11.R;
import com.seu.yang11.common.util.SystemUtil;
import com.seu.yang11.core.AppContext;
import com.seu.yang11.core.domain.model.QuestionaireCompleteSign;
import com.seu.yang11.core.service.SurveyService;

/**
 * 问卷调查完成确认界面
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: SurveyCompleteActivity.java, v 0.1 2012-4-22 下午10:11:15 narutoying Exp $
 */
public class SurveyCompleteActivity extends Activity {

	private Button completeBtn;
	private Spinner surveyPersonInput;
	private Spinner checkPerson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SystemUtil.addActivity(this);
		setContentView(R.layout.survey_complete);
		initComponents();
		final AppContext appContext = (AppContext) getApplicationContext();
		completeBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				SurveyService.completeRecording(SurveyCompleteActivity.this,
						appContext, buildCompleteSign());
				SurveyService.postSurvey(appContext);
				Intent intent = new Intent(SurveyCompleteActivity.this,
						DietaryExposureSystemActivity.class);
				startActivity(intent);
			}
		});
	}

	private QuestionaireCompleteSign buildCompleteSign() {
		QuestionaireCompleteSign result = new QuestionaireCompleteSign();
		result.setSurveyPerson(surveyPersonInput.getSelectedItem().toString());
		result.setCheckPerson(checkPerson.getSelectedItem().toString());
		AppContext appContext = (AppContext) getApplicationContext();
		result.setSurveyStartTime(appContext.getSurveyStartTime());
		result.setSurveyEndTime(new Date());
		return result;
	}

	private void initComponents() {
		completeBtn = (Button) findViewById(R.id.survey_complete_completeBtn);
		surveyPersonInput = (Spinner) findViewById(R.id.survey_complete_person);
		checkPerson = (Spinner) findViewById(R.id.survey_check_person);
		initSpinnerData();
	}

	private void initSpinnerData() {
		String[] surveyPersonArr = new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, surveyPersonArr);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		surveyPersonInput.setAdapter(adapter);
		String[] checkPersonArr = new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15" };
		ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, checkPersonArr);
		cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		checkPerson.setAdapter(cAdapter);
	}

	//创建菜单，这个菜单我们在XML文件中定义 这里加载进来就OK
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		//加载我们的菜单文件
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	//菜单选项事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_quit) {
			SystemUtil.closeSystem(this);
		} else if (item.getItemId() == R.id.menu_index) {
			SystemUtil.goToMainActivity(this);
		}
		return true;
	}
}
