package com.seu.yang11.core.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * �ʾ�ʵ����һ���û�ѡ����һ���ʾ�ģ�岢��ʼ¼���ʾ��ͻ����һ���ʾ�ʵ��
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:01
 */
public class QuestionaireInstance {

	/**
	 * ��Ӧ���ݿ�id
	 */
	private int id;

	private List<QuestionAnswer> QuestionAnswer = new ArrayList<QuestionAnswer>();
	private QuestionaireTemplate QuestionnaireTemplate;
	private QuestionaireBasicInfo QuestionnaireBasicInfo;
	private QuestionaireCompleteSign questionaireCompleteSign;

	public QuestionaireInstance() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<QuestionAnswer> getQuestionAnswer() {
		return QuestionAnswer;
	}

	public void setQuestionAnswer(List<QuestionAnswer> questionAnswer) {
		QuestionAnswer = questionAnswer;
	}

	public QuestionaireBasicInfo getQuestionnaireBasicInfo() {
		return QuestionnaireBasicInfo;
	}

	public void setQuestionnaireBasicInfo(
			QuestionaireBasicInfo questionnaireBasicInfo) {
		QuestionnaireBasicInfo = questionnaireBasicInfo;
	}

	public QuestionaireTemplate getQuestionnaireTemplate() {
		return QuestionnaireTemplate;
	}

	public void setQuestionnaireTemplate(
			QuestionaireTemplate questionnaireTemplate) {
		QuestionnaireTemplate = questionnaireTemplate;
	}

	public QuestionaireCompleteSign getQuestionaireCompleteSign() {
		return questionaireCompleteSign;
	}

	public void setQuestionaireCompleteSign(
			QuestionaireCompleteSign questionaireCompleteSign) {
		this.questionaireCompleteSign = questionaireCompleteSign;
	}

}