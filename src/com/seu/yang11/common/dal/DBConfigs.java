/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal;

/**
 * 存放数据库相关配置与表结构
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: DBConfigs.java, v 0.1 2012-5-4 下午4:36:03 narutoying Exp $
 */
public class DBConfigs {

	/**
	 * 有如下表：
	 * 1. 问卷模板 
	 * 2. 问卷实例
	 * 3. 问题模板
	 * 4. 问题答案
	 */
	public static final String TABLE_QUESTIONAIRE_TEMPLATE = "questionaire_template";
	public static final String QUESTIONAIRE_TEMPLATE_ID = "id";
	public static final String QUESTIONAIRE_TEMPLATE_NAME = "name";
	public static final String QUESTIONAIRE_TEMPLATE_CREATE_TIME = "createTime";
	public static final String QUESTIONAIRE_TEMPLATE_DESCRIPTION = "description";
	public static final String QUESTIONAIRE_TEMPLATE_QUESTION_STEPS = "questionsteps";

	public static final String TABLE_QUESTIONAIRE_INSTANCE = "questionaire_instance";
	public static final String QUESTIONAIRE_INSTANCE_ID = "id";
	public static final String QUESTIONAIRE_INSTANCE_templateId = "templateId";
	public static final String QUESTIONAIRE_INSTANCE_surveyPerson = "surveyPerson";
	public static final String QUESTIONAIRE_INSTANCE_checkPerson = "checkPerson";
	public static final String QUESTIONAIRE_INSTANCE_surveyDate = "surveyDate";
	public static final String QUESTIONAIRE_INSTANCE_surveyStartDate = "surveyStartDate";
	public static final String QUESTIONAIRE_INSTANCE_surveyEndDate = "surveyEndDate";
	public static final String QUESTIONAIRE_INSTANCE_number = "number";
	public static final String QUESTIONAIRE_INSTANCE_province = "province";
	public static final String QUESTIONAIRE_INSTANCE_city = "city";
	public static final String QUESTIONAIRE_INSTANCE_zone = "zone";
	public static final String QUESTIONAIRE_INSTANCE_street = "street";
	public static final String QUESTIONAIRE_INSTANCE_villageCommittee = "villageCommittee";
	public static final String QUESTIONAIRE_INSTANCE_doorNumber = "doorNumber";
	public static final String QUESTIONAIRE_INSTANCE_organization = "organization";
	public static final String QUESTIONAIRE_INSTANCE_surveyedPersonId = "surveyedPersonId";
	public static final String QUESTIONAIRE_INSTANCE_surveyedPersonName = "surveyedPersonName";
	public static final String QUESTIONAIRE_INSTANCE_surveyedPersonCellphone = "surveyedPersonCellphone";
	public static final String QUESTIONAIRE_INSTANCE_surveyLocation = "surveyLocation";

	public static final String TABLE_QUESTION_TEMPLATE = "question_template";
	public static final String QUESTION_TEMPLATE_id = "id";
	public static final String QUESTION_TEMPLATE_questionaireTemplateId = "questionaireTemplateId";
	public static final String QUESTION_TEMPLATE_qIndex = "qIndex";
	public static final String QUESTION_TEMPLATE_title = "title";
	public static final String QUESTION_TEMPLATE_content = "content";
	public static final String QUESTION_TEMPLATE_step = "step";
	public static final String QUESTION_TEMPLATE_type = "type";

	public static final String TABLE_QUESTION_ANSWER = "question_answer";
	public static final String QUESTION_ANSWER_id = "id";
	public static final String QUESTION_ANSWER_content = "content";
	public static final String QUESTION_ANSWER_questionaireId = "questionaireId";
	public static final String QUESTION_ANSWER_questionTemplateId = "questionTemplateId";

	public static final String CREATE_TABLE_QUESTIONAIRE_TEMPLATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_QUESTIONAIRE_TEMPLATE
			+ " (id integer primary key autoincrement, name varchar(50), createTime timestamp, description varchar(255), questionsteps varchar(255))";

	public static final String CREATE_TABLE_QUESTIONAIRE_INSTANCE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_QUESTIONAIRE_INSTANCE
			+ " (id integer primary key autoincrement, templateId integer, surveyPerson varchar(50), surveyDate timestamp, number varchar(20),"
			+ " province varchar(20), city varchar(20), zone varchar(20), street varchar(20), villageCommittee varchar(20), doorNumber varchar(20))";

	public static final String CREATE_TABLE_QUESTION_TEMPLATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_QUESTION_TEMPLATE
			+ " (id integer primary key autoincrement,"
			+ " questionaireTemplateId integer, qIndex integer, title varchar(100), content varchar(255), step integer, type integer, validator varchar(255))";

	public static final String CREATE_TABLE_QUESTION_ANSWER = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_QUESTION_ANSWER
			+ " (id integer primary key autoincrement,"
			+ " content varchar(255), questionaireId integer, questionTemplateId integer)";

	public static String getLocationDBPath(String packageName) {
		return "data/data/" + packageName + "/databases/" + LOCATION_DB_NAME;
	}

	public static final String LOCATION_DB_NAME = "china_province_city_zone.db3";

	public static final String QUESTIONAIRE_DB_NAME = "DietaryExposureSystem.db";

}
