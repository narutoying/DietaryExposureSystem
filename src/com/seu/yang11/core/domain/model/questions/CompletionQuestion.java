package com.seu.yang11.core.domain.model.questions;

import java.util.ArrayList;
import java.util.List;

import com.seu.yang11.common.util.StringUtil;
import com.seu.yang11.core.domain.model.QuestionAnswer;
import com.seu.yang11.core.domain.model.QuestionTemplate;

/**
 * �����
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:00
 */
public class CompletionQuestion extends QuestionTemplate {

	private String[] blanks;
	/** �հ״���У������ */
	private String[] blankValidations;
	/** ������������ı����ֺͿո񲿷� */
	private List<Object> segments = new ArrayList<Object>();
	public static final String BLANK_FLAG = "$";

	public CompletionQuestion() {
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
				answerStr += BLANK_FLAG;
			}
			answerStr = answerStr.substring(0, answerStr.length() - 1);
			result.setAnswer(answerStr);
			result.setQuestionTemplate(this);
			return result;
		}
		return null;
	}

	public String[] getBlanks() {
		return blanks;
	}

	public void setBlanks(String[] blanks) {
		this.blanks = blanks;
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
	}
	
	/**
	 * �����հ�����
	 */
	public void buildBlanks() {
		int countMatches = StringUtil.countMatches(title, BLANK_FLAG);
		if (countMatches > 0) {
			blanks = new String[countMatches];
		}
		/*
		 * ��ʼ���ı��Ϳո�
		 */
		int tmpPos = 0;
		int indexOfBlank = 0;
		do {
			int blankIndex = title.indexOf("$", tmpPos);
			if (blankIndex != -1) {
				String text = title.substring(tmpPos, blankIndex);
				this.segments.add(text);
				this.segments.add(new QuestionBlank(indexOfBlank, buildBlankValidator(indexOfBlank)));
				indexOfBlank++;
				tmpPos = blankIndex + 1;
			} else {
				String text = title.substring(tmpPos);
				this.segments.add(text);
				break;
			}
		} while (tmpPos < title.length());
	}

	/**
	 * ���ݿո�����λ�ù�����֤��
	 * 
	 * @param indexOfBlank
	 * @return
	 */
	private String buildBlankValidator(int indexOfBlank) {
		if(this.blankValidations != null) {
			if(indexOfBlank <= blankValidations.length - 1) {
				return blankValidations[indexOfBlank];
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public class QuestionBlank {
		private int index;
		private String validator;

		public QuestionBlank(int index, String validator) {
			super();
			this.index = index;
			this.validator = validator;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getValidator() {
			return validator;
		}

		public void setValidator(String validator) {
			this.validator = validator;
		}
		
		@Override
		public String toString() {
			return this.index + "@" + this.validator;
		}

	}

	public List<Object> getSegments() {
		return segments;
	}

	public void setSegments(List<Object> segments) {
		this.segments = segments;
	}

	public String[] getBlankValidations() {
		return blankValidations;
	}

	public void setBlankValidations(String[] blankValidations) {
		this.blankValidations = blankValidations;
	}
	
}