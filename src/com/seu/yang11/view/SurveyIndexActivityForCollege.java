/**
 * 
 */
package com.seu.yang11.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.DialerKeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.seu.yang11.R;
import com.seu.yang11.common.util.StringUtil;
import com.seu.yang11.common.util.SystemUtil;
import com.seu.yang11.core.AppContext;
import com.seu.yang11.core.domain.model.QuestionaireBasicInfo;
import com.seu.yang11.core.service.SurveyService;

/**
 * 当用户在首页点击“开始调查”按钮后跳转到此页面 
 * 本页面填写一些基本信息，区别于之前为省疾控做的界面，本界面服务于7月份学校问卷模板
 * 
 * 
 * @author narutoying09@gmail.com
 * 
 */
public class SurveyIndexActivityForCollege extends Activity {

	private static final String TAG = "SurveyIndexActivityForCollege";

	/**
	 * 下面是本界面的控件
	 */
	private Button nextBtn;
	/** 问卷编号 */
	private EditText numberText;
	//	private EditText surveyPersonIdText;
	//	private EditText surveyPersonNameText;
	//	private EditText surveyPersonCellphoneText;
	//	private EditText surveyLocationText;
	/** 市 */
	//	private EditText cityText;
	/** 县/区 */
	private EditText zoneText;
	/** 乡/镇 */
	private Spinner townText;
	/** 村 */
	private Spinner villageText;
	/** 单位 */
	//	private EditText organizationText;

	/** 本界面上的所有输入控件 */
	private List<EditText> allCheckEditTexts = new ArrayList<EditText>();

	private static Map<String, String[]> townVillageMap = new HashMap<String, String[]>();
	private String[] towns = new String[] { "扬子山潘", "大厂", "葛塘", "其他1", "其他2",
			"其他3" };

	static {
		String[] v1 = new String[] { "20村", "21村", "其他1" };
		String[] v2 = new String[] { "晓山", "其他2" };
		String[] v3 = new String[] { "中山", "其他3" };
		String[] v4 = new String[] { "其他1" };
		String[] v5 = new String[] { "其他2" };
		String[] v6 = new String[] { "其他3" };
		townVillageMap.put("扬子山潘", v1);
		townVillageMap.put("大厂", v2);
		townVillageMap.put("葛塘", v3);
		townVillageMap.put("其他1", v4);
		townVillageMap.put("其他2", v5);
		townVillageMap.put("其他3", v6);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SystemUtil.addActivity(this);
		setContentView(R.layout.survey_index_2);
		initComponents();
		nextBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!checkHasBlank()) {
					AppContext appContext = (AppContext) getApplicationContext();
					SurveyService.recordBasicInfos(
							SurveyIndexActivityForCollege.this, appContext,
							buildQuestionaireInfos());
					// 跳转到答题界面
					Intent intent = new Intent(
							SurveyIndexActivityForCollege.this,
							SurveyQuestionActivity.class);
					startActivity(intent);
				} else {
					new AlertDialog.Builder(SurveyIndexActivityForCollege.this)
							.setTitle("系统提示")
							.setMessage("基本信息均不能为空")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialoginterface,
												int i) {
											//按钮事件 
										}
									}).show();
				}
			}

			/**
			 * 组装用户在界面上输入的如编号、地址等信息
			 * 
			 * @return
			 */
			private QuestionaireBasicInfo buildQuestionaireInfos() {
				QuestionaireBasicInfo result = new QuestionaireBasicInfo();
				result.setNumber(numberText.getText().toString());
				Object selectedTown = townText.getSelectedItem();
				result.setStreet((selectedTown != null) ? selectedTown
						.toString() : null);
				Object selectedVillage = villageText.getSelectedItem();
				result.setVillageCommittee((selectedVillage != null) ? selectedVillage
						.toString() : null);
				return result;
			}

		});
	}

	/**
	 * 检查界面上的输入框是否全部不为空，只要有一个为空，则返回true表示有空
	 * 
	 * @return
	 */
	protected boolean checkHasBlank() {
		for (EditText text : this.allCheckEditTexts) {
			if (StringUtil.isBlank(text.getText().toString())) {
				return true;
			}
		}
		return false;
	}

	private void initComponents() {
		nextBtn = (Button) findViewById(R.id.survey_index_next);
		numberText = (EditText) findViewById(R.id.survey_index_number);
		numberText.setKeyListener(new DialerKeyListener());
		/*surveyPersonNameText = (EditText) findViewById(R.id.surveyed_person_name);
		surveyPersonCellphoneText = (EditText) findViewById(R.id.surveyed_person_cellphone);
		surveyPersonCellphoneText.setKeyListener(new DialerKeyListener());
		surveyLocationText = (EditText) findViewById(R.id.survey_location);*/
		//		zoneText = (EditText) findViewById(R.id.survey_index_zone);
		townText = (Spinner) findViewById(R.id.survey_index_town);
		villageText = (Spinner) findViewById(R.id.survey_index_villageCommittee);
		addCheckEditTexts();
		initSpinnerData();
	}

	private void initSpinnerData() {
		ArrayAdapter<String> townAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, towns);
		townAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		townText.setAdapter(townAdapter);
		townText.setOnItemSelectedListener(buildTownListener());
		String[] defaultVillages = townVillageMap.get(towns[0]);
		ArrayAdapter<String> defaultVillageAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, defaultVillages);
		defaultVillageAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		villageText.setAdapter(defaultVillageAdapter);
	}

	private OnItemSelectedListener buildTownListener() {
		return new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String selectedTown = towns[arg2];
				String[] strings = townVillageMap.get(selectedTown);
				android.widget.ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_spinner_item, strings);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				villageText.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		};
	}

	/**
	 * 将界面上的所有输入控件
	 */
	private void addCheckEditTexts() {
		allCheckEditTexts.add(numberText);
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
