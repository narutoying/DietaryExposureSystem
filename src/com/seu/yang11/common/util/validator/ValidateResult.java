/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util.validator;
/**
 * 校验结果
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: ValidateResult.java, v 0.1 2012-7-16 下午10:22:23 narutoying Exp $
 */
public class ValidateResult {
	private boolean success = true;
	private String msg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
