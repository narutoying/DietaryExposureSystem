/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util.validator;

import com.seu.yang11.common.util.StringUtil;

/**
 * 数字校验器
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: DigitValidator.java, v 0.1 2012-7-2 下午11:35:50 narutoying Exp $
 */
public class DigitValidator implements BlankValidator {
	/** 最小值，闭区间 */
	private double min;
	/** 最大值，闭区间 */
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
					result.setMsg("输入数字" + number + "超出区间[" + getMin() + ", " + getMax()
							+ "]");
					result.setSuccess(false);
				}
			} catch (NumberFormatException e) {
				result.setMsg("不能输入非数字字符");
				result.setSuccess(false);
			}
		}
		return result;
	}
}
