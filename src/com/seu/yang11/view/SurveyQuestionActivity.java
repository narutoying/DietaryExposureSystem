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
 * ����������
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: SurveyQuestionActivity.java, v 0.1 2012-4-22 ����3:57:57 narutoying Exp $
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
		// ����xml�����õĽ��棬��ȫ�Լ����ƽ���Ĺ���
		//		setContentView(R.layout.survey_question);
		final AppContext appContext = (AppContext) getApplicationContext();
		init(appContext);
		// ��̬�����������ʾ���⡢���⡢��ť
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
			nextBtn.setText("�������");
		} else {
			nextBtn.setText("��һ��");
		}
		/*nextBtn.setTextSize(30);*/
		nextBtn.setTextSize(20);
		nextBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String[] answers = getAnswers();
				// У���û��Ƿ�ѡ�񣬵�ѡ��/�ж����ѡһ������ѡ������ѡһ���������Ҳ����Ϊ��
				if (hasAnswered(answers)) {
					// �����ǰ��Ŀ������⣬��У��������е��ı�
					if (validateBlanks(answers)) {
						SurveyService.recordQuestionAnswers(
								SurveyQuestionActivity.this, appContext,
								answers, currentQuestionTemplate,
								currentQuestionIndex, getNextQuestionIndex());
						if (appContext.getCurrentQuestionaireStep() == QuestionaireStep.COMPLETE) {
							// �������һ�⣬����ת�����ĵ���Ժǩ�֡���������ҳ��
							Intent intent = new Intent(
									SurveyQuestionActivity.this,
									SurveyCompleteActivity.class);
							startActivity(intent);
						} else {
							// ���������һ�⣬��չʾ��һ����Ŀ
							Intent intent = new Intent(
									SurveyQuestionActivity.this,
									SurveyQuestionActivity.class);
							startActivity(intent);
						}
					} else {
						// У�鲻ͨ��������ʾ��ϸУ����
						new AlertDialog.Builder(SurveyQuestionActivity.this)
								.setTitle("�����У����")
								.setMessage(buildBlankCheckResults())
								.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialoginterface,
													int i) {
												//��ť�¼� 
											}
										}).show();
					}
				} else {
					new AlertDialog.Builder(SurveyQuestionActivity.this)
							.setTitle("ϵͳ��ʾ")
							.setMessage("�𰸲���Ϊ��")
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

		});
		layout.addView(nextBtn);
	}

	/**
	 * ���������У������ֻ����������Ϣ
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
	 * У�������
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
	 * ��ȡ��һ�����
	 * ��ǰֻ���ڵ�ѡ��ʱ����ݲ�ͬ��ѡ��������һ����ţ����õ�ѡ��δ���û�ǵ�ѡ�⣬��Ĭ�Ϸ���null
	 * 
	 * @return
	 */
	private Integer getNextQuestionIndex() {
		return this.singleOptionSettedNextQuestionIndex;
	}

	/**
	 * �ж��Ƿ����
	 * 1. �����ÿ���ո������Ϊ��
	 * 2. ѡ��������ѡһ��
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

	// ��ȡ��Ļ���û�������
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
	 * ��ȡ�û�����Ļ��ѡ��ĵ�ѡѡ��
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
	 * ��ȡ�û�����Ļ��ѡ��Ķ�ѡѡ��
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
			/*tv.setTextSize(40);*///8���ʾ�
			tv.setTextSize(25);
			layout.addView(tv);
		}
	}

	/**
	 * ������Ӧ�Ŀո�ؼ�
	 */
	private List<EditText> completionInputs;
	private List<BlankValidator> blankValidators = new ArrayList<BlankValidator>();
	/**
	 * ��ѡ�⡢�ж������ĵ�ѡѡ��ؼ�
	 */
	private List<RadioButton> radioButtons;
	/**
	 * ��ѡ���Ӧ��checkbox�ؼ�
	 */
	private List<CheckBox> checkBoxes;

	/**
	 * չʾ��Ŀ�����������������
	 */
	private void setQuestionDisplay(LinearLayout layout, final Context context) {
		if (currentQuestionTemplate != null) {
			QuestionType questionType = currentQuestionTemplate
					.getQuestionType();
			String questionTitle = currentQuestionTemplate.getTitle();
			// �����
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
							// �ı�
							TextView tv = new TextView(context);
							tv.setText((String) segment);
							tv.setTextColor(android.graphics.Color.BLACK);
							/*tv.setTextSize(35);*/
							tv.setTextSize(25);
							layout.addView(tv);
						} else if (segment instanceof QuestionBlank) {
							// �ո�
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
							// ����У������Ϊ�գ���ʹ��ǰ�����ֻ����������
							if (digitValidator != null) {
								et.setKeyListener(new DialerKeyListener());
							}
						}
					}
				}
			} else if (questionType == QuestionType.SINGLE_CHOICE
					|| questionType == QuestionType.TF) {
				// ��ѡ�����ж���
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
									// ͼƬռλ
									ImageView iv = new ImageView(context);
									iv.setImageResource(ImageUtil.getImageId(
											context,
											imageForTitle[indexOfImage]));
									layout.addView(iv);
									indexOfImage++;
								}
							} else {
								// �ı�
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
								// Ĭ�����û�������Ŀʱ@ǰ������Ϊ׼����1@��|2@Ů����idΪ1/2
								// ��������"a@��|b@Ů"������indexΪ׼
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
												// ѡ�д�ѡ�����ݴ�ѡ���Ӧ����һ���������singleOptionSettedNextQuestionIndex
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
				// ��ѡ��
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
							// ��ѡ���ڲ����Ϊ׼������5��ѡ���idΪ1-5֮�������
							checkBox.setId(oneOption.getIndex());
							// 1. ��ѡ����� ����֪��������ѡ��֮����Ҫ������ѡ���ѡ��ȫ��ȡ����
							if (StringUtil.equals("��֪��",
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
								//2. ��ѡ���˷�"��֪��"�Ĵ𰸣���Ҫ������֪����ѡ��Ĺ�ѡȡ����
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
