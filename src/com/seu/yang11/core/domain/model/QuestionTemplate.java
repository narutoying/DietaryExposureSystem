package com.seu.yang11.core.domain.model;

import com.seu.yang11.core.domain.model.enums.QuestionStep;
import com.seu.yang11.core.domain.model.enums.QuestionType;

/**
 * 问卷中的问题模板
 * @author narutoying
 * @version 1.0
 * @created 21-四月-2012 23:41:01
 */
public abstract class QuestionTemplate {
	
	/** 图片占位符 */
	public static final String IMAGE_SYMBOL = "&";

	private int id;

	/**
	 * 题目序号，从1开始，每种类型的题目序号都是相互隔离的
	 */
	protected int index;
	/**
	 * 题干
	 */
	protected String title;
	/**
	 * 题目
	 */
	protected String content;
	/**
	 * 下一个题目模板id
	 */
	protected int nextQuestionTemplateId;
	/**
	 * 所属问卷模板id
	 */
	public int questionnaireTemplateId;
	/**
	 * 问题所属步骤
	 */
	public QuestionStep questionStep;
	/**
	 * 问题类型
	 */
	public QuestionType QuestionType;

	/** 标题所用图片 */
	protected String[] imageForTitle;
	/** 标题数组，包含文本与图片 */
	protected String[] titleArr;

	public QuestionTemplate() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 保存用户填入的答案
	 */
	public abstract QuestionAnswer buildAnswer(String[] answer);

	/**
	 * 构造题目编号(QuestionStep.stepNum-index)，如：
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