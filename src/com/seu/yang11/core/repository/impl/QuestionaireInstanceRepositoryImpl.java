/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;

import com.seu.yang11.common.dal.dao.QuestionaireInstanceDAO;
import com.seu.yang11.common.dal.dataobject.QuestionaireInstanceDO;
import com.seu.yang11.core.domain.model.QuestionaireBasicInfo;
import com.seu.yang11.core.domain.model.QuestionaireCompleteSign;
import com.seu.yang11.core.domain.model.QuestionaireInstance;
import com.seu.yang11.core.domain.model.QuestionaireTemplate;
import com.seu.yang11.core.repository.QuestionAnswerRepository;
import com.seu.yang11.core.repository.QuestionaireInstanceRepository;
import com.seu.yang11.core.repository.QuestionaireTemplateRepository;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireInstanceRepositoryImpl.java, v 0.1 2012-4-22 下午3:48:01 narutoying Exp $
 */
public class QuestionaireInstanceRepositoryImpl implements
		QuestionaireInstanceRepository {

	private Context context;
	private QuestionaireInstanceDAO questionaireInstanceDAO;
	private QuestionaireTemplateRepository questionaireTemplateRepository;
	private QuestionAnswerRepository questionAnswerRepository;

	public QuestionaireInstanceRepositoryImpl() {
		super();
	}

	public QuestionaireInstanceRepositoryImpl(Context context) {
		super();
		this.context = context;
		this.questionaireInstanceDAO = new QuestionaireInstanceDAO(context);
		this.questionaireTemplateRepository = new QuestionaireTemplateRepositoryImpl(
				context);
		this.questionAnswerRepository = new QuestionAnswerRepositoryImpl(
				context);
	}

	/** 
	 * @see com.seu.yang11.core.repository.QuestionaireInstanceRepository#createInstance(com.seu.yang11.core.domain.model.QuestionaireTemplate)
	 */
	@Override
	public QuestionaireInstance createInstance(
			QuestionaireTemplate questionnaireTemplate) {
		QuestionaireInstance instance = new QuestionaireInstance();
		// 获取自增id
		QuestionaireInstanceDO questionaireInstanceDO = new QuestionaireInstanceDO();
		questionaireInstanceDO.setTemplateId(questionnaireTemplate.getId());
		int newId = this.questionaireInstanceDAO.insert(questionaireInstanceDO);
		instance.setId(newId);
		instance.setQuestionnaireTemplate(questionnaireTemplate);
		return instance;
	}

	/** 
	 * @see com.seu.yang11.core.repository.QuestionaireInstanceRepository#updateInstance(com.seu.yang11.core.domain.model.QuestionaireInstance)
	 */
	@Override
	public void updateInstance(QuestionaireInstance questionnaireInstance) {
		QuestionaireInstanceDO questionaireInstanceDO = new QuestionaireInstanceDO();
		questionaireInstanceDO.setId(questionnaireInstance.getId());
		questionaireInstanceDO.setTemplateId(questionnaireInstance
				.getQuestionnaireTemplate().getId());
		QuestionaireBasicInfo basicInfo = questionnaireInstance
				.getQuestionnaireBasicInfo();
		if (basicInfo != null) {
			questionaireInstanceDO.setNumber(basicInfo.getNumber());
			questionaireInstanceDO.setProvince(basicInfo.getProvince());
			questionaireInstanceDO.setCity(basicInfo.getCity());
			questionaireInstanceDO.setZone(basicInfo.getZone());
			questionaireInstanceDO.setStreet(basicInfo.getStreet());
			questionaireInstanceDO.setVillageCommittee(basicInfo
					.getVillageCommittee());
			questionaireInstanceDO.setDoorNumber(basicInfo.getDoorNumber());
			questionaireInstanceDO.setOrganization(basicInfo.getOrganization());
			questionaireInstanceDO.setSurveyedPersonCellphone(basicInfo
					.getSurveyedPersonCellphone());
			questionaireInstanceDO.setSurveyedPersonId(basicInfo
					.getSurveyedPersonId());
			questionaireInstanceDO.setSurveyedPersonName(basicInfo
					.getSurveyedPersonName());
			questionaireInstanceDO.setSurveyLocation(basicInfo
					.getSurveyLocation());
		}
		QuestionaireCompleteSign completeSign = questionnaireInstance
				.getQuestionaireCompleteSign();
		if (completeSign != null) {
			questionaireInstanceDO.setSurveyPerson(completeSign
					.getSurveyPerson());
			questionaireInstanceDO
					.setCheckPerson(completeSign.getCheckPerson());
			questionaireInstanceDO.setSurveyStartTime(completeSign
					.getSurveyStartTime());
			questionaireInstanceDO.setSurveyEndTime(completeSign
					.getSurveyEndTime());
			questionaireInstanceDO.setSurveyDate(completeSign
					.getSurveyEndTime());
		}
		this.questionaireInstanceDAO.update(questionaireInstanceDO);
	}

	/** 
	 * @see com.seu.yang11.core.repository.QuestionaireInstanceRepository#getInstanceById(java.lang.String)
	 */
	@Override
	public QuestionaireInstance getInstanceById(String id) {
		return null;
	}

	/** 
	 * @see com.seu.yang11.core.repository.QuestionaireInstanceRepository#getAllInstances()
	 */
	@Override
	public List<QuestionaireInstance> getAllInstances() {
		List<QuestionaireInstanceDO> allQuestionaireInstanceDOs = this.questionaireInstanceDAO
				.getAll();
		if (allQuestionaireInstanceDOs != null
				&& allQuestionaireInstanceDOs.size() > 0) {
			List<QuestionaireInstance> result = new ArrayList<QuestionaireInstance>();
			for (QuestionaireInstanceDO questionaireInstanceDO : allQuestionaireInstanceDOs) {
				QuestionaireInstance questionaireInstance = new QuestionaireInstance();
				questionaireInstance.setId(questionaireInstanceDO.getId());
				QuestionaireCompleteSign sign = new QuestionaireCompleteSign();
				sign.setCheckPerson(questionaireInstanceDO.getCheckPerson());
				sign.setSurveyPerson(questionaireInstanceDO.getSurveyPerson());
				sign.setSurveyStartTime(questionaireInstanceDO
						.getSurveyStartTime());
				sign.setSurveyEndTime(questionaireInstanceDO.getSurveyEndTime());
				questionaireInstance.setQuestionaireCompleteSign(sign);
				QuestionaireBasicInfo questionnaireBasicInfo = buildBasicInfo(questionaireInstanceDO);
				questionaireInstance
						.setQuestionnaireBasicInfo(questionnaireBasicInfo);
				questionaireInstance
						.setQuestionnaireTemplate(this.questionaireTemplateRepository
								.getDefaultTemplate());
				questionaireInstance
						.setQuestionAnswer(this.questionAnswerRepository
								.getAnswersByQuestionaireId(questionaireInstanceDO
										.getId()));
				result.add(questionaireInstance);
			}
			return result;
		}
		return Collections.emptyList();
	}

	private QuestionaireBasicInfo buildBasicInfo(
			QuestionaireInstanceDO questionaireInstanceDO) {
		QuestionaireBasicInfo questionnaireBasicInfo = new QuestionaireBasicInfo();
		questionnaireBasicInfo
				.setProvince(questionaireInstanceDO.getProvince());
		questionnaireBasicInfo.setCity(questionaireInstanceDO.getCity());
		questionnaireBasicInfo.setZone(questionaireInstanceDO.getZone());
		questionnaireBasicInfo.setStreet(questionaireInstanceDO.getStreet());
		questionnaireBasicInfo.setVillageCommittee(questionaireInstanceDO
				.getVillageCommittee());
		questionnaireBasicInfo.setDoorNumber(questionaireInstanceDO
				.getDoorNumber());
		questionnaireBasicInfo.setNumber(questionaireInstanceDO.getNumber());
		questionnaireBasicInfo.setOrganization(questionaireInstanceDO
				.getOrganization());
		questionnaireBasicInfo.setSurveyLocation(questionaireInstanceDO
				.getSurveyLocation());
		questionnaireBasicInfo
				.setSurveyedPersonCellphone(questionaireInstanceDO
						.getSurveyedPersonCellphone());
		questionnaireBasicInfo.setSurveyedPersonId(questionaireInstanceDO
				.getSurveyedPersonId());
		questionnaireBasicInfo.setSurveyedPersonName(questionaireInstanceDO
				.getSurveyedPersonName());
		return questionnaireBasicInfo;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
