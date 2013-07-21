/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.dataobject;

/**
 * ����ģ��DO����
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionTemplate.java, v 0.1 2012-4-25 ����11:35:22 narutoying Exp $
 */
public class QuestionTemplateDO {

	private int id;
	/**
	 * �����ʾ�ģ��
	 */
	private int questionaireTemplateId;

	/**
	 * ��Ŀ��ţ���1��ʼ��ÿ�����͵���Ŀ��Ŷ����໥�����
	 */
	private int qIndex;
	/**
	 * ���
	 */
	private String title;
	/**
	 * ��Ŀ
	 */
	private String content;
	/**
	 * �������ⲽ��
	 */
	private int step;
	/**
	 * ��������
	 */
	private int type;
	/**
	 * У����
	 */
	private String validator;
	/** ͼƬid */
	private String image;

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

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionaireTemplateId() {
		return questionaireTemplateId;
	}

	public void setQuestionaireTemplateId(int questionaireTemplateId) {
		this.questionaireTemplateId = questionaireTemplateId;
	}

	public int getqIndex() {
		return qIndex;
	}

	public void setqIndex(int qIndex) {
		this.qIndex = qIndex;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
