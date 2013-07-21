/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.dataobject;

/**
 * 问题模板DO对象
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionTemplate.java, v 0.1 2012-4-25 下午11:35:22 narutoying Exp $
 */
public class QuestionTemplateDO {

	private int id;
	/**
	 * 所属问卷模板
	 */
	private int questionaireTemplateId;

	/**
	 * 题目序号，从1开始，每种类型的题目序号都是相互隔离的
	 */
	private int qIndex;
	/**
	 * 题干
	 */
	private String title;
	/**
	 * 题目
	 */
	private String content;
	/**
	 * 所属问题步骤
	 */
	private int step;
	/**
	 * 问题类型
	 */
	private int type;
	/**
	 * 校验器
	 */
	private String validator;
	/** 图片id */
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
