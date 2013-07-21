/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.core.domain.model;

/**
 * 缓存调查中最近一次录入的相关信息
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: SurveyInfoCache.java, v 0.1 2012-9-13 下午8:25:35 narutoying Exp $
 */
public class SurveyInfoCache {

	/** 调查人  */
	private String surveyPersonName;

	/** 缓存前6位的问卷编号 */
	private String surveyNumber;

	/** 市选项序号 */
	private int cityPosition;

	/** 区选项序号 */
	private int areaPosition;

	/** 街道选项序号 */
	private int streetPostion;

	/** 居委会 */
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
