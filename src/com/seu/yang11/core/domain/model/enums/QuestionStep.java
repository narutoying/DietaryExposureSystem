package com.seu.yang11.core.domain.model.enums;

/**
 * 一个问题所在的步骤，只关注问题本身，不涉及整个调查问卷的生命周期
 * @author narutoying
 * @version 1.0
 * @created 21-四月-2012 23:41:00
 */
public class QuestionStep {

	/** 步骤序号 */
	private int stepNum;
	private String name;

	public QuestionStep(String name, int stepNum) {
		this.stepNum = stepNum;
		this.name = name;
	}

	/**
	 * 结合步骤数来返回每个问题对应的选项编码，如当前问题属于步骤1，问题序号是8，则返回结果为108
	 * 
	 * @param questionInnerIndex
	 * @param stepNum
	 */
	public static String buildQuestionCode(int questionInnerIndex, int stepNum) {
		return "";
	}
	
	public static QuestionStep buildQuestionStep(int step, String name) {
		return null;
	}

	public int getStepNum() {
		return stepNum;
	}

	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}