package com.seu.yang11.core.domain.model;

import java.util.Date;

/**
 * �����ʾ���ɺ��ǩ�����
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 22:18:41
 */
public class QuestionaireCompleteSign {

	/** ����Ա */
	private String surveyPerson;
	/** ���鿪ʼʱ�� */
	private Date surveyStartTime;
	/** �������ʱ�� */
	private Date surveyEndTime;
	/** �ලԱ */
	private String checkPerson;

	public QuestionaireCompleteSign() {

	}

	public String getSurveyPerson() {
		return surveyPerson;
	}

	public void setSurveyPerson(String surveyPerson) {
		this.surveyPerson = surveyPerson;
	}

	public Date getSurveyStartTime() {
		return surveyStartTime;
	}

	public void setSurveyStartTime(Date surveyStartTime) {
		this.surveyStartTime = surveyStartTime;
	}

	public Date getSurveyEndTime() {
		return surveyEndTime;
	}

	public void setSurveyEndTime(Date surveyEndTime) {
		this.surveyEndTime = surveyEndTime;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

}