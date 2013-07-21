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
 * 当用户在首页点击“开始调查”按钮后跳转到此页面 
 * 本页面填写一些基本信息，如问卷编号，调查地点
 * 
 * 
 * @author narutoying09@gmail.com
 * 
 */
public class SurveyIndexActivity extends Activity {

	private static final String TAG = "SurveyIndexActivity";

	/** 缓存6位数字 */
	private static final int CACHE_SURVEY_NUMBER_DIGITS = 6;

	/**
	 * 下面是本界面的控件
	 */
	private Button nextBtn;
	private EditText numberText;
	//	private EditText provinceText;
	//	private EditText cityText;
	//	private EditText zoneText;
	//	private EditText streetText;
	private EditText villageText;
	private EditText doorNumberText;
	// 下拉框
	private Spinner spprovince;
	private Spinner spcity;
	private Spinner sparea;
	private Spinner spstreet;
	/** 市选项序号 */
	private int cityPosition;
	/** 区选项序号 */
	private int areaPosition;
	/** 街道选项序号 */
	private int streetPostion;

	/** 本界面上的所有输入控件 */
	private List<EditText> allCheckEditTexts = new ArrayList<EditText>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SystemUtil.addActivity(this);
		setContentView(R.layout.survey_index);
		initComponents();
		initSpinnerListeners();// 注意此处方法顺序！！！
		initialProvince();
		initNextBtnListener();
		loadCachedSurveyInfo();
	}

	/**
	 * 构建缓存信息
	 */
	private void buildCachedSurveyInfo() {
		// 设置上下文中相关信息缓存
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
	 * 加载缓存中的数据
	 */
	private void loadCachedSurveyInfo() {
		AppContext appContext = (AppContext) getApplicationContext();
		SurveyInfoCache surveyInfoCache = appContext.getSurveyInfoCache();
		if (surveyInfoCache != null) {
			// 问卷编号
			String surveyNumber = surveyInfoCache.getSurveyNumber();
			if (StringUtil.isNotBlank(surveyNumber)) {
				int length = surveyNumber.length();
				if (length > CACHE_SURVEY_NUMBER_DIGITS) {
					numberText.setText(surveyNumber.substring(0,
							CACHE_SURVEY_NUMBER_DIGITS));
				}
			}
			// 市、区、街道
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
			// 居委会
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
							// 跳转到答题界面
							Intent intent = new Intent(
									SurveyIndexActivity.this,
									SurveyQuestionActivity.class);
							startActivity(intent);
						} else {
							MyViewHelper.showSimpleDlg(
									SurveyIndexActivity.this, "系统提示",
									recordBasicInfos.getMessage(), null, null);
						}
					} else {
						MyViewHelper.showSimpleDlg(SurveyIndexActivity.this,
								"系统提示", "问卷编号位数必须为10位", null, null);
					}
				} else {
					MyViewHelper.showSimpleDlg(SurveyIndexActivity.this,
							"系统提示", "基本信息各输入框均不能为空", null, null);
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
	 * 检查问卷编号是否满足条件
	 * 目前是必须10位
	 * 
	 * @return 没有问题返回false，有问题返回true
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
	 * 将界面上的所有输入控件
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
	private String selectedProvince = "";// 下拉框中选择的省
	private String selectedCity = "";// 下拉框中选择的市
	private String selectedArea = "";// 下拉框中选择的区
	private String selectedStreet = "";// 下拉框中选择的街道

	// 从数据库中读取省市区的数据
	// 初始化省下拉框
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
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // 样式
				spprovince.setAdapter(provinceAdapter);
				spprovince.setOnItemSelectedListener(provinceSelectedListener);
				// 下面代码是用于默认选择江苏省，并且无法自行选择省份╮(╯▽╰)╭
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
		// 省下拉框事件
		provinceSelectedListener = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Spinner spProvince = (Spinner) parent;
					selectedProvince = (String) spProvince
							.getItemAtPosition(position);
					ArrayAdapter cityAdapter = null;
					Map<String, Integer> data = (Map) provinceData.get(position);// 根据所选择的得到对应的省份id
																					// 然后得到城市
					int pid = data.get(selectedProvince);// 得到省份的id
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

		// 市下拉框事件
		citySelectedListener = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Spinner spCity = (Spinner) parent;
					cityPosition = position;
					selectedCity = (String) spCity.getItemAtPosition(position);
					ArrayAdapter areaAdapter = null;
					Map<String, Integer> data = (Map) cityData.get(position);// 根据所选择的得到对应的省份id
																				// 然后得到城市
					int cid = data.get(selectedCity);// 得到城市的id
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

		// 区下拉框事件
		areaSelectedListener = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Spinner spArea = (Spinner) parent;
					areaPosition = position;
					selectedArea = (String) spArea.getItemAtPosition(position);
					ArrayAdapter streetAdapter = null;
					Map<String, Integer> data = (Map) zoneData.get(position);// 根据所选择的得到对应的省份id
																				// 然后得到城市
					int zone_id = data.get(selectedArea);// 得到区的id
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
