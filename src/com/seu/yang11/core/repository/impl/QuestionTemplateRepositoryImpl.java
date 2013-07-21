/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.repository.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.seu.yang11.common.dal.convert.QuestionStepConvertor;
import com.seu.yang11.common.dal.dao.QuestionTemplateDAO;
import com.seu.yang11.common.dal.dao.QuestionaireTemplateDAO;
import com.seu.yang11.common.dal.dataobject.QuestionTemplateDO;
import com.seu.yang11.common.dal.dataobject.QuestionaireTemplateDO;
import com.seu.yang11.common.util.StringUtil;
import com.seu.yang11.core.domain.model.QuestionTemplate;
import com.seu.yang11.core.domain.model.enums.QuestionStep;
import com.seu.yang11.core.domain.model.enums.QuestionType;
import com.seu.yang11.core.domain.model.questions.CompletionQuestion;
import com.seu.yang11.core.domain.model.questions.MultipleOptionQuestion;
import com.seu.yang11.core.domain.model.questions.SingleOptionQuestion;
import com.seu.yang11.core.domain.model.questions.TFQuestion;
import com.seu.yang11.core.repository.QuestionTemplateRepository;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionTemplateRepositoryImpl.java, v 0.1 2012-5-4 下午8:24:44 narutoying Exp $
 */
public class QuestionTemplateRepositoryImpl implements
		QuestionTemplateRepository {

	private static final String Tag = "QuestionTemplateRepositoryImpl";

	private Context context;
	private QuestionTemplateDAO questionTemplateDAO;
	private QuestionaireTemplateDAO questionaireTemplateDAO;

	public QuestionTemplateRepositoryImpl() {
		super();
	}

	public QuestionTemplateRepositoryImpl(Context context) {
		super();
		this.context = context;
		this.questionTemplateDAO = new QuestionTemplateDAO(context);
		this.questionaireTemplateDAO = new QuestionaireTemplateDAO(context);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public List<QuestionTemplate> getByQuestionaireTemplateId(
			int questionaireTemplateId) {
		// 通过问卷模板id查询对应所有的问题模板
		List<QuestionTemplate> result = new ArrayList<QuestionTemplate>();
		List<QuestionTemplateDO> findByQuestionaireTemplateId = this.questionTemplateDAO
				.findByQuestionaireTemplateId(questionaireTemplateId);
		QuestionaireTemplateDO questionaireTemplateDO = this.questionaireTemplateDAO
				.findById(questionaireTemplateId);
		String questionStepDescription = questionaireTemplateDO
				.getQuestionSteps();
		if (findByQuestionaireTemplateId != null
				&& findByQuestionaireTemplateId.size() > 0) {
			for (QuestionTemplateDO questionTemplateDO : findByQuestionaireTemplateId) {
				try {
					QuestionTemplate questionTemplate = buildQuestionTemplate(
							questionTemplateDO, questionStepDescription);
					result.add(questionTemplate);
				} catch (Exception e) {
					Log.e(Tag, "构建问题模板出错，id=" + questionTemplateDO.getId(), e);
				}
			}
		}
		return result;
	}

	private QuestionTemplate buildQuestionTemplate(
			QuestionTemplateDO questionTemplateDO,
			String questionStepDescription) {
		QuestionTemplate questionTemplate = null;
		int id = questionTemplateDO.getId();
		int type = questionTemplateDO.getType();
		int index = questionTemplateDO.getqIndex();
		int templateId = questionTemplateDO.getQuestionaireTemplateId();
		int step = questionTemplateDO.getStep();
		QuestionStep questionStep = QuestionStepConvertor.getQuestionStep(step,
				questionStepDescription);
		String title = questionTemplateDO.getTitle();
		String content = questionTemplateDO.getContent();
		QuestionType questionType = QuestionType.getQuestionType(type);
		if (questionType == QuestionType.COMPLETION) {
			CompletionQuestion tmp = new CompletionQuestion();
			tmp.setId(id);
			tmp.setIndex(index);
			tmp.setTitle(title);
			tmp.setContent(content);
			tmp.setQuestionnaireTemplateId(templateId);
			tmp.setQuestionType(questionType);
			tmp.setQuestionStep(questionStep);
			tmp.setBlankValidations(buildBlankValidations(questionTemplateDO
					.getValidator()));
			tmp.buildBlanks();
			questionTemplate = tmp;
		} else if (questionType == QuestionType.TF) {
			TFQuestion tmp = new TFQuestion();
			tmp.setId(id);
			tmp.setIndex(index);
			tmp.setTitle(title);
			tmp.setContent(content);
			tmp.setQuestionnaireTemplateId(templateId);
			tmp.setQuestionType(questionType);
			tmp.setQuestionStep(questionStep);
			tmp.buildOptions();
			questionTemplate = tmp;
		} else if (questionType == QuestionType.SINGLE_CHOICE) {
			SingleOptionQuestion tmp = new SingleOptionQuestion();
			tmp.setId(id);
			tmp.setIndex(index);
			tmp.setTitle(title);
			tmp.setContent(content);
			tmp.setQuestionnaireTemplateId(templateId);
			tmp.setQuestionType(questionType);
			tmp.setQuestionStep(questionStep);
			tmp.buildOptions();
			questionTemplate = tmp;
		} else if (questionType == QuestionType.MULIPLE_CHOICE) {
			MultipleOptionQuestion tmp = new MultipleOptionQuestion();
			tmp.setId(id);
			tmp.setIndex(index);
			tmp.setTitle(title);
			tmp.setContent(content);
			tmp.setQuestionnaireTemplateId(templateId);
			tmp.setQuestionType(questionType);
			tmp.setQuestionStep(questionStep);
			tmp.buildOptions();
			questionTemplate = tmp;
		} else if (questionType == QuestionType.COMPLEX) {
			// TODO ...
		}
		questionTemplate.setTitleArr(buildTitleArray(questionTemplateDO
				.getTitle()));
		questionTemplate.setImageForTitle(buildImageForTitle(questionTemplateDO
				.getImage()));
		return questionTemplate;
	}

	private String[] buildImageForTitle(String image) {
		return StringUtil.split(image, ";");
	}

	/**
	 * 构建标题数组，包含文字、图片
	 * @param title  警示图&表示
	 * @return {"警示图", "&", "表示"}
	 */
	private String[] buildTitleArray(String title) {
		if (StringUtil.isNotBlank(title)) {
			List<String> tmpList = new ArrayList<String>();
			int tmpPos = 0;
			do {
				int imageIndex = title.indexOf(QuestionTemplate.IMAGE_SYMBOL,
						tmpPos);
				if (imageIndex != -1) {
					String text = title.substring(tmpPos, imageIndex);
					tmpList.add(text);
					tmpList.add(QuestionTemplate.IMAGE_SYMBOL);
					tmpPos = imageIndex + 1;
				} else {
					String text = title.substring(tmpPos);
					tmpList.add(text);
					break;
				}
			} while (tmpPos < title.length());
			return tmpList.toArray(new String[] {});
		}
		return null;
	}

	/**
	 * 将db字段中校验器字符串转换成对应每个空格的校验器
	 * 
	 * @param validator digit:0-100|-|digit:99-199
	 * @return
	 */
	private String[] buildBlankValidations(String validator) {
		if (StringUtil.isNotBlank(validator)) {
			String[] split = StringUtil.split(validator, "|");
			if (split != null) {
				String[] result = new String[split.length];
				for (int i = 0; i < split.length; i++) {
					String one = split[i];
					if (StringUtil.equals("-", one) || StringUtil.isBlank(one)) {
						result[i] = null;
					} else {
						result[i] = one;
					}
				}
				return result;
			}
		}
		return null;
	}

	@Override
	public QuestionTemplate findById(int questionTemplateId) {
		QuestionTemplateDO findById = this.questionTemplateDAO
				.findById(questionTemplateId);
		QuestionaireTemplateDO questionaireTemplateDO = this.questionaireTemplateDAO
				.findById(findById.getQuestionaireTemplateId());
		String questionStepDescription = questionaireTemplateDO
				.getQuestionSteps();
		return this.buildQuestionTemplate(findById, questionStepDescription);
	}

}
