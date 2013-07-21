package com.seu.yang11.core.domain.model.questions;

import com.seu.yang11.core.domain.model.QuestionAnswer;

/**
 * 单选题
 * @author narutoying
 * @version 1.0
 * @created 21-四月-2012 23:41:01
 */
public class SingleOptionQuestion extends OptionQuestion {
	
	public SingleOptionQuestion() {
		
	}

	/**
	 * 保存用户填入的答案
	 */
	public QuestionAnswer buildAnswer(String[] answer) {
		if (answer != null && answer.length == 1) {
			QuestionAnswer result = new QuestionAnswer();
			result.setAnswer(answer[0]);
			result.setQuestionTemplate(this);
			return result;
		}
		return null;
	}

	@Override
	public void buildOptions() {
		super.buildOptions();
	}
	
}