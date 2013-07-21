/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util.validator;
/**
 * 填空题空格校验器
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: Validator.java, v 0.1 2012-7-16 下午10:10:36 narutoying Exp $
 */
public interface BlankValidator {
	public ValidateResult getValidateResult(String inputStr);
}
