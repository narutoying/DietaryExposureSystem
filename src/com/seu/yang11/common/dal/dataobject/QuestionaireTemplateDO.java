/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.dataobject;

import java.util.Date;

/**
 * �ʾ�ģ��DO����
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireTemplateDO.java, v 0.1 2012-4-25 ����11:35:02 narutoying Exp $
 */
public class QuestionaireTemplateDO {
	private int id;
	private String name;
	private Date createTime; // ��ѡ
	/** �ʾ����� */
	private String description;
	/** ���ⲽ�� */
	private String questionSteps;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestionSteps() {
		return questionSteps;
	}

	public void setQuestionSteps(String questionSteps) {
		this.questionSteps = questionSteps;
	}

}
