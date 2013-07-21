/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seu.yang11.common.dal.DBConfigs;
import com.seu.yang11.common.dal.dataobject.QuestionaireInstanceDO;
import com.seu.yang11.common.util.DataBaseOpenHelper;
import com.seu.yang11.common.util.DateUtil;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireInstanceDAO.java, v 0.1 2012-4-25 下午11:57:38 narutoying Exp $
 */
public class QuestionaireInstanceDAO {
	private DataBaseOpenHelper dataBaseOpenHelper;

	public QuestionaireInstanceDAO(Context context) {
		dataBaseOpenHelper = new DataBaseOpenHelper(context);
	}

	public DataBaseOpenHelper getDataBaseOpenHelper() {
		return dataBaseOpenHelper;
	}

	public void setDataBaseOpenHelper(DataBaseOpenHelper dataBaseOpenHelper) {
		this.dataBaseOpenHelper = dataBaseOpenHelper;
	}

	/**
	 * 插入问卷实例DO对象
	 * 
	 * @param questionaireInstanceDO
	 * @return 返回自增id
	 */
	public int insert(QuestionaireInstanceDO questionaireInstanceDO) {
		int newId = -1;
		SQLiteDatabase db = dataBaseOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_templateId,
				questionaireInstanceDO.getTemplateId());
		db.insert(DBConfigs.TABLE_QUESTIONAIRE_INSTANCE, null, values);
		Cursor rawQuery = db.rawQuery("select last_insert_rowid()", null);
		if (rawQuery.moveToFirst()) {
			String newIdStr = rawQuery.getString(0);
			newId = Integer.parseInt(newIdStr);
		}
		return newId;
	}

	/**
	 * 更新问卷实例DO对象
	 * 
	 * @param questionaireInstanceDO
	 */
	public void update(QuestionaireInstanceDO questionaireInstanceDO) {
		SQLiteDatabase db = dataBaseOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_number,
				questionaireInstanceDO.getNumber());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_province,
				questionaireInstanceDO.getProvince());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_city,
				questionaireInstanceDO.getCity());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_zone,
				questionaireInstanceDO.getZone());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_street,
				questionaireInstanceDO.getStreet());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_villageCommittee,
				questionaireInstanceDO.getVillageCommittee());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_doorNumber,
				questionaireInstanceDO.getDoorNumber());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_surveyPerson,
				questionaireInstanceDO.getSurveyPerson());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_checkPerson,
				questionaireInstanceDO.getCheckPerson());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_surveyDate, DateUtil
				.convertToString(questionaireInstanceDO.getSurveyDate(),
						DateUtil.defaultPattern));
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_surveyStartDate, DateUtil
				.convertToString(questionaireInstanceDO.getSurveyStartTime(),
						DateUtil.defaultPattern));
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_surveyEndDate, DateUtil
				.convertToString(questionaireInstanceDO.getSurveyEndTime(),
						DateUtil.defaultPattern));
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_organization,
				questionaireInstanceDO.getOrganization());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_surveyLocation,
				questionaireInstanceDO.getSurveyLocation());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_surveyedPersonId,
				questionaireInstanceDO.getSurveyedPersonId());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_surveyedPersonName,
				questionaireInstanceDO.getSurveyedPersonName());
		values.put(DBConfigs.QUESTIONAIRE_INSTANCE_surveyedPersonCellphone,
				questionaireInstanceDO.getSurveyedPersonCellphone());
		db.update(DBConfigs.TABLE_QUESTIONAIRE_INSTANCE, values, "id=?",
				new String[] { questionaireInstanceDO.getId() + "" });
	}

	public List<QuestionaireInstanceDO> getAll() {
		SQLiteDatabase db = dataBaseOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "
				+ DBConfigs.TABLE_QUESTIONAIRE_INSTANCE, null);
		if (cursor != null && cursor.moveToFirst()) {
			List<QuestionaireInstanceDO> result = new ArrayList<QuestionaireInstanceDO>();
			do {
				QuestionaireInstanceDO instanceDO = buildDO(cursor);
				result.add(instanceDO);
			} while (cursor.moveToNext());
			return result;
		}
		return Collections.emptyList();
	}

	private QuestionaireInstanceDO buildDO(Cursor cursor) {
		QuestionaireInstanceDO instanceDO = new QuestionaireInstanceDO();
		instanceDO.setId(cursor.getInt(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_ID)));
		instanceDO.setTemplateId(cursor.getInt(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_templateId)));
		instanceDO.setSurveyPerson(cursor.getString(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_surveyPerson)));
		instanceDO.setCheckPerson(cursor.getString(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_checkPerson)));
		instanceDO.setSurveyDate(DateUtil.convertToDate(cursor.getString(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_surveyDate)),
				DateUtil.defaultPattern));
		instanceDO
				.setSurveyStartTime(DateUtil.convertToDate(
						cursor.getString(cursor
								.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_surveyStartDate)),
						DateUtil.defaultPattern));
		instanceDO
				.setSurveyEndTime(DateUtil.convertToDate(
						cursor.getString(cursor
								.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_surveyEndDate)),
						DateUtil.defaultPattern));
		instanceDO.setProvince(cursor.getString(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_province)));
		instanceDO.setCity(cursor.getString(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_city)));
		instanceDO.setZone(cursor.getString(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_zone)));
		instanceDO.setStreet(cursor.getString(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_street)));
		instanceDO
				.setVillageCommittee(cursor.getString(cursor
						.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_villageCommittee)));
		instanceDO.setDoorNumber(cursor.getString(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_doorNumber)));
		instanceDO.setNumber(cursor.getString(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_number)));
		instanceDO.setOrganization(cursor.getString(cursor
				.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_organization)));
		instanceDO
				.setSurveyLocation(cursor.getString(cursor
						.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_surveyLocation)));
		instanceDO
				.setSurveyedPersonId(cursor.getString(cursor
						.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_surveyedPersonId)));
		instanceDO
				.setSurveyedPersonName(cursor.getString(cursor
						.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_surveyedPersonName)));
		instanceDO
				.setSurveyedPersonCellphone(cursor.getString(cursor
						.getColumnIndex(DBConfigs.QUESTIONAIRE_INSTANCE_surveyedPersonCellphone)));
		return instanceDO;
	}

	/**
	 * 根据问卷编号查找
	 * 
	 * @param number
	 * @return
	 */
	public QuestionaireInstanceDO getByNumber(String number) {
		QuestionaireInstanceDO instanceDO = null;
		SQLiteDatabase db = dataBaseOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "
				+ DBConfigs.TABLE_QUESTIONAIRE_INSTANCE + " where number=?",
				new String[] { number });
		if (cursor != null && cursor.moveToFirst()) {
			instanceDO = buildDO(cursor);
		}
		return instanceDO;
	}

}
