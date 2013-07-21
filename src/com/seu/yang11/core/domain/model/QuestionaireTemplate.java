package com.seu.yang11.core.domain.model;

import java.util.Date;
import java.util.List;

/**
 * 问卷模板
 * @author narutoying
 * @version 1.0
 * @created 21-四月-2012 23:41:01
 */
public class QuestionaireTemplate {

	private int id;
	private String name;
	private Date createTime;
	/**
	 * 问卷描述
	 */
	private String description;
	public List<QuestionTemplate> QuestionTemplate;

	public QuestionaireTemplate(int id, String name, Date createTime,
			String description) {
		super();
		this.id = id;
		this.name = name;
		this.createTime = createTime;
		this.description = description;
	}

	public void finalize() throws Throwable {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QuestionTemplate> getQuestionTemplate() {
		return QuestionTemplate;
	}

	public void setQuestionTemplate(List<QuestionTemplate> questionTemplate) {
		QuestionTemplate = questionTemplate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}