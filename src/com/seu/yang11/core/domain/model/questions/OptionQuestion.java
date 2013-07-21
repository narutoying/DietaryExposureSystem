package com.seu.yang11.core.domain.model.questions;

import java.util.ArrayList;
import java.util.List;

import com.seu.yang11.common.util.StringUtil;
import com.seu.yang11.core.domain.model.QuestionAnswer;
import com.seu.yang11.core.domain.model.QuestionTemplate;

/**
 * ѡ����
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:00
 */
public abstract class OptionQuestion extends QuestionTemplate {

	protected List<Option> options = new ArrayList<Option>();

	// ����a@��������|b@С����Ģ��
	public static String OPTION_DELIMETER = "|"; // ѡ���
	public static String OPTION_INNER_DELIMETER = "@"; // ѡ����

	public OptionQuestion() {

	}

	/**
	 * ����ѡ���Ҫ��setContent������ʹ��
	 */
	public void buildOptions() {
		// "a@��������@1|b@С����Ģ��@2|c@�Ǵ��Ź�@3|d@��˺����@4"
		if (StringUtil.isNotBlank(content)) {
			String[] allOptionsArr = StringUtil
					.split(content, OPTION_DELIMETER);
			if (allOptionsArr != null && allOptionsArr.length > 0) {
				int index = 1;
				for (String oneOptionStr : allOptionsArr) {
					if (StringUtil.isNotBlank(oneOptionStr)) {
						String[] oneOptionArr = StringUtil.split(oneOptionStr,
								OPTION_INNER_DELIMETER);
						if (oneOptionArr != null && oneOptionArr.length >= 2) {
							Option option = new Option();
							option.setIndex(index++);
							option.setSymbolName(oneOptionArr[0]);
							option.setContent(oneOptionArr[1]);
							if (oneOptionArr.length == 3) {
								String gotoQuestionIndex = oneOptionArr[2];
								if (StringUtil.isNotBlank(gotoQuestionIndex)) {
									gotoQuestionIndex = StringUtil
											.trim(gotoQuestionIndex);
									Integer nextQuestionIndex = Integer
											.parseInt(gotoQuestionIndex);
									option.setNextQuestionIndex(nextQuestionIndex);
								}
							}
							this.options.add(option);
						}
					}
				}
			}
		}
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * �����û�����Ĵ�
	 */
	public QuestionAnswer buildAnswer(String[] answer) {
		return null;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

}