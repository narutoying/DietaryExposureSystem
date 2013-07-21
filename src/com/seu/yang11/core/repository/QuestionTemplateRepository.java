/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.repository;

import java.util.List;

import com.seu.yang11.core.domain.model.QuestionTemplate;

/**
 * 问卷题目模板仓储
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionTemplateRepository.java, v 0.1 2012-5-4 下午8:22:57 narutoying Exp $
 */
public interface QuestionTemplateRepository {
	public List<QuestionTemplate> getByQuestionaireTemplateId(int questionaireTemplateId);
	
	public QuestionTemplate findById(int questionTemplateId);
}
