/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.repository;

import com.seu.yang11.core.domain.model.QuestionaireTemplate;

/**
 * �ʾ�ģ��ִ�
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireTemplateRepository.java, v 0.1 2012-4-22 ����3:23:23 narutoying Exp $
 */
public interface QuestionaireTemplateRepository {
	/**
	 * ��ȡĬ���ʾ�ģ�壨ʡ���ص����ʾ�
	 * 
	 * @return
	 */
	public QuestionaireTemplate getDefaultTemplate();

	/**
	 * ��ȡָ��id���ʾ�ģ��
	 * 
	 * @return
	 */
	public QuestionaireTemplate getTemplateById(int id);
}
