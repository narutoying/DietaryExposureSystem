/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.repository;

import java.util.List;

import com.seu.yang11.core.domain.model.QuestionAnswer;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionAnswerRepository.java, v 0.1 2012-4-24 ионГ12:41:45 narutoying Exp $
 */
public interface QuestionAnswerRepository {
	public void saveQuestionAnswer(QuestionAnswer answer);

	public List<QuestionAnswer> getAnswersByQuestionaireId(int questionaireId);
}
