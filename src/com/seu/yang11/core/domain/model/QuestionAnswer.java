package com.seu.yang11.core.domain.model;

/**
 * һ������ģ���Ӧ�Ĵ�
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:00
 */
public class QuestionAnswer {

	/**
	 * ��Ŀ�𰸣��������������ı��ʹ𰸣�ѡ�����ѡ��(A/B/C/D),�ж���Ķ�/��
	 */
	private String answer;
	private int questionaireId;
	public QuestionTemplate QuestionTemplate;

	public QuestionAnswer() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * �ڽ�����չʾ��Ŀ���
	 */
	public void show() {

	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getQuestionaireId() {
		return questionaireId;
	}

	public void setQuestionaireId(int questionaireId) {
		this.questionaireId = questionaireId;
	}

	public QuestionTemplate getQuestionTemplate() {
		return QuestionTemplate;
	}

	public void setQuestionTemplate(QuestionTemplate questionTemplate) {
		QuestionTemplate = questionTemplate;
	}

}