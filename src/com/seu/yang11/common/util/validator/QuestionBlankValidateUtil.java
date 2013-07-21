/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util.validator;

import com.seu.yang11.common.util.StringUtil;

/**
 * 问题空格校验工具
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionBlankValidateUtil.java, v 0.1 2012-7-2 下午11:33:18 narutoying Exp $
 */
public class QuestionBlankValidateUtil {
	/**
	 * 获取数字校验器
	 * 
	 * @param validateStr digit:0-100
	 * @return
	 */
	public static DigitValidator getDigitValidator(String validateStr) {
		DigitValidator result = null;
		if(StringUtil.isNotBlank(validateStr)) {
			String digitPrefix = "digit:";
			if(validateStr.startsWith(digitPrefix)) {
				result = new DigitValidator();
				String rangeStr = validateStr.substring(digitPrefix.length());
				if(StringUtil.isNotBlank(rangeStr)) {
					String[] split = StringUtil.split(rangeStr, "-");
					if(split != null && split.length == 2) {
						result.setMin(Double.parseDouble(split[0]));
						result.setMax(Double.parseDouble(split[1]));
					}
				}
				return result;
			}
		}
		return result;
	}
}
