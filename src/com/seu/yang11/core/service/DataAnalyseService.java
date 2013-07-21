/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.seu.yang11.common.dal.dao.QuestionAnswerDAO;
import com.seu.yang11.common.dal.dataobject.QuestionAnswerDO;
import com.seu.yang11.common.util.MathUtil;
import com.seu.yang11.common.util.StringUtil;
import com.seu.yang11.core.domain.model.questions.CompletionQuestion;

/**
 * 数据分析服务
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: DataAnalyseService.java, v 0.1 2012-5-13 下午3:20:49 narutoying Exp $
 */
public class DataAnalyseService {

	private static final int AGE_TEMPLATE_ID = 1232;
	private static final int SEX_TEMPLATE_ID = 1231;
	private QuestionAnswerDAO questionAnswerDAO;

	public DataAnalyseService(Context context) {
		super();
		this.questionAnswerDAO = new QuestionAnswerDAO(context);
	}

	private static final int SEX_MALE = 1;
	private static final int SEX_FEMALE = 2;

	/**
	 * 获取男女比例信息，格式如下：
	 * 本次调查共XX人
	 * 其中男性XX人，占比XX%
	 * 其中女性YY人，占比YY%
	 * 
	 * @return
	 */
	public List<String> getSexPercentage() {
		List<String> results = new ArrayList<String>();
		List<QuestionAnswerDO> allDOs = this.questionAnswerDAO.getAll();
		int total = 0;
		int maleNum = 0;
		int femaleNum = 0;
		if (allDOs != null && allDOs.size() > 0) {
			for (QuestionAnswerDO answerDO : allDOs) {
				int questionTemplateId = answerDO.getQuestionTemplateId();
				if (questionTemplateId == SEX_TEMPLATE_ID) { // 与数据库中对应性别的题目id对应
					String sex = answerDO.getContent();
					if (StringUtil.isNotBlank(sex)) {
						int sexId = Integer.parseInt(sex);
						if (sexId == SEX_MALE) {
							maleNum++;
						} else if (sexId == SEX_FEMALE) {
							femaleNum++;
						}
						total++;
					}
				}
			}
		}
		String totalStr = "本次调查共" + total + "人";
		String maleStr = "其中男性" + maleNum + "人，占比"
				+ calPercent(maleNum, total, 2);
		String femaleStr = "其中女性" + femaleNum + "人，占比"
				+ calPercent(femaleNum, total, 2);
		results.add(totalStr);
		results.add(maleStr);
		results.add(femaleStr);
		return results;
	}

	private String calPercent(int num1, int num2, int scale) {
		return MathUtil.addZero(MathUtil.divide(num1, num2, scale)) + "%";
	}

	/**
	 * 获取年龄段信息，年龄段有：15-24,25-34,35-44,45-54,55-64,65以上。
	 * 格式如下：
	 * 10岁以下： XX人
	 * 10-19岁：XX人
	 * 20-29岁：XX人
	 * 30-39岁：XX人
	 * ……
	 * 80岁以上： XX人
	 * 
	 * @return
	 */
	public List<String> getAgeLevels() {
		List<String> results = new ArrayList<String>();
		List<QuestionAnswerDO> allDOs = this.questionAnswerDAO.getAll();
		int[] ageArr = new int[6];
		for (int age : ageArr) {
			age = 0;
		}
		int total = 0;
		if (allDOs != null && allDOs.size() > 0) {
			for (QuestionAnswerDO answerDO : allDOs) {
				int questionTemplateId = answerDO.getQuestionTemplateId();
				if (questionTemplateId == AGE_TEMPLATE_ID) {// 与数据库中对应年龄的题目id对应
					String ageStr = buildAge(answerDO.getContent());
					if (StringUtil.isNotBlank(ageStr)) {
						int age;
						try {
							age = Integer.parseInt(ageStr);
						} catch (NumberFormatException e) {
							continue;
						}
						if (age >= 15 && age <= 24) {
							ageArr[0]++;
						} else if (age >= 25 && age <= 34) {
							ageArr[1]++;
						} else if (age >= 35 && age <= 44) {
							ageArr[2]++;
						} else if (age >= 45 && age <= 54) {
							ageArr[3]++;
						} else if (age >= 55 && age <= 64) {
							ageArr[4]++;
						} else if (age >= 65) {
							ageArr[5]++;
						}
						total++;
					}
				}
			}
		} // //15-24,25-34,35-44,45-54,55-64,65以上。
		for (int i = 0; i < ageArr.length; i++) {
			if (i == 0) {
				results.add("15岁-24岁: " + ageArr[i] + "人，占比"
						+ calPercent(ageArr[i], total, 2));
			} else if (i == 1) {
				results.add("25-34岁: " + ageArr[i] + "人，占比"
						+ calPercent(ageArr[i], total, 2));
			} else if (i == 2) {
				results.add("35-44岁: " + ageArr[i] + "人，占比"
						+ calPercent(ageArr[i], total, 2));
			} else if (i == 3) {
				results.add("45-54岁: " + ageArr[i] + "人，占比"
						+ calPercent(ageArr[i], total, 2));
			} else if (i == 4) {
				results.add("55-64岁: " + ageArr[i] + "人，占比"
						+ calPercent(ageArr[i], total, 2));
			} else if (i == 5) {
				results.add("65岁以上: " + ageArr[i] + "人，占比"
						+ calPercent(ageArr[i], total, 2));
			}
		}
		return results;
	}

	/**
	 * 
	 * @param content 1988$11
	 * @return
	 */
	private String buildAge(String content) {
		if (StringUtil.isNotBlank(content)) {
			String[] ageArr = StringUtil.split(content,
					CompletionQuestion.BLANK_FLAG);
			if (ageArr != null && ageArr.length > 0) {
				int year = Integer.parseInt(ageArr[0]);
				return (new Date().getYear() + 1900 - year) + "";
			}
		}
		return null;
	}
}
