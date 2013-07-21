package com.seu.yang11.core.domain.model.questions;

import com.seu.yang11.core.domain.model.QuestionAnswer;
import com.seu.yang11.core.domain.model.QuestionTemplate;

/**
 * 复杂的、不规范的题，比如既有填空又有选择的，比如下面的：
 * 
 * </br>
 * @author narutoying
 * @version 1.0
 * @created 21-四月-2012 23:41:00
 */
public class ComplexQuestion extends QuestionTemplate {

	public ComplexQuestion(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 保存用户填入的答案
	 */
	public QuestionAnswer buildAnswer(String[] answer){
		return null;
	}

}