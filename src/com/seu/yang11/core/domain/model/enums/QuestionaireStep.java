/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.domain.model.enums;

/**
 * �����������һ���ʾ�������������������ڲ���
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireStep.java, v 0.1 2012-4-22 ����9:23:56 narutoying Exp $
 */
public enum QuestionaireStep {
	INIT(0, "��ʼ��"), BASIC_INFO(1, "������Ϣ¼��"), QUESTIONS(2, "������¼��"), COMPLETE(
			3, "�ʾ�¼�����"), POST_HANDLE(4, "����"), TERMINATE(5, "�ս�");

	QuestionaireStep(int step, String name) {
		this.step = step;
		this.name = name;
	}

	int step;
	String name;
}
