package com.seu.yang11.core.domain.model.questions;

import java.util.ArrayList;
import java.util.List;

import com.seu.yang11.common.util.StringUtil;
import com.seu.yang11.core.domain.model.QuestionAnswer;
import com.seu.yang11.core.domain.model.QuestionTemplate;

/**
 * 选择题
 * @author narutoying
 * @version 1.0
 * @created 21-四月-2012 23:41:00
 */
public abstract class OptionQuestion extends QuestionTemplate {

	protected List<Option> options = new ArrayList<Option>();

	// 例如a@宫保鸡丁|b@小鸡炖蘑菇
	public static String OPTION_DELIMETER = "|"; // 选项间
	public static String OPTION_INNER_DELIMETER = "@"; // 选项内

	public OptionQuestion() {

	}

	/**
	 * 构造选项，需要在setContent方法后使用
	 */
	public void buildOptions() {
		// "a@宫保鸡丁@1|b@小鸡炖蘑菇@2|c@糖醋排骨@3|d@手撕包菜@4"
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
	 * 保存用户填入的答案
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