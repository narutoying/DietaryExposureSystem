/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.convert;

import java.util.HashMap;
import java.util.Map;

import com.seu.yang11.common.util.StringUtil;
import com.seu.yang11.core.domain.model.enums.QuestionStep;

/**
 * ת����Ŀ����
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionStepConvertor.java, v 0.1 2012-7-4 ����12:14:52 narutoying Exp $
 */
public class QuestionStepConvertor {
	/**
	 * ��ȡ���ⲽ��
	 * 
	 * @param step ������
	 * @param stepDescription 0:���Բ���0|1:���Բ���1
	 * @return
	 */
	public static QuestionStep getQuestionStep(int step, String stepDescription) {
		Map<Integer, String> stepMap = buildStepMap(stepDescription);
		if (stepMap != null) {
			String stepStr = stepMap.get(step);
			if (StringUtil.isNotBlank(stepStr)) {
				QuestionStep result = new QuestionStep(stepStr, step);
				return result;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private static Map<Integer, String> buildStepMap(String stepDescription) {
		if (StringUtil.isNotBlank(stepDescription)) {
			Map<Integer, String> result = new HashMap<Integer, String>();
			String[] steps = StringUtil.split(stepDescription, "|");
			if (steps != null && steps.length > 0) {
				for (String oneStep : steps) {
					String[] oneStepSplit = StringUtil.split(oneStep, ":");
					if (oneStepSplit != null && oneStepSplit.length == 2) {
						result.put(Integer.parseInt(oneStepSplit[0]),
								oneStepSplit[1]);
					}
				}
			}
			return result;
		} else {
			return null;
		}
	}
}
