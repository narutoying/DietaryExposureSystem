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
 * �˽�����ϵͳĬ�ϵ�һ��ҳ�棬��ϵͳ�Ż��� �տ�ʼϵͳ���ܱȽϼ򵥣�
 * ��ֻ�ṩ����ѡ�� 1. ��ʼ���� 2. ���ݵ���
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
		// ���õ�ǰ����������ĸ�layout
		setContentViewByQuestionaireTemplateId();
		Button startSurveyBtn = (Button) findViewById(R.id.startSurveyBtn);
		startSurveyBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// �����֤��ͨ����ʼ����
				SecurityUtil.showAuthorityDlg(
						DietaryExposureSystemActivity.this,
						new CallBackMethod() {

							@Override
							public void doSomething() {
								AppContext appContext = (AppContext) getApplicationContext();
								SurveyService.initSurvey(
										DietaryExposureSystemActivity.this,
										appContext);
								// ͨ��intentʵ�ֽ�����ת
								if (appContext.getCurrentQuestionaireStep() == QuestionaireStep.TERMINATE) {
									new AlertDialog.Builder(
											DietaryExposureSystemActivity.this)
											.setTitle("ϵͳ��ʾ")
											.setMessage("�����ʾ���Ŀ����Ϊ��")
											.setPositiveButton(
													"ȷ��",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialoginterface,
																int i) {
															//��ť�¼� 
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

		// Ϊ���ݵ�����ť�����Ӧ����
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
									message = "�����ʾ����ɹ�";
								} else {
									message = "�����ʾ���ʧ��";
								}
								new AlertDialog.Builder(
										DietaryExposureSystemActivity.this)
										.setTitle("ϵͳ��ʾ")
										.setMessage(message)
										.setPositiveButton(
												"ȷ��",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialoginterface,
															int i) {
														//��ť�¼� 
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
	 * ͨ���ʾ�ģ��id������ͼ
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
	 * ת�Ƶ�������Ϣ¼��ģ�飬���ݲ�ͬ���ʾ�ģ����ʾ��ͬ������
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
	 * ��ʼ������
	 * 1. λ����Ϣdb
	 * 2. ���db
	 */
	private void initDatabase() {
		copyLocationDB();
		copyQuestionaireDB();
	}

	private void copyQuestionaireDB() {
		copyFileToDatabases(DBConfigs.QUESTIONAIRE_DB_NAME, R.raw.des_db);
	}

	// ��λ�����ݿ⿽������app��databasesĿ¼��
	private void copyLocationDB() {
		copyFileToDatabases(DBConfigs.LOCATION_DB_NAME,
				R.raw.china_province_city_zone);
	}

	/**
	 * ����ָ���ļ���Դ��data/data/��ǰ��������/database/targetFileName
	 * ��֤�ļ�ֻ�´��һ��
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

	//�����˵�������˵�������XML�ļ��ж��� ������ؽ�����OK
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		//�������ǵĲ˵��ļ�
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	//�˵�ѡ���¼�
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
