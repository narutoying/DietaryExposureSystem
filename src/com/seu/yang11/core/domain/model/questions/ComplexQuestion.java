package com.seu.yang11.core.domain.model.questions;

import com.seu.yang11.core.domain.model.QuestionAnswer;
import com.seu.yang11.core.domain.model.QuestionTemplate;

/**
 * ���ӵġ����淶���⣬��������������ѡ��ģ���������ģ�
 * 
 * </br>
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:00
 */
public class ComplexQuestion extends QuestionTemplate {

	public ComplexQuestion(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * �����û�����Ĵ�
	 */
	public QuestionAnswer buildAnswer(String[] answer){
		return null;
	}

}