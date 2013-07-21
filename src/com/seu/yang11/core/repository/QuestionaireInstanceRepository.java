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
 * @version $Id: QuestionaireInstanceRepository.java, v 0.1 2012-4-22 下午3:38:22 narutoying Exp $
 */
public interface QuestionaireInstanceRepository {
	/**
	 * 通过一套模板生成对应的模板实例
	 * 
	 * @param questionnaireTemplate
	 * @return
	 */
	public QuestionaireInstance createInstance(
			QuestionaireTemplate questionnaireTemplate);

	/**
	 * 更新对应的模板实例
	 * 
	 * @param questionnaireInstance
	 */
	public void updateInstance(QuestionaireInstance questionnaireInstance);

	/**
	 * 获取指定id的实例
	 * 
	 * @param id
	 * @return
	 */
	public QuestionaireInstance getInstanceById(String id);

	/**
	 * 获取所有的实例
	 * 
	 * @return
	 */
	public List<QuestionaireInstance> getAllInstances();

}
