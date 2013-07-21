/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.domain.model;

/**
 * 统一操作结果
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: OperationResult.java, v 0.1 2012-7-28 下午1:28:01 narutoying Exp $
 */
public class OperationResult {
	/** 操作是否成功，必选 */
	private boolean success;
	/** 结果码，可选 */
	private String resultCode;
	/** 操作结果附带消息，可选 */
	private String message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
}
