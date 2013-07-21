/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.domain.model.questions;

/**
 * ѡ�����Ӧ��ѡ��
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: Option.java, v 0.1 2012-4-24 ����1:14:08 narutoying Exp $
 */
public class Option {
	/**
	 * ����һ��ѡ���⣺
	 * ��ϲ����ʲô��?
	 * a. �������� (1)
	 * b. С����Ģ�� (2)
	 * c. �Ǵ��Ź�(3)
	 * d. ��˺���� (4)
	 * �������е�һ��ѡ����˵��symbolName="a", index = 1, content = "��������"
	 */
	private String symbolName;
	private int index; // ��1��ʼ
	private String content;
	/** ��һ����ţ��������Ų���ֻ����ģ��id��������Ŀ�������ʾ����Ŀ�е���ţ���0��ʼ����ʱֻ�е�ѡ/�жϻ�ʹ�� */
	private Integer nextQuestionIndex = null;

	public String getSymbolName() {
		return symbolName;
	}

	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getNextQuestionIndex() {
		return nextQuestionIndex;
	}

	public void setNextQuestionIndex(Integer nextQuestionIndex) {
		this.nextQuestionIndex = nextQuestionIndex;
	}

}
