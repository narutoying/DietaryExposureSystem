/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.dataobject;

/**
 * 问题答案DO对象
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionAnswerDO.java, v 0.1 2012-4-25 下午11:35:36 narutoying Exp $
 */
public class QuestionAnswerDO {
	private int id;
	private String content;
	private int questionaireId;
	private int questionTemplateId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionaireId() {
		return questionaireId;
	}

	public void setQuestionaireId(int questionaireId) {
		this.questionaireId = questionaireId;
	}

	public int getQuestionTemplateId() {
		return questionTemplateId;
	}

	public void setQuestionTemplateId(int questionTemplateId) {
		this.questionTemplateId = questionTemplateId;
	}

}
