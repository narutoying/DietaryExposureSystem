/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.DialerKeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.seu.yang11.R;
import com.seu.yang11.common.util.ImageUtil;
import com.seu.yang11.common.util.StringUtil;
import com.seu.yang11.common.util.SystemUtil;
import com.seu.yang11.common.util.validator.BlankValidator;
import com.seu.yang11.common.util.validator.DigitValidator;
import com.seu.yang11.common.util.validator.QuestionBlankValidateUtil;
import com.seu.yang11.common.util.validator.ValidateResult;
import com.seu.yang11.core.AppContext;
import com.seu.yang11.core.domain.model.QuestionTemplate;
import com.seu.yang11.core.domain.model.QuestionaireInstance;
import com.seu.yang11.core.domain.model.enums.QuestionStep;
import com.seu.yang11.core.domain.model.enums.QuestionType;
import com.seu.yang11.core.domain.model.enums.QuestionaireStep;
import com.seu.yang11.core.domain.model.questions.CompletionQuestion;
import com.seu.yang11.core.domain.model.questions.CompletionQuestion.QuestionBlank;
import com.seu.yang11.core.domain.model.questions.Option;
import com.seu.yang11.core.domain.model.questions.OptionQuestion;
import com.seu.yang11.core.service.SurveyService;

/**
 * 调查答题界面
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: SurveyQuestionActivity.java, v 0.1 2012-4-22 下午3:57:57 narutoying Exp $
 */
public class SurveyQuestionActivity extends Activity {

	private static final String TAG = "SurveyQuestionActivity";

	private QuestionTemplate currentQuestionTemplate = null;
	private int currentQuestionIndex;
	private Integer singleOptionSettedNextQuestionIndex = null;
	private Map<RadioButton, Option> singleOptionMap = new HashMap<RadioButton, Option>();
	private List<String> validateErrorResults = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SystemUtil.addActivity(this);
		// 不用xml中配置的界面，完全自己控制界面的构成
		//		setContentView(R.layout.survey_question);
		final AppContext appContext = (AppContext) getApplicationContext();
		init(appContext);
		// 动态创建组件，显示标题、问题、按钮
		ScrollView sv = new ScrollView(this);
		sv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		sv.setBackgroundResource(R.drawable.bg1);
		LinearLayout layout = new LinearLayout(this);
		layout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.setOrientation(LinearLayout.VERTICAL);
//		setQuestionStepTitle(layout, this);
		setQuestionDisplay(layout, this);
		setNextQuestionBtn(layout, this, appContext);
		sv.addView(layout);
		setContentView(sv);
	}

	private void setNextQuestionBtn(LinearLayout layout, Context context,
			final AppContext appContext) {
		Button nextBtn = new Button(context);
		if (SurveyService.isLastQuestion(appContext)) {
			nextBtn.setText("问题完毕");
		} else {
			nextBtn.setText("下一题");
		}
		/*nextBtn.setTextSize(30);*/
		nextBtn.setTextSize(20);
		nextBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String[] answers = getAnswers();
				// 校验用户是否选择，单选题/判断题必选一个，多选题至少选一个，填空题也不能为空
				if (hasAnswered(answers)) {
					// 如果当前题目是填空题，则校验输入框中的文本
					if (validateBlanks(answers)) {
						SurveyService.recordQuestionAnswers(
								SurveyQuestionActivity.this, appContext,
								answers, currentQuestionTemplate,
								currentQuestionIndex, getNextQuestionIndex());
						if (appContext.getCurrentQuestionaireStep() == QuestionaireStep.COMPLETE) {
							// 已是最后一题，则跳转到最后的调查院签字、调查日期页面
							Intent intent = new Intent(
									SurveyQuestionActivity.this,
									SurveyCompleteActivity.class);
							startActivity(intent);
						} else {
							// 还不是最后一题，则展示下一道题目
							Intent intent = new Intent(
									SurveyQuestionActivity.this,
									SurveyQuestionActivity.class);
							startActivity(intent);
						}
					} else {
						// 校验不通过，则显示详细校验结果
						new AlertDialog.Builder(SurveyQuestionActivity.this)
								.setTitle("填空题校验结果")
								.setMessage(buildBlankCheckResults())
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialoginterface,
													int i) {
												//按钮事件 
											}
										}).show();
					}
				} else {
					new AlertDialog.Builder(SurveyQuestionActivity.this)
							.setTitle("系统提示")
							.setMessage("答案不能为空")
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

		});
		layout.addView(nextBtn);
	}

	/**
	 * 构建填空题校验结果，只包括错误信息
	 * 
	 * @return
	 */
	protected String buildBlankCheckResults() {
		StringBuilder sb = new StringBuilder();
		for (String result : this.validateErrorResults) {
			sb.append(result + "\n");
		}
		return sb.toString();
	}

	/**
	 * 校验填空题
	 * 
	 * @return
	 */
	protected boolean validateBlanks(String[] answers) {
		boolean result = true;
		if (this.currentQuestionTemplate instanceof CompletionQuestion) {
			this.validateErrorResults.clear();
			for (int i = 0; i < answers.length; i++) {
				String answer = answers[i];
				BlankValidator blankValidator = this.blankValidators.get(i);
				if (blankValidator != null) {
					ValidateResult validateResult = blankValidator
							.getValidateResult(answer);
					if (!validateResult.isSuccess()) {
						this.validateErrorResults.add(validateResult.getMsg());
						result &= false;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取下一题序号
	 * 当前只有在单选题时会根据不同的选项设置下一题序号，若该单选题未设置或非单选题，则默认返回null
	 * 
	 * @return
	 */
	private Integer getNextQuestionIndex() {
		return this.singleOptionSettedNextQuestionIndex;
	}

	/**
	 * 判断是否答题
	 * 1. 填空题每个空格均不能为空
	 * 2. 选择题至少选一个
	 * 
	 * @param answers
	 * @return
	 */
	protected boolean hasAnswered(String[] answers) {
		boolean result = true;
		if (answers != null && answers.length > 0) {
			for (String answer : answers) {
				if (StringUtil.isBlank(answer)) {
					result = false;
					break;
				}
			}
		} else {
			result = false;
		}
		return result;
	}

	// 获取屏幕上用户的输入
	private String[] getAnswers() {
		String[] result = null;
		if (currentQuestionTemplate != null) {
			QuestionType questionType = currentQuestionTemplate
					.getQuestionType();
			if (questionType == QuestionType.COMPLETION) {
				if (completionInputs != null && completionInputs.size() > 0) {
					result = new String[completionInputs.size()];
					for (int i = 0; i < completionInputs.size(); i++) {
						result[i] = completionInputs.get(i).getText()
								.toString();
					}
				}
			} else if (questionType == QuestionType.TF
					|| questionType == QuestionType.SINGLE_CHOICE) {
				result = new String[1];
				result[0] = getSeletedSingleOption();
			} else if (questionType == QuestionType.MULIPLE_CHOICE) {
				result = getSelectedMultipleOptions();
			} else if (questionType == QuestionType.COMPLEX) {

			}
		}
		return result;
	}

	/**
	 * 获取用户在屏幕上选择的单选选项
	 * 
	 * @return
	 */
	private String getSeletedSingleOption() {
		String result = null;
		if (this.radioButtons != null && this.radioButtons.size() > 0) {
			for (RadioButton radioButton : this.radioButtons) {
				if (radioButton.isChecked()) {
					result = "" + radioButton.getId();
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 获取用户在屏幕上选择的多选选项
	 * 
	 * @return
	 */
	private String[] getSelectedMultipleOptions() {
		List<String> tempResult = new ArrayList<String>();
		if (this.checkBoxes != null && this.checkBoxes.size() > 0) {
			for (CheckBox checkBox : this.checkBoxes) {
				if (checkBox.isChecked()) {
					tempResult.add("" + checkBox.getId());
				}
			}
			return tempResult.toArray(new String[] {});
		}
		return null;
	}

	private void setQuestionStepTitle(LinearLayout layout, Context context) {
		if (currentQuestionTemplate != null) {
			QuestionStep questionStep = currentQuestionTemplate
					.getQuestionStep();
			String stepTitle = "";
			if (questionStep != null) {
				stepTitle = questionStep.getName();
			}
			TextView tv = new TextView(context);
			tv.setText(stepTitle);
			tv.setTextColor(android.graphics.Color.BLACK);
			/*tv.setTextSize(40);*///8份问卷
			tv.setTextSize(25);
			layout.addView(tv);
		}
	}

	/**
	 * 填空题对应的空格控件
	 */
	private List<EditText> completionInputs;
	private List<BlankValidator> blankValidators = new ArrayList<BlankValidator>();
	/**
	 * 单选题、判断题对象的单选选项控件
	 */
	private List<RadioButton> radioButtons;
	/**
	 * 多选题对应的checkbox控件
	 */
	private List<CheckBox> checkBoxes;

	/**
	 * 展示题目，包括题干与题主体
	 */
	private void setQuestionDisplay(LinearLayout layout, final Context context) {
		if (currentQuestionTemplate != null) {
			QuestionType questionType = currentQuestionTemplate
					.getQuestionType();
			String questionTitle = currentQuestionTemplate.getTitle();
			// 填空题
			if (questionType == QuestionType.COMPLETION) {
				CompletionQuestion question = null;
				if (this.currentQuestionTemplate instanceof CompletionQuestion) {
					question = (CompletionQuestion) this.currentQuestionTemplate;
				}
				if (question != null) {
					completionInputs = new ArrayList<EditText>();
					List<Object> segments = question.getSegments();
					for (Object segment : segments) {
						if (segment instanceof String) {
							// 文本
							TextView tv = new TextView(context);
							tv.setText((String) segment);
							tv.setTextColor(android.graphics.Color.BLACK);
							/*tv.setTextSize(35);*/
							tv.setTextSize(25);
							layout.addView(tv);
						} else if (segment instanceof QuestionBlank) {
							// 空格
							EditText et = new EditText(context);
							et.setWidth(100);
							layout.addView(et);
							completionInputs.add(et);
							segment = (QuestionBlank) segment;
							String validatorStr = ((QuestionBlank) segment)
									.getValidator();
							DigitValidator digitValidator = QuestionBlankValidateUtil
									.getDigitValidator(validatorStr);
							blankValidators.add(digitValidator);
							// 数字校验器不为空，则使当前输入框只能输入数字
							if (digitValidator != null) {
								et.setKeyListener(new DialerKeyListener());
							}
						}
					}
				}
			} else if (questionType == QuestionType.SINGLE_CHOICE
					|| questionType == QuestionType.TF) {
				// 单选题与判断题
				OptionQuestion optionQuestion = null;
				if (this.currentQuestionTemplate instanceof OptionQuestion) {
					optionQuestion = (OptionQuestion) this.currentQuestionTemplate;
				}
				if (optionQuestion != null) {
					List<Option> options = optionQuestion.getOptions();
					int indexOfImage = 0;
					String[] titleArr = optionQuestion.getTitleArr();
					if (titleArr != null) {
						for (String titleStr : titleArr) {
							if (StringUtil.equals(titleStr,
									QuestionTemplate.IMAGE_SYMBOL)) {
								String[] imageForTitle = optionQuestion
										.getImageForTitle();
								if (imageForTitle != null
										&& indexOfImage < imageForTitle.length) {
									// 图片占位
									ImageView iv = new ImageView(context);
									iv.setImageResource(ImageUtil.getImageId(
											context,
											imageForTitle[indexOfImage]));
									layout.addView(iv);
									indexOfImage++;
								}
							} else {
								// 文本
								TextView tv = new TextView(context);
								tv.setText(titleStr);
								tv.setTextColor(android.graphics.Color.BLACK);
								/*tv.setTextSize(35);*/
								tv.setTextSize(25);
								layout.addView(tv);
							}
						}
					}
					if (options != null && options.size() > 0) {
						radioButtons = new ArrayList<RadioButton>();
						RadioGroup radioGroup = new RadioGroup(context);
						for (Option oneOption : options) {
							final RadioButton radioButton = new RadioButton(
									context);
							this.singleOptionMap.put(radioButton, oneOption);
							radioButton.setText(oneOption.getSymbolName()
									+ ". " + oneOption.getContent());
							radioButton
									.setTextColor(android.graphics.Color.BLACK);
							/*radioButton.setTextSize(35);*/
							radioButton.setTextSize(25);
							try {
								// 默认以用户配置题目时@前的数字为准，如1@男|2@女，则id为1/2
								// 若配置了"a@男|b@女"，则以index为准
								radioButton.setId(Integer.parseInt(oneOption
										.getSymbolName()));
							} catch (NumberFormatException e) {
								radioButton.setId(oneOption.getIndex());
							} finally {
								radioButton
										.setOnCheckedChangeListener(new OnCheckedChangeListener() {
											@Override
											public void onCheckedChanged(
													CompoundButton buttonView,
													boolean isChecked) {
												// 选中此选项，则根据此选项对应的下一题序号设置singleOptionSettedNextQuestionIndex
												if (isChecked) {
													Option selectOption = singleOptionMap
															.get(radioButton);
													singleOptionSettedNextQuestionIndex = selectOption
															.getNextQuestionIndex();
												}
											}
										});
							}
							radioGroup.addView(radioButton);
							radioButtons.add(radioButton);
						}
						layout.addView(radioGroup);
					}
				}
			} else if (questionType == QuestionType.MULIPLE_CHOICE) {
				// 多选题
				OptionQuestion optionQuestion = null;
				if (this.currentQuestionTemplate instanceof OptionQuestion) {
					optionQuestion = (OptionQuestion) this.currentQuestionTemplate;
				}
				if (optionQuestion != null) {
					List<Option> options = optionQuestion.getOptions();
					TextView tv = new TextView(context);
					tv.setText(questionTitle);
					tv.setTextColor(android.graphics.Color.BLACK);
					/*tv.setTextSize(35);*/
					tv.setTextSize(25);
					layout.addView(tv);
					if (options != null && options.size() > 0) {
						this.checkBoxes = new ArrayList<CheckBox>();
						for (Option oneOption : options) {
							final CheckBox checkBox = new CheckBox(context);
							checkBox.setText(oneOption.getSymbolName() + ". "
									+ oneOption.getContent());
							checkBox.setTextColor(android.graphics.Color.BLACK);
							/*checkBox.setTextSize(35);*/
							checkBox.setTextSize(25);
							// 以选项内部序号为准，如有5个选项，则id为1-5之间的数字
							checkBox.setId(oneOption.getIndex());
							// 1. 若选项答案是 “不知道”，则勾选它之后需要将其他选项的选择全部取消掉
							if (StringUtil.equals("不知道",
									StringUtil.trim(oneOption.getContent()))) {
								checkBox.setTag(1);
								checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

									@Override
									public void onCheckedChanged(
											CompoundButton arg0, boolean arg1) {
										if (checkBox.isChecked()) {
											for (CheckBox cb : checkBoxes) {
												if ((Integer) cb.getTag() == 0) {
													cb.setChecked(false);
												}
											}
										}
									}
								});
							} else {
								//2. 若选择了非"不知道"的答案，则要将“不知道”选项的勾选取消掉
								checkBox.setTag(0);
								checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

									@Override
									public void onCheckedChanged(
											CompoundButton arg0, boolean arg1) {
										if (checkBox.isChecked()) {
											for (CheckBox cb : checkBoxes) {
												if ((Integer) cb.getTag() == 1) {
													cb.setChecked(false);
												}
											}
										}
									}
								});
							}
							layout.addView(checkBox);
							checkBoxes.add(checkBox);
						}
					}
				}
			}
		}
	}

	private void init(AppContext appContext) {
		QuestionaireInstance currentQuestionaireInstance = appContext
				.getCurrentQuestionaireInstance();
		if (currentQuestionaireInstance != null) {
			List<QuestionTemplate> questionTemplate = currentQuestionaireInstance
					.getQuestionnaireTemplate().getQuestionTemplate();
			currentQuestionTemplate = questionTemplate.get(appContext
					.getCurrentQuestionIndex());
			currentQuestionIndex = appContext.getCurrentQuestionIndex();
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

}
