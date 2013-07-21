/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util.validator;

import com.seu.yang11.common.util.StringUtil;

/**
 * ����У����
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: DigitValidator.java, v 0.1 2012-7-2 ����11:35:50 narutoying Exp $
 */
public class DigitValidator implements BlankValidator {
	/** ��Сֵ�������� */
	private double min;
	/** ���ֵ�������� */
	private double max;

	public DigitValidator() {
		super();
	}

	public DigitValidator(double min, double max) {
		super();
		this.min = min;
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	@Override
	public ValidateResult getValidateResult(String currentInputString) {
		ValidateResult result = new ValidateResult();
		if (StringUtil.isNotBlank(currentInputString)) {
			try {
				Double number = Double.parseDouble(currentInputString);
				if (number > getMax() || number < getMin()) {
					result.setMsg("��������" + number + "��������[" + getMin() + ", " + getMax()
							+ "]");
					result.setSuccess(false);
				}
			} catch (NumberFormatException e) {
				result.setMsg("��������������ַ�");
				result.setSuccess(false);
			}
		}
		return result;
	}
}
