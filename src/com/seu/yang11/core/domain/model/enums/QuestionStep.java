package com.seu.yang11.core.domain.model.enums;

/**
 * һ���������ڵĲ��裬ֻ��ע���Ȿ�����漰���������ʾ����������
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:00
 */
public class QuestionStep {

	/** ������� */
	private int stepNum;
	private String name;

	public QuestionStep(String name, int stepNum) {
		this.stepNum = stepNum;
		this.name = name;
	}

	/**
	 * ��ϲ�����������ÿ�������Ӧ��ѡ����룬�統ǰ�������ڲ���1�����������8���򷵻ؽ��Ϊ108
	 * 
	 * @param questionInnerIndex
	 * @param stepNum
	 */
	public static String buildQuestionCode(int questionInnerIndex, int stepNum) {
		return "";
	}
	
	public static QuestionStep buildQuestionStep(int step, String name) {
		return null;
	}

	public int getStepNum() {
		return stepNum;
	}

	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}