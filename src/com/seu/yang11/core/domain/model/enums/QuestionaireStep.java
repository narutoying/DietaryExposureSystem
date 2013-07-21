/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.domain.model.enums;

/**
 * 整个程序进行一次问卷调查所经历的生命周期步骤
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireStep.java, v 0.1 2012-4-22 下午9:23:56 narutoying Exp $
 */
public enum QuestionaireStep {
	INIT(0, "初始化"), BASIC_INFO(1, "基本信息录入"), QUESTIONS(2, "问题结果录入"), COMPLETE(
			3, "问卷录入完成"), POST_HANDLE(4, "后处理"), TERMINATE(5, "终结");

	QuestionaireStep(int step, String name) {
		this.step = step;
		this.name = name;
	}

	int step;
	String name;
}
