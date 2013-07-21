/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.domain.model.questions;

/**
 * 选择题对应的选项
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: Option.java, v 0.1 2012-4-24 上午1:14:08 narutoying Exp $
 */
public class Option {
	/**
	 * 例如一道选择题：
	 * 你喜欢吃什么菜?
	 * a. 宫保鸡丁 (1)
	 * b. 小鸡炖蘑菇 (2)
	 * c. 糖醋排骨(3)
	 * d. 手撕包菜 (4)
	 * 对于其中第一个选项来说，symbolName="a", index = 1, content = "宫保鸡丁"
	 */
	private String symbolName;
	private int index; // 从1开始
	private String content;
	/** 下一题序号，这里的序号不是只问题模板id，而是题目在整个问卷的题目中的序号（从0开始）暂时只有单选/判断会使用 */
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
