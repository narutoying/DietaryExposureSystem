package com.seu.yang11.core.domain.model.enums;

/**
 * ��Ŀ����
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:01
 */
public enum QuestionType {
	COMPLETION(1, "�����"), TF(2, "�ж���"), SINGLE_CHOICE(3, "��ѡ��"), MULIPLE_CHOICE(
			4, "��ѡ��"), COMPLEX(5, "������");

	QuestionType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	private int id;
	private String name;

	public static QuestionType getQuestionType(int typeId) {
		if (typeId == 1) {
			return COMPLETION;
		} else if (typeId == 2) {
			return TF;
		} else if (typeId == 3) {
			return SINGLE_CHOICE;
		} else if (typeId == 4) {
			return MULIPLE_CHOICE;
		} else if (typeId == 5) {
			return COMPLEX;
		}
		return null;
	}
}