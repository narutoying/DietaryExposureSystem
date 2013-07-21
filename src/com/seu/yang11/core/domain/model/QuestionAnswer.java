package com.seu.yang11.core.domain.model;

/**
 * 一个问题模板对应的答案
 * @author narutoying
 * @version 1.0
 * @created 21-四月-2012 23:41:00
 */
public class QuestionAnswer {

	/**
	 * 题目答案，可以是填空题的文本型答案，选择题的选项(A/B/C/D),判断题的对/错
	 */
	private String answer;
	private int questionaireId;
	public QuestionTemplate QuestionTemplate;

	public QuestionAnswer() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 在界面上展示题目与答案
	 */
	public void show() {

	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getQuestionaireId() {
		return questionaireId;
	}

	public void setQuestionaireId(int questionaireId) {
		this.questionaireId = questionaireId;
	}

	public QuestionTemplate getQuestionTemplate() {
		return QuestionTemplate;
	}

	public void setQuestionTemplate(QuestionTemplate questionTemplate) {
		QuestionTemplate = questionTemplate;
	}

}