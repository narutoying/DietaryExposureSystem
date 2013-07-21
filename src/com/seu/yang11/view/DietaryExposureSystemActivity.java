package com.seu.yang11.view;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.seu.yang11.R;
import com.seu.yang11.common.dal.DBConfigs;
import com.seu.yang11.common.util.CallBackMethod;
import com.seu.yang11.common.util.FileUtil;
import com.seu.yang11.common.util.SecurityUtil;
import com.seu.yang11.common.util.SystemUtil;
import com.seu.yang11.core.AppContext;
import com.seu.yang11.core.constants.Constants;
import com.seu.yang11.core.constants.QuestionaireTemplateEnum;
import com.seu.yang11.core.domain.model.enums.QuestionaireStep;
import com.seu.yang11.core.service.ExportService;
import com.seu.yang11.core.service.SurveyService;

/**
 * 此界面是系统默认第一个页面，即系统门户。 刚开始系统功能比较简单，
 * 故只提供两个选项 1. 开始调查 2. 数据导出
 * 
 * 
 * @author narutoying09@gmail.com
 * 
 */
public class DietaryExposureSystemActivity extends Activity {

	private static final String TAG = "DietaryExposureSystemActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SystemUtil.addActivity(this);
		initDatabase();
		// 设置当前这个界面用哪个layout
		setContentViewByQuestionaireTemplateId();
		Button startSurveyBtn = (Button) findViewById(R.id.startSurveyBtn);
		startSurveyBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 身份认证，通过则开始调查
				SecurityUtil.showAuthorityDlg(
						DietaryExposureSystemActivity.this,
						new CallBackMethod() {

							@Override
							public void doSomething() {
								AppContext appContext = (AppContext) getApplicationContext();
								SurveyService.initSurvey(
										DietaryExposureSystemActivity.this,
										appContext);
								// 通过intent实现界面跳转
								if (appContext.getCurrentQuestionaireStep() == QuestionaireStep.TERMINATE) {
									new AlertDialog.Builder(
											DietaryExposureSystemActivity.this)
											.setTitle("系统提示")
											.setMessage("调查问卷题目不能为空")
											.setPositiveButton(
													"确定",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialoginterface,
																int i) {
															//按钮事件 
														}
													}).show();
								} else {
									goToBasicInfoActivity();
								}

							}

						}, new CallBackMethod() {

							@Override
							public void doSomething() {
								SecurityUtil
										.showNoAuthorityDlg(DietaryExposureSystemActivity.this);
							}
						});
			}

		});

		// 为数据导出按钮添加响应函数
		Button dataExportBtn = (Button) findViewById(R.id.dataExportBtn);
		dataExportBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				SecurityUtil.showAuthorityDlg(
						DietaryExposureSystemActivity.this,
						new CallBackMethod() {

							@Override
							public void doSomething() {
								ExportService exportService = new ExportService(
										DietaryExposureSystemActivity.this);
								boolean result = false;
								if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q1) {
									result = exportService
											.exportQuestionaireDatasToExcel();
								} else {
									result = exportService
											.exportQuestionaireDatasToExcelForCollege();
								}
								String message = "";
								if (result) {
									message = "调查问卷导出成功";
								} else {
									message = "调查问卷导出失败";
								}
								new AlertDialog.Builder(
										DietaryExposureSystemActivity.this)
										.setTitle("系统提示")
										.setMessage(message)
										.setPositiveButton(
												"确定",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialoginterface,
															int i) {
														//按钮事件 
													}
												}).show();
							}
						}, new CallBackMethod() {

							@Override
							public void doSomething() {
								SecurityUtil
										.showNoAuthorityDlg(DietaryExposureSystemActivity.this);
							}
						});

			}

		});

		/*Button dataStatisticsBtn = (Button) findViewById(R.id.dataStatisticsBtn);
		dataStatisticsBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DietaryExposureSystemActivity.this,
						DataStatisticsActivity.class);
				startActivity(intent);
			}

		});*/
	}

	/**
	 * 通过问卷模板id设置视图
	 */
	private void setContentViewByQuestionaireTemplateId() {
		if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q1) {
			setContentView(R.layout.main);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q2) {
			setContentView(R.layout.main2);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q3) {
			setContentView(R.layout.main3);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q4) {
			setContentView(R.layout.main4);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q5) {
			setContentView(R.layout.main5);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q6) {
			setContentView(R.layout.main6);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q7) {
			setContentView(R.layout.main7);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q8) {
			setContentView(R.layout.main8);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q9) {
			setContentView(R.layout.main9);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q10) {
			setContentView(R.layout.main10);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q11) {
			setContentView(R.layout.main11);
		} else if (Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID == QuestionaireTemplateEnum.Q12) {
			setContentView(R.layout.main11);
		}
	}

	/**
	 * 转移到基本信息录入模块，根据不同的问卷模板显示不同的内容
	 */
	private void goToBasicInfoActivity() {
		int questionaireTemplateId = Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID;
		if (questionaireTemplateId == QuestionaireTemplateEnum.Q1
				|| questionaireTemplateId == QuestionaireTemplateEnum.Q10) {
			Intent intent = new Intent(DietaryExposureSystemActivity.this,
					SurveyIndexActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(DietaryExposureSystemActivity.this,
					SurveyIndexActivityForCollege.class);
			startActivity(intent);
		}
	}

	/**
	 * 初始化数据
	 * 1. 位置信息db
	 * 2. 题库db
	 */
	private void initDatabase() {
		copyLocationDB();
		copyQuestionaireDB();
	}

	private void copyQuestionaireDB() {
		copyFileToDatabases(DBConfigs.QUESTIONAIRE_DB_NAME, R.raw.des_db);
	}

	// 将位置数据库拷贝到本app的databases目录下
	private void copyLocationDB() {
		copyFileToDatabases(DBConfigs.LOCATION_DB_NAME,
				R.raw.china_province_city_zone);
	}

	/**
	 * 加载指定文件资源到data/data/当前包的名称/database/targetFileName
	 * 保证文件只会拷贝一次
	 * 
	 * @param targetFileName
	 * @param rawId
	 */
	private void copyFileToDatabases(String targetFileName, int rawId) {
		File dir = new File("data/data/" + getPackageName() + "/databases");
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		File file = new File(dir, targetFileName);
		if (!file.exists()) {
			FileUtil.loadDbFile(rawId, file, getResources(), getPackageName());
			Log.d(TAG, "DataBase [" + targetFileName + "] Load Successfully");
		}
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			SystemUtil.closeSystem(this);
			return false;
		}
		return false;
	}
}
