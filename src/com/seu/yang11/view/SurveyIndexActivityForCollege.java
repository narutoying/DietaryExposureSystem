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
 * ���û�����ҳ�������ʼ���顱��ť����ת����ҳ�� 
 * ��ҳ����дһЩ������Ϣ��������֮ǰΪʡ�������Ľ��棬�����������7�·�ѧУ�ʾ�ģ��
 * 
 * 
 * @author narutoying09@gmail.com
 * 
 */
public class SurveyIndexActivityForCollege extends Activity {

	private static final String TAG = "SurveyIndexActivityForCollege";

	/**
	 * �����Ǳ�����Ŀؼ�
	 */
	private Button nextBtn;
	/** �ʾ��� */
	private EditText numberText;
	//	private EditText surveyPersonIdText;
	//	private EditText surveyPersonNameText;
	//	private EditText surveyPersonCellphoneText;
	//	private EditText surveyLocationText;
	/** �� */
	//	private EditText cityText;
	/** ��/�� */
	private EditText zoneText;
	/** ��/�� */
	private Spinner townText;
	/** �� */
	private Spinner villageText;
	/** ��λ */
	//	private EditText organizationText;

	/** �������ϵ���������ؼ� */
	private List<EditText> allCheckEditTexts = new ArrayList<EditText>();

	private static Map<String, String[]> townVillageMap = new HashMap<String, String[]>();
	private String[] towns = new String[] { "����ɽ��", "��", "����", "����1", "����2",
			"����3" };

	static {
		String[] v1 = new String[] { "20��", "21��", "����1" };
		String[] v2 = new String[] { "��ɽ", "����2" };
		String[] v3 = new String[] { "��ɽ", "����3" };
		String[] v4 = new String[] { "����1" };
		String[] v5 = new String[] { "����2" };
		String[] v6 = new String[] { "����3" };
		townVillageMap.put("����ɽ��", v1);
		townVillageMap.put("��", v2);
		townVillageMap.put("����", v3);
		townVillageMap.put("����1", v4);
		townVillageMap.put("����2", v5);
		townVillageMap.put("����3", v6);
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
					// ��ת���������
					Intent intent = new Intent(
							SurveyIndexActivityForCollege.this,
							SurveyQuestionActivity.class);
					startActivity(intent);
				} else {
					new AlertDialog.Builder(SurveyIndexActivityForCollege.this)
							.setTitle("ϵͳ��ʾ")
							.setMessage("������Ϣ������Ϊ��")
							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialoginterface,
												int i) {
											//��ť�¼� 
										}
									}).show();
				}
			}

			/**
			 * ��װ�û��ڽ�������������š���ַ����Ϣ
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
	 * �������ϵ�������Ƿ�ȫ����Ϊ�գ�ֻҪ��һ��Ϊ�գ��򷵻�true��ʾ�п�
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
	 * �������ϵ���������ؼ�
	 */
	private void addCheckEditTexts() {
		allCheckEditTexts.add(numberText);
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

}
