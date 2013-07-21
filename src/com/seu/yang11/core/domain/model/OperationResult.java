/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.domain.model;

/**
 * ͳһ�������
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: OperationResult.java, v 0.1 2012-7-28 ����1:28:01 narutoying Exp $
 */
public class OperationResult {
	/** �����Ƿ�ɹ�����ѡ */
	private boolean success;
	/** ����룬��ѡ */
	private String resultCode;
	/** �������������Ϣ����ѡ */
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
