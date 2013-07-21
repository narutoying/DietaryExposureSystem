package com.seu.yang11.core.domain.model.questions;

import com.seu.yang11.core.domain.model.QuestionAnswer;

/**
 * 判断题
 * 只需提供title，content自动填充为"是|否"
 * @author narutoying
 * @version 1.0
 * @created 21-四月-2012 23:41:01
 */
public class TFQuestion extends OptionQuestion {

	public TFQuestion() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 保存用户填入的答案
	 */
	public QuestionAnswer buildAnswer(String[] answer) {
		if(answer != null && answer.length == 1) {
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