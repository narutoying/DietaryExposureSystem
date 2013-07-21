/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.seu.yang11.common.dal.dao.QuestionAnswerDAO;
import com.seu.yang11.common.dal.dataobject.QuestionAnswerDO;
import com.seu.yang11.core.domain.model.QuestionAnswer;
import com.seu.yang11.core.domain.model.QuestionTemplate;
import com.seu.yang11.core.repository.QuestionAnswerRepository;
import com.seu.yang11.core.repository.QuestionTemplateRepository;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionAnswerRepositoryImpl.java, v 0.1 2012-4-24 ����12:42:22 narutoying Exp $
 */
public class QuestionAnswerRepositoryImpl implements QuestionAnswerRepository {

	private Context context;

	private QuestionAnswerDAO questionAnswerDAO;
	private QuestionTemplateRepository questionTemplateRepository;

	public QuestionAnswerRepositoryImpl() {
		super();
	}

	public QuestionAnswerRepositoryImpl(Context context) {
		super();
		this.context = context;
		questionAnswerDAO = new QuestionAnswerDAO(context);
		questionTemplateRepository = new QuestionTemplateRepositoryImpl(context);
	}

	/** 
	 * @see com.seu.yang11.core.repository.QuestionAnswerRepository#saveQuestionAnswer(com.seu.yang11.core.domain.model.QuestionAnswer)
	 */
	@Override
	public void saveQuestionAnswer(QuestionAnswer answer) {
		int questionaireId = answer.getQuestionaireId();
		QuestionTemplate questionTemplate = answer.getQuestionTemplate();
		int questionTemplateId = questionTemplate.getId();
		QuestionAnswerDO questionAnswerDO = new QuestionAnswerDO();
		questionAnswerDO.setQuestionaireId(questionaireId);
		questionAnswerDO.setQuestionTemplateId(questionTemplateId);
		questionAnswerDO.setContent(answer.getAnswer());
		// 0. �жϸô��Ƿ�Ϊ�²���
		if (isFirstSave(answer)) {
			// 1.1 ����������ǵ�һ�β���
			this.questionAnswerDAO.insert(questionAnswerDO);
		} else {
			// 1.2 ��������𰸷ǵ�һ�β��룬���޸�
			this.questionAnswerDAO.update(questionAnswerDO);
		}
	}

	/**
	 * �ʾ�ʵ��id+����ģ��idΨһȷ��һ��answer
	 * 
	 * @param answer
	 * @return
	 */
	private boolean isFirstSave(QuestionAnswer answer) {
		QuestionAnswerDO answerDO = this.questionAnswerDAO.find(answer
				.getQuestionaireId(), answer.getQuestionTemplate().getId());
		if (answerDO == null) {
			return true;
		} else {
			return false;
		}
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * ��ȡָ���ʾ�Ĵ𰸣����ʾ��������Ŀ���������û�д�(��ת����)�Ķ��Ჹλ
	 */
	@Override
	public List<QuestionAnswer> getAnswersByQuestionaireId(int questionaireId) {
		List<QuestionAnswerDO> allqQuestionAnswerDOs = this.questionAnswerDAO
				.getAll(questionaireId);
		if (allqQuestionAnswerDOs != null && allqQuestionAnswerDOs.size() > 0) {
			List<QuestionAnswer> result = new ArrayList<QuestionAnswer>();
			Map<Integer, QuestionAnswerDO> allAnswerMap = this.questionAnswerDAO
					.getAllMap(questionaireId);
			// 1. ��ȡ���ʾ�ʵ����Ӧ�ʾ��������Ŀģ��id��Ȼ�����
			QuestionAnswerDO answerDO = allqQuestionAnswerDOs.get(0);
			int questionnaireTemplateId = this.questionTemplateRepository
					.findById(answerDO.getQuestionTemplateId())
					.getQuestionnaireTemplateId();
			List<QuestionTemplate> questionTemplateIdList = this.questionTemplateRepository
					.getByQuestionaireTemplateId(questionnaireTemplateId);
			// 2.1 ����allAnswerMap�л�ȡ����Ӧ�Ĵ𰸣���ת���ɶ�Ӧ������ģ��
			// 2.2 ��δ�ҵ������Ǵ���һ���𰸣�������Ϊ��
			for (QuestionTemplate questionTemplate : questionTemplateIdList) {
				int qId = questionTemplate.getId();
				QuestionAnswerDO tmp = allAnswerMap.get(qId);
				QuestionAnswer questionAnswer = new QuestionAnswer();
				questionAnswer.setQuestionaireId(questionaireId);
				questionAnswer
				.setQuestionTemplate(this.questionTemplateRepository
						.findById(qId));
				if (tmp != null) {
					questionAnswer.setAnswer(tmp.getContent());
				} else {
					questionAnswer.setAnswer("");
				}
				result.add(questionAnswer);
			}
			return result;
		}
		return Collections.emptyList();
	}

}
