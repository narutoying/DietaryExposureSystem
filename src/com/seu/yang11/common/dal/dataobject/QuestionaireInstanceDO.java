/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.dataobject;

import java.util.Date;

/**
 * 问卷实例DO对象
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireInstanceDO.java, v 0.1 2012-4-25 下午11:34:48 narutoying Exp $
 */
public class QuestionaireInstanceDO {
	private int id;
	/** 所属问卷模板id */
	private int templateId;
	/** 调查员 */
	private String surveyPerson;

	/** 调查日期 */
	private Date surveyDate;
	/** 调查开始时间 */
	private Date surveyStartTime;
	/** 调查结束时间 */
	private Date surveyEndTime;
	/** 监督员 */
	private String checkPerson;
	/**
	 * 问卷编号(由用户填入)
	 */
	private String number;

	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区(县)
	 */
	private String zone;
	/**
	 * 街道/镇
	 */
	private String street;
	/**
	 * 村/居委会
	 */
	private String villageCommittee;
	/**
	 * 门牌号
	 */
	private String doorNumber;
	/**
	 * 单位
	 */
	private String organization;
	/** 被调查人编号 */
	private String surveyedPersonId;
	/** 被调查人姓名 */
	private String surveyedPersonName;
	/** 被调查人电话 */
	private String surveyedPersonCellphone;
	/** 调查地点 */
	private String surveyLocation;

	public String getSurveyPerson() {
		return surveyPerson;
	}

	public void setSurveyPerson(String surveyPerson) {
		this.surveyPerson = surveyPerson;
	}

	public Date getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getVillageCommittee() {
		return villageCommittee;
	}

	public void setVillageCommittee(String villageCommittee) {
		this.villageCommittee = villageCommittee;
	}

	public String getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getSurveyLocation() {
		return surveyLocation;
	}

	public void setSurveyLocation(String surveyLocation) {
		this.surveyLocation = surveyLocation;
	}

	public String getSurveyedPersonId() {
		return surveyedPersonId;
	}

	public void setSurveyedPersonId(String surveyedPersonId) {
		this.surveyedPersonId = surveyedPersonId;
	}

	public String getSurveyedPersonName() {
		return surveyedPersonName;
	}

	public void setSurveyedPersonName(String surveyedPersonName) {
		this.surveyedPersonName = surveyedPersonName;
	}

	public String getSurveyedPersonCellphone() {
		return surveyedPersonCellphone;
	}

	public void setSurveyedPersonCellphone(String surveyedPersonCellphone) {
		this.surveyedPersonCellphone = surveyedPersonCellphone;
	}

	public Date getSurveyStartTime() {
		return surveyStartTime;
	}

	public void setSurveyStartTime(Date surveyStartTime) {
		this.surveyStartTime = surveyStartTime;
	}

	public Date getSurveyEndTime() {
		return surveyEndTime;
	}

	public void setSurveyEndTime(Date surveyEndTime) {
		this.surveyEndTime = surveyEndTime;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

}
