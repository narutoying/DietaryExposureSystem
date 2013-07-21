package com.seu.yang11.core.domain.model;

/**
 * 一个问卷固有的一些属性，如调查人、调查日期等
 * @author narutoying
 * @version 1.0
 * @created 21-四月-2012 23:41:00
 */
public class QuestionaireBasicInfo {

	/**
	 * 问卷编号
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
	 * 街道/镇/乡
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

	public QuestionaireBasicInfo() {

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
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

}