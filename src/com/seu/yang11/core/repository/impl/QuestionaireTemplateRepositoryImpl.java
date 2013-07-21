/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.repository.impl;

import java.util.Date;

import android.content.Context;

import com.seu.yang11.common.dal.dao.QuestionaireTemplateDAO;
import com.seu.yang11.common.dal.dataobject.QuestionaireTemplateDO;
import com.seu.yang11.core.domain.model.QuestionaireTemplate;
import com.seu.yang11.core.repository.QuestionTemplateRepository;
import com.seu.yang11.core.repository.QuestionaireTemplateRepository;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireTemplateRepositoryImpl.java, v 0.1 2012-4-22 下午3:24:54 narutoying Exp $
 */
public class QuestionaireTemplateRepositoryImpl implements
		QuestionaireTemplateRepository {

	private Context context;
	private QuestionaireTemplateDAO questionaireTemplateDAO;
	private QuestionTemplateRepository questionTemplateRepository;

	public QuestionaireTemplateRepositoryImpl() {
		super();
	}

	public QuestionaireTemplateRepositoryImpl(Context context) {
		super();
		this.context = context;
		this.questionaireTemplateDAO = new QuestionaireTemplateDAO(context);
		this.questionTemplateRepository = new QuestionTemplateRepositoryImpl(
				context);
	}

	/** 
	 * @see com.seu.yang11.core.repository.QuestionaireTemplateRepository#getDefaultTemplate()
	 */
	@Override
	public QuestionaireTemplate getDefaultTemplate() {
		QuestionaireTemplate defaultTemplate = new QuestionaireTemplate(1,
				"默认模板", new Date(), "默认模板，暂不支持模板选择");
		defaultTemplate.setQuestionTemplate(this.questionTemplateRepository
				.getByQuestionaireTemplateId(defaultTemplate.getId()));
		return defaultTemplate;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public QuestionaireTemplate getTemplateById(int id) {
		QuestionaireTemplateDO templateDO = questionaireTemplateDAO
				.findById(id);
		if (templateDO != null) {
			QuestionaireTemplate result = new QuestionaireTemplate(id,
					templateDO.getName(), templateDO.getCreateTime(),
					templateDO.getDescription());
			result.setQuestionTemplate(this.questionTemplateRepository
					.getByQuestionaireTemplateId(id));
			return result;
		} else {
			return null;
		}
	}

}
