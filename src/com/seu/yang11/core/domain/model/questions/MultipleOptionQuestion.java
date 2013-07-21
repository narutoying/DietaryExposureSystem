package com.seu.yang11.core.domain.model.questions;

import com.seu.yang11.core.domain.model.QuestionAnswer;

/**
 * ��ѡ��
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:00
 */
public class MultipleOptionQuestion extends OptionQuestion {

	public static final String ANSWER_SPLIT_SYMBOL = "$";

	public MultipleOptionQuestion() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * �����û�����Ĵ�
	 */
	public QuestionAnswer buildAnswer(String[] answer) {
		if (answer != null && answer.length > 0) {
			QuestionAnswer result = new QuestionAnswer();
			String answerStr = "";
			for (String oneAnswer : answer) {
				answerStr += oneAnswer;
				answerStr += ANSWER_SPLIT_SYMBOL;
			}
			answerStr = answerStr.substring(0, answerStr.length() - 1);
			result.setAnswer(answerStr);
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