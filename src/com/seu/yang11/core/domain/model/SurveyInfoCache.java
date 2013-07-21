/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.domain.model;

/**
 * ������������һ��¼��������Ϣ
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: SurveyInfoCache.java, v 0.1 2012-9-13 ����8:25:35 narutoying Exp $
 */
public class SurveyInfoCache {

	/** ������  */
	private String surveyPersonName;

	/** ����ǰ6λ���ʾ��� */
	private String surveyNumber;

	/** ��ѡ����� */
	private int cityPosition;

	/** ��ѡ����� */
	private int areaPosition;

	/** �ֵ�ѡ����� */
	private int streetPostion;

	/** ��ί�� */
	private String villageCommittee;

	public String getSurveyPersonName() {
		return surveyPersonName;
	}

	public void setSurveyPersonName(String surveyPersonName) {
		this.surveyPersonName = surveyPersonName;
	}

	public String getSurveyNumber() {
		return surveyNumber;
	}

	public void setSurveyNumber(String surveyNumber) {
		this.surveyNumber = surveyNumber;
	}

	public int getCityPosition() {
		return cityPosition;
	}

	public void setCityPosition(int cityPosition) {
		this.cityPosition = cityPosition;
	}

	public int getAreaPosition() {
		return areaPosition;
	}

	public void setAreaPosition(int areaPosition) {
		this.areaPosition = areaPosition;
	}

	public int getStreetPostion() {
		return streetPostion;
	}

	public void setStreetPostion(int streetPostion) {
		this.streetPostion = streetPostion;
	}

	public String getVillageCommittee() {
		return villageCommittee;
	}

	public void setVillageCommittee(String villageCommittee) {
		this.villageCommittee = villageCommittee;
	}

}
