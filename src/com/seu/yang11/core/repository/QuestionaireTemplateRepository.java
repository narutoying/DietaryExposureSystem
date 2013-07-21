/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.repository;

import com.seu.yang11.core.domain.model.QuestionaireTemplate;

/**
 * 问卷模板仓储
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireTemplateRepository.java, v 0.1 2012-4-22 下午3:23:23 narutoying Exp $
 */
public interface QuestionaireTemplateRepository {
	/**
	 * 获取默认问卷模板（省疾控调查问卷）
	 * 
	 * @return
	 */
	public QuestionaireTemplate getDefaultTemplate();

	/**
	 * 获取指定id的问卷模板
	 * 
	 * @return
	 */
	public QuestionaireTemplate getTemplateById(int id);
}
