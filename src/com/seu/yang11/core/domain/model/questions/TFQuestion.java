package com.seu.yang11.core.domain.model.questions;

import com.seu.yang11.core.domain.model.QuestionAnswer;

/**
 * �ж���
 * ֻ���ṩtitle��content�Զ����Ϊ"��|��"
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:01
 */
public class TFQuestion extends OptionQuestion {

	public TFQuestion() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * �����û�����Ĵ�
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