package com.seu.yang11.core.domain.model;

/**
 * һ���ʾ���е�һЩ���ԣ�������ˡ��������ڵ�
 * @author narutoying
 * @version 1.0
 * @created 21-����-2012 23:41:00
 */
public class QuestionaireBasicInfo {

	/**
	 * �ʾ���
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
	 * �ֵ�/��/��
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