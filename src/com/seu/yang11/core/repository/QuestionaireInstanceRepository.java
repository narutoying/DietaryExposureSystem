/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.repository;

import java.util.List;

import com.seu.yang11.core.domain.model.QuestionaireInstance;
import com.seu.yang11.core.domain.model.QuestionaireTemplate;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireInstanceRepository.java, v 0.1 2012-4-22 ����3:38:22 narutoying Exp $
 */
public interface QuestionaireInstanceRepository {
	/**
	 * ͨ��һ��ģ�����ɶ�Ӧ��ģ��ʵ��
	 * 
	 * @param questionnaireTemplate
	 * @return
	 */
	public QuestionaireInstance createInstance(
			QuestionaireTemplate questionnaireTemplate);

	/**
	 * ���¶�Ӧ��ģ��ʵ��
	 * 
	 * @param questionnaireInstance
	 */
	public void updateInstance(QuestionaireInstance questionnaireInstance);

	/**
	 * ��ȡָ��id��ʵ��
	 * 
	 * @param id
	 * @return
	 */
	public QuestionaireInstance getInstanceById(String id);

	/**
	 * ��ȡ���е�ʵ��
	 * 
	 * @return
	 */
	public List<QuestionaireInstance> getAllInstances();

}
