/**
 * 
 */
package com.seu.yang11.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.DialerKeyListener;
import android.util.Log;
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
import com.seu.yang11.common.dal.DBConfigs;
import com.seu.yang11.common.util.AddressUtil;
import com.seu.yang11.common.util.StringUtil;
import com.seu.yang11.common.util.SystemUtil;
import com.seu.yang11.core.AppContext;
import com.seu.yang11.core.domain.model.OperationResult;
import com.seu.yang11.core.domain.model.QuestionaireBasicInfo;
import com.seu.yang11.core.domain.model.SurveyInfoCache;
import com.seu.yang11.core.service.SurveyService;
import com.seu.yang11.view.util.MyViewHelper;

/**
 * ���û�����ҳ�������ʼ���顱��ť����ת����ҳ�� 
 * ��ҳ����дһЩ������Ϣ�����ʾ��ţ�����ص�
 * 
 * 
 * @author narutoying09@gmail.com
 * 
 */
public class SurveyIndexActivity extends Activity {

	private static final String TAG = "SurveyIndexActivity";

	/** ����6λ���� */
	private static final int CACHE_SURVEY_NUMBER_DIGITS = 6;

	/**
	 * �����Ǳ�����Ŀؼ�
	 */
	private Button nextBtn;
	private EditText numberText;
	//	private EditText provinceText;
	//	private EditText cityText;
	//	private EditText zoneText;
	//	private EditText streetText;
	private EditText villageText;
	private EditText doorNumberText;
	// ������
	private Spinner spprovince;
	private Spinner spcity;
	private Spinner sparea;
	private Spinner spstreet;
	/** ��ѡ����� */
	private int cityPosition;
	/** ��ѡ����� */
	private int areaPosition;
	/** �ֵ�ѡ����� */
	private int streetPostion;

	/** �������ϵ���������ؼ� */
	private List<EditText> allCheckEditTexts = new ArrayList<EditText>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SystemUtil.addActivity(this);
		setContentView(R.layout.survey_index);
		initComponents();
		initSpinnerListeners();// ע��˴�����˳�򣡣���
		initialProvince();
		initNextBtnListener();
		loadCachedSurveyInfo();
	}

	/**
	 * ����������Ϣ
	 */
	private void buildCachedSurveyInfo() {
		// �����������������Ϣ����
		SurveyInfoCache cache = new SurveyInfoCache();
		cache.setCityPosition(cityPosition);
		cache.setAreaPosition(areaPosition);
		cache.setStreetPostion(streetPostion);
		cache.setSurveyNumber(numberText.getText().toString());
		cache.setVillageCommittee(villageText.getText().toString());
		AppContext appContext = (AppContext) getApplicationContext();
		appContext.setSurveyInfoCache(cache);
	}

	/**
	 * ���ػ����е�����
	 */
	private void loadCachedSurveyInfo() {
		AppContext appContext = (AppContext) getApplicationContext();
		SurveyInfoCache surveyInfoCache = appContext.getSurveyInfoCache();
		if (surveyInfoCache != null) {
			// �ʾ���
			String surveyNumber = surveyInfoCache.getSurveyNumber();
			if (StringUtil.isNotBlank(surveyNumber)) {
				int length = surveyNumber.length();
				if (length > CACHE_SURVEY_NUMBER_DIGITS) {
					numberText.setText(surveyNumber.substring(0,
							CACHE_SURVEY_NUMBER_DIGITS));
				}
			}
			// �С������ֵ�
			final int cityPosition = surveyInfoCache.getCityPosition();
			final int areaPosition = surveyInfoCache.getAreaPosition();
			final int streetPostion = surveyInfoCache.getStreetPostion();
			new Handler().postDelayed(new Runnable() {        
			    public void run() {
			      spcity.setSelection(cityPosition, false);
			      sparea.setSelection(areaPosition, false);
			      spstreet.setSelection(streetPostion, false);
				}
			}, 200);
			// ��ί��
			villageText.setText(surveyInfoCache.getVillageCommittee());
		}
	}

	private void initNextBtnListener() {
		nextBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!checkHasBlank()) {
					if (!checkNumberDigits()) {
						AppContext appContext = (AppContext) getApplicationContext();
						OperationResult recordBasicInfos = SurveyService
								.recordBasicInfos(SurveyIndexActivity.this,
										appContext, buildQuestionaireInfos());
						if (recordBasicInfos.isSuccess()) {
							buildCachedSurveyInfo();
							// ��ת���������
							Intent intent = new Intent(
									SurveyIndexActivity.this,
									SurveyQuestionActivity.class);
							startActivity(intent);
						} else {
							MyViewHelper.showSimpleDlg(
									SurveyIndexActivity.this, "ϵͳ��ʾ",
									recordBasicInfos.getMessage(), null, null);
						}
					} else {
						MyViewHelper.showSimpleDlg(SurveyIndexActivity.this,
								"ϵͳ��ʾ", "�ʾ���λ������Ϊ10λ", null, null);
					}
				} else {
					MyViewHelper.showSimpleDlg(SurveyIndexActivity.this,
							"ϵͳ��ʾ", "������Ϣ������������Ϊ��", null, null);
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
				result.setProvince(selectedProvince);
				result.setCity(selectedCity);
				result.setZone(selectedArea);
				result.setStreet(selectedStreet);
				result.setVillageCommittee(villageText.getText().toString());
				result.setDoorNumber(doorNumberText.getText().toString());
				return result;
			}

		});
	}

	/**
	 * ����ʾ����Ƿ���������
	 * Ŀǰ�Ǳ���10λ
	 * 
	 * @return û�����ⷵ��false�������ⷵ��true
	 */
	protected boolean checkNumberDigits() {
		String numberString = this.numberText.getText().toString();
		if (StringUtil.isNotBlank(numberString)) {
			int length = numberString.length();
			if (length != 10) {
				return true;
			}
		}
		return false;
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
		spprovince = (Spinner) findViewById(R.id.spprovince);
		spcity = (Spinner) findViewById(R.id.spcity);
		sparea = (Spinner) findViewById(R.id.sparea);
		spstreet = (Spinner) findViewById(R.id.spstreet);
		villageText = (EditText) findViewById(R.id.survey_index_villageCommittee);
		doorNumberText = (EditText) findViewById(R.id.survey_index_doorNumber);
		doorNumberText.setKeyListener(new DialerKeyListener());
		addCheckEditTexts();
	}

	/**
	 * �������ϵ���������ؼ�
	 */
	private void addCheckEditTexts() {
		allCheckEditTexts.add(numberText);
		allCheckEditTexts.add(villageText);
		allCheckEditTexts.add(doorNumberText);
	}

	private File locationFile;
	private List provinceData;
	private List cityData;
	private List zoneData;
	private String selectedProvince = "";// ��������ѡ���ʡ
	private String selectedCity = "";// ��������ѡ�����
	private String selectedArea = "";// ��������ѡ�����
	private String selectedStreet = "";// ��������ѡ��Ľֵ�

	// �����ݿ��ж�ȡʡ����������
	// ��ʼ��ʡ������
	private void initialProvince() {
		try {
			locationFile = new File(
					DBConfigs.getLocationDBPath(getPackageName()));
			if (locationFile.exists()) {
				Map<Integer, List> data = AddressUtil.getProvince(locationFile);
				List provinceList = data.get(1);
				provinceData = data.get(0);
				ArrayAdapter provinceAdapter = new ArrayAdapter(
						getApplicationContext(),
						android.R.layout.simple_spinner_item, provinceList);
				provinceAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // ��ʽ
				spprovince.setAdapter(provinceAdapter);
				spprovince.setOnItemSelectedListener(provinceSelectedListener);
				// �������������Ĭ��ѡ����ʡ�������޷�����ѡ��ʡ�ݨr(�s���t)�q
				spprovince.setEnabled(false);
			}
		} catch (Exception e) {
			Log.d(TAG, "InitialProvince:" + e.getMessage());
		}
	}
	
	private OnItemSelectedListener provinceSelectedListener;
	private OnItemSelectedListener citySelectedListener;
	private OnItemSelectedListener areaSelectedListener;
	private OnItemSelectedListener streetSelectedListener;
	
	private void initSpinnerListeners() {
		// ʡ�������¼�
		provinceSelectedListener = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Spinner spProvince = (Spinner) parent;
					selectedProvince = (String) spProvince
							.getItemAtPosition(position);
					ArrayAdapter cityAdapter = null;
					Map<String, Integer> data = (Map) provinceData.get(position);// ������ѡ��ĵõ���Ӧ��ʡ��id
																					// Ȼ��õ�����
					int pid = data.get(selectedProvince);// �õ�ʡ�ݵ�id
					if (locationFile != null && locationFile.exists()) {
						Map<Integer, List> citymap = AddressUtil.getCityByPid(pid,
								locationFile);
						List cityList = citymap.get(1);
						cityData = citymap.get(0);
						cityAdapter = new ArrayAdapter(getApplicationContext(),
								android.R.layout.simple_spinner_item, cityList);
						cityAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spcity.setAdapter(cityAdapter);
						spcity.setOnItemSelectedListener(citySelectedListener);
					}
				} catch (Exception e) {
					Log.d(TAG, "Select Province error:" + e.getMessage());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		};

		// ���������¼�
		citySelectedListener = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Spinner spCity = (Spinner) parent;
					cityPosition = position;
					selectedCity = (String) spCity.getItemAtPosition(position);
					ArrayAdapter areaAdapter = null;
					Map<String, Integer> data = (Map) cityData.get(position);// ������ѡ��ĵõ���Ӧ��ʡ��id
																				// Ȼ��õ�����
					int cid = data.get(selectedCity);// �õ����е�id
					Map<Integer, List> zoneMap = AddressUtil.getAreaByPid(cid,
							locationFile);
					zoneData = zoneMap.get(0);
					areaAdapter = new ArrayAdapter(getApplicationContext(),
							android.R.layout.simple_spinner_item, zoneMap.get(1));
					areaAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sparea.setAdapter(areaAdapter);
					sparea.setOnItemSelectedListener(areaSelectedListener);
				} catch (Exception e) {
					Log.d(TAG, "Select City error:" + e.getMessage());
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		};

		// ���������¼�
		areaSelectedListener = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Spinner spArea = (Spinner) parent;
					areaPosition = position;
					selectedArea = (String) spArea.getItemAtPosition(position);
					ArrayAdapter streetAdapter = null;
					Map<String, Integer> data = (Map) zoneData.get(position);// ������ѡ��ĵõ���Ӧ��ʡ��id
																				// Ȼ��õ�����
					int zone_id = data.get(selectedArea);// �õ�����id
					List<String> streetByZoneId = AddressUtil.getStreetByZoneId(
							zone_id, locationFile);
					streetAdapter = new ArrayAdapter(getApplicationContext(),
							android.R.layout.simple_spinner_item, streetByZoneId);
					streetAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spstreet.setAdapter(streetAdapter);
					spstreet.setOnItemSelectedListener(streetSelectedListener);
				} catch (Exception e) {
					Log.d(TAG, "Select Area error:" + e.getMessage());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		};

		streetSelectedListener = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					streetPostion = position;
					Spinner spStreet = (Spinner) parent;
					selectedStreet = (String) spStreet.getItemAtPosition(position);
				} catch (Exception e) {
					Log.d(TAG, "Select Street error:" + e.getMessage());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		};
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
