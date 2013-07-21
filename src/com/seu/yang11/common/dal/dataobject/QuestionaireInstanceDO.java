/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.dataobject;

import java.util.Date;

/**
 * �ʾ�ʵ��DO����
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireInstanceDO.java, v 0.1 2012-4-25 ����11:34:48 narutoying Exp $
 */
public class QuestionaireInstanceDO {
	private int id;
	/** �����ʾ�ģ��id */
	private int templateId;
	/** ����Ա */
	private String surveyPerson;

	/** �������� */
	private Date surveyDate;
	/** ���鿪ʼʱ�� */
	private Date surveyStartTime;
	/** �������ʱ�� */
	private Date surveyEndTime;
	/** �ලԱ */
	private String checkPerson;
	/**
	 * �ʾ���(���û�����)
	 */
	private String number;

	/**
	 * ʡ
	 */
	private String province;
	/**
	 * ��
	 */
	private String city;
	/**
	 * ��(��)
	 */
	private String zone;
	/**
	 * �ֵ�/��
	 */
	private String street;
	/**
	 * ��/��ί��
	 */
	private String villageCommittee;
	/**
	 * ���ƺ�
	 */
	private String doorNumber;
	/**
	 * ��λ
	 */
	private String organization;
	/** �������˱�� */
	private String surveyedPersonId;
	/** ������������ */
	private String surveyedPersonName;
	/** �������˵绰 */
	private String surveyedPersonCellphone;
	/** ����ص� */
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
