package com.seu.yang11.core.domain.model;

import com.seu.yang11.core.domain.model.enums.QuestionStep;
import com.seu.yang11.core.domain.model.enums.QuestionType;

/**
 * �ʾ��е�����ģ��
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:01
 */
public abstract class QuestionTemplate {
	
	/** ͼƬռλ�� */
	public static final String IMAGE_SYMBOL = "&";

	private int id;

	/**
	 * ��Ŀ��ţ���1��ʼ��ÿ�����͵���Ŀ��Ŷ����໥�����
	 */
	protected int index;
	/**
	 * ���
	 */
	protected String title;
	/**
	 * ��Ŀ
	 */
	protected String content;
	/**
	 * ��һ����Ŀģ��id
	 */
	protected int nextQuestionTemplateId;
	/**
	 * �����ʾ�ģ��id
	 */
	public int questionnaireTemplateId;
	/**
	 * ������������
	 */
	public QuestionStep questionStep;
	/**
	 * ��������
	 */
	public QuestionType QuestionType;

	/** ��������ͼƬ */
	protected String[] imageForTitle;
	/** �������飬�����ı���ͼƬ */
	protected String[] titleArr;

	public QuestionTemplate() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * �����û�����Ĵ�
	 */
	public abstract QuestionAnswer buildAnswer(String[] answer);

	/**
	 * ������Ŀ���(QuestionStep.stepNum-index)���磺
	 * 0-1,1-2...
	 * 
	 * @return
	 */
	public String buildQuestionNumber() {
		return this.questionStep.getStepNum() + "-" + this.index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public QuestionStep getQuestionStep() {
		return questionStep;
	}

	public void setQuestionStep(QuestionStep questionaireStep) {
		questionStep = questionaireStep;
	}

	public QuestionType getQuestionType() {
		return QuestionType;
	}

	public void setQuestionType(QuestionType questionType) {
		QuestionType = questionType;
	}

	public int getQuestionnaireTemplateId() {
		return questionnaireTemplateId;
	}

	public void setQuestionnaireTemplateId(int questionnaireTemplateId) {
		this.questionnaireTemplateId = questionnaireTemplateId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNextQuestionTemplateId() {
		return nextQuestionTemplateId;
	}

	public void setNextQuestionTemplateId(int nextQuestionTemplateId) {
		this.nextQuestionTemplateId = nextQuestionTemplateId;
	}

	public String[] getImageForTitle() {
		return imageForTitle;
	}

	public void setImageForTitle(String[] imageForTitle) {
		this.imageForTitle = imageForTitle;
	}

	public String[] getTitleArr() {
		return titleArr;
	}

	public void setTitleArr(String[] titleArr) {
		this.titleArr = titleArr;
	}

}