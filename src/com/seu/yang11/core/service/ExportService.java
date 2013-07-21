/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.seu.yang11.common.util.DateUtil;
import com.seu.yang11.common.util.ExcelWriter;
import com.seu.yang11.common.util.StringUtil;
import com.seu.yang11.core.constants.Constants;
import com.seu.yang11.core.domain.model.QuestionAnswer;
import com.seu.yang11.core.domain.model.QuestionTemplate;
import com.seu.yang11.core.domain.model.QuestionaireBasicInfo;
import com.seu.yang11.core.domain.model.QuestionaireCompleteSign;
import com.seu.yang11.core.domain.model.QuestionaireInstance;
import com.seu.yang11.core.domain.model.QuestionaireTemplate;
import com.seu.yang11.core.domain.model.enums.QuestionType;
import com.seu.yang11.core.domain.model.questions.MultipleOptionQuestion;
import com.seu.yang11.core.domain.model.questions.Option;
import com.seu.yang11.core.domain.model.questions.OptionQuestion;
import com.seu.yang11.core.repository.QuestionaireInstanceRepository;
import com.seu.yang11.core.repository.QuestionaireTemplateRepository;
import com.seu.yang11.core.repository.impl.QuestionaireInstanceRepositoryImpl;
import com.seu.yang11.core.repository.impl.QuestionaireTemplateRepositoryImpl;

/**
 * 导出服务
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: ExportService.java, v 0.1 2012-5-5 下午7:15:30 narutoying Exp $
 */
public class ExportService {

	private Context context;
	private QuestionaireInstanceRepository questionaireInstanceRepository;
	private QuestionaireTemplateRepository questionaireTemplateRepository;

	public ExportService(Context context) {
		super();
		this.context = context;
		this.questionaireInstanceRepository = new QuestionaireInstanceRepositoryImpl(
				context);
		this.questionaireTemplateRepository = new QuestionaireTemplateRepositoryImpl(
				context);
	}

	/**
	 * 将android端sqlite中存放的问卷数据导出为excel问卷
	 * 格式如下：
	 * 问卷编号 | 调查人 | 调查日期 | 省 | 市 | …… | 001(问题编号) | …… | 408 
	 * 320xxx  | 小妹  |2012-05-05 | 江苏 | 太仓 | …… | 1 | …… | 睡觉$吃饭$看微博
	 */
	public boolean exportQuestionaireDatasToExcel() {
		File f = new File("/sdcard/desResult.xls");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e1) {
			}
		}
		ExcelWriter e = null;
		try {
			e = new ExcelWriter(new FileOutputStream(f));
		} catch (FileNotFoundException e3) {
		}
		try {
			List<QuestionaireInstance> allQuestionaireInstances = this.questionaireInstanceRepository
					.getAllInstances();
			// 绘制表头
			e.createRow(0);
			e.setCell(0, "问卷编号");
			e.setCell(1, "调查人");
			e.setCell(2, "调查日期");
			e.setCell(3, "省");
			e.setCell(4, "市");
			e.setCell(5, "区(县)");
			e.setCell(6, "街道(镇)");
			e.setCell(7, "居委会(村)");
			e.setCell(8, "门牌号");
			// TODO 改造，适应每份问卷不同的模板
			QuestionaireTemplate defaultTemplate = this.questionaireTemplateRepository
					.getDefaultTemplate();
			List<QuestionTemplate> defaultQuestionTemplates = defaultTemplate
					.getQuestionTemplate();
			int cellCount = 9;
			for (QuestionTemplate questionTemplate : defaultQuestionTemplates) {
				//				QuestionType questionType = questionTemplate.getQuestionType();
				//				if (questionType == QuestionType.MULIPLE_CHOICE) {
				//					/*
				//					 * 多选题个性化定制表头
				//					 * 假设有多选题形如"a@选项1|b@选项2|c@选项3|d@选项4|e@选项5"，题目序号"3-1"
				//					 * 并且用户勾选了前三个选项
				//					 * 则在表格上需要展示为 
				//					 * 3-1.a 3-1.b 3-1.c 3-1.d 3-1.e
				//					 *    1    1     1     0     0
				//					 */
				//					OptionQuestion temp = (OptionQuestion) questionTemplate;
				//					temp.buildOptions();
				//					List<Option> options = temp.getOptions();
				//					if (options != null && options.size() > 0) {
				//						for (Option oneOption : options) {
				//							e.setCell(cellCount++,
				//									questionTemplate.buildQuestionNumber()
				//											+ "." + oneOption.getSymbolName());
				//						}
				//					}
				//				} 
				e.setCell(cellCount++, questionTemplate.buildQuestionNumber());
			}

			int rowCount = 1;
			for (QuestionaireInstance questionaireInstance : allQuestionaireInstances) {
				e.createRow(rowCount++);
				QuestionaireBasicInfo basicInfo = questionaireInstance
						.getQuestionnaireBasicInfo();
				QuestionaireCompleteSign completeSign = questionaireInstance
						.getQuestionaireCompleteSign();
				e.setCell(0, basicInfo.getNumber());
				e.setCell(1, completeSign.getSurveyPerson());
				//				e.setCell(2, DateUtil.convertToString(
				//						completeSign.getSurveyDate(), DateUtil.defaultPattern));
				e.setCell(3, basicInfo.getProvince());
				e.setCell(4, basicInfo.getCity());
				e.setCell(5, basicInfo.getZone());
				e.setCell(6, basicInfo.getStreet());
				e.setCell(7, basicInfo.getVillageCommittee());
				e.setCell(8, basicInfo.getDoorNumber());
				int answerCellCount = 9;
				for (QuestionAnswer answer : questionaireInstance
						.getQuestionAnswer()) {
					//					QuestionTemplate questionTemplate = answer
					//							.getQuestionTemplate();
					//					QuestionType questionType = questionTemplate
					//							.getQuestionType();
					//					if (questionType == QuestionType.MULIPLE_CHOICE) {
					//						/*
					//						 * 多选题个性化定制表头
					//						 * 假设有多选题形如"a@选项1|b@选项2|c@选项3|d@选项4|e@选项5"，题目序号"3-1"
					//						 * 并且用户勾选了前三个选项 1$2$3
					//						 * 则在表格上需要展示为 
					//						 * 3-1.a 3-1.b 3-1.c 3-1.d 3-1.e
					//						 *    1    1     1     0     0
					//						 */
					//						OptionQuestion temp = (OptionQuestion) questionTemplate;
					//						temp.buildOptions();
					//						List<Option> options = temp.getOptions();
					//						if (options != null && options.size() > 0) {
					//							for (int i = 0; i < options.size(); i++) {
					//								e.setCell(
					//										answerCellCount++,
					//										buildAnswerForMultipleOption(
					//												answer.getAnswer(), i));
					//							}
					//						}
					//					} 

					String answerStr = answer.getAnswer();
					QuestionTemplate questionTemplate = answer
							.getQuestionTemplate();
					if (questionTemplate.getQuestionType() == QuestionType.MULIPLE_CHOICE) {
						OptionQuestion optionQuestion = (OptionQuestion) questionTemplate;
						List<Option> options = optionQuestion.getOptions();
						answerStr = buildAnswerForMultipleOptionQuestion(
								answerStr, options);
					}
					e.setCell(answerCellCount++, answerStr);
				}
			}
			e.export();
			return true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e2) {
			e2.printStackTrace();
			return false;
		}
	}

	public boolean exportQuestionaireDatasToExcelForCollege() {
		String filename = "/sdcard/"
				+ this.context.getPackageManager().getApplicationLabel(
						this.context.getApplicationInfo()) + "-调查结果.xls";
		//		filename = "/sdcard/result.xls";
		File f = new File(filename);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e1) {
			}
		}
		ExcelWriter e = null;
		try {
			e = new ExcelWriter(new FileOutputStream(f));
		} catch (FileNotFoundException e3) {
		}
		try {
			List<QuestionaireInstance> allQuestionaireInstances = this.questionaireInstanceRepository
					.getAllInstances();
			// 绘制表头
			e.createRow(0);
			e.setCell(0, "问卷编号");
			e.setCell(1, "调查员");
			e.setCell(2, "监督员");
			e.setCell(3, "调查开始时间");
			e.setCell(4, "调查结束时间");
			e.setCell(5, "区(县)");
			e.setCell(6, "街道(乡镇)");
			e.setCell(7, "居委会(村)");
			QuestionaireTemplate questionaireTemplate = this.questionaireTemplateRepository
					.getTemplateById(Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID);
			List<QuestionTemplate> questionTemplates = questionaireTemplate
					.getQuestionTemplate();
			int cellCount = 8;
			for (QuestionTemplate questionTemplate : questionTemplates) {
				try {
					e.setCell(cellCount++,
							questionTemplate.buildQuestionNumber());
				} catch (Exception ex) {
					Log.e("ExportService", "构建题号出错:" + questionTemplate.getId());
					return false;
				}
			}
			int rowCount = 1;
			for (QuestionaireInstance questionaireInstance : allQuestionaireInstances) {
				e.createRow(rowCount++);
				QuestionaireBasicInfo basicInfo = questionaireInstance
						.getQuestionnaireBasicInfo();
				QuestionaireCompleteSign completeSign = questionaireInstance
						.getQuestionaireCompleteSign();
				e.setCell(0, basicInfo.getNumber());
				e.setCell(1, completeSign.getSurveyPerson());
				e.setCell(2, completeSign.getCheckPerson());
				e.setCell(3, DateUtil.convertToString(
						completeSign.getSurveyStartTime(),
						DateUtil.defaultPattern));
				e.setCell(4, DateUtil.convertToString(
						completeSign.getSurveyEndTime(),
						DateUtil.defaultPattern));
				e.setCell(5, basicInfo.getZone());
				e.setCell(6, basicInfo.getStreet());
				e.setCell(7, basicInfo.getVillageCommittee());
				int answerCellCount = 8;
				for (QuestionAnswer answer : questionaireInstance
						.getQuestionAnswer()) {
					String answerStr = answer.getAnswer();
					QuestionTemplate questionTemplate = answer
							.getQuestionTemplate();
					if (questionTemplate.getQuestionType() == QuestionType.MULIPLE_CHOICE) {
						OptionQuestion optionQuestion = (OptionQuestion) questionTemplate;
						List<Option> options = optionQuestion.getOptions();
						answerStr = buildAnswerForMultipleOptionQuestion(
								answerStr, options);
					}
					e.setCell(answerCellCount++, answerStr);
				}
			}
			e.export();
			return true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e2) {
			e2.printStackTrace();
			return false;
		}
	}

	/**
	 * 为多选题构建答案
	 * 
	 * @param answerStr 1$2$3 选项有5个
	 * @param options 11100
	 * @return
	 */
	private String buildAnswerForMultipleOptionQuestion(String answerStr,
			List<Option> options) {
		if (options != null && options.size() > 0) {
			int numOfOptions = options.size();
			String result = "";
			int[] resultArr = new int[numOfOptions];
			String[] splitAnswers = StringUtil.split(answerStr,
					MultipleOptionQuestion.ANSWER_SPLIT_SYMBOL);
			if (splitAnswers != null && splitAnswers.length > 0) {
				for (String oneAnswer : splitAnswers) {
					if (StringUtil.isNotBlank(oneAnswer)) {
						int oneAnswerInt = Integer.parseInt(oneAnswer);
						resultArr[oneAnswerInt - 1] = 1;
					}
				}
			}
			for (int oneInt : resultArr) {
				result = result + oneInt;
			}
			return result;
		}
		return null;
	}

	//	/**
	//	 * 
	//	 * 
	//	 * @param answer 1$2$3
	//	 * @param i
	//	 * @return
	//	 */
	//	private String buildAnswerForMultipleOption(String answer, int i) {
	//		String[] arr = StringUtil.split(answer,
	//				MultipleOptionQuestion.ANSWER_SPLIT_SYMBOL);
	//		for (String str : arr) {
	//			int answerInt = Integer.parseInt(str);
	//			if ((i + 1) == answerInt) {
	//				return "1";
	//			}
	//		}
	//		return "0";
	//	}
}
