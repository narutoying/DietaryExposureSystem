package com.seu.yang11.core.domain.model;

import java.util.Date;

/**
 * 调查问卷完成后的签名相关
 * @author narutoying
 * @version 1.0
 * @created 21-四月-2012 22:18:41
 */
public class QuestionaireCompleteSign {

	/** 调查员 */
	private String surveyPerson;
	/** 调查开始时间 */
	private Date surveyStartTime;
	/** 调查结束时间 */
	private Date surveyEndTime;
	/** 监督员 */
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