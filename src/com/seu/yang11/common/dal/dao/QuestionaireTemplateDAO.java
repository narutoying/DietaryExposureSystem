/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seu.yang11.common.dal.DBConfigs;
import com.seu.yang11.common.dal.dataobject.QuestionaireTemplateDO;
import com.seu.yang11.common.util.DataBaseOpenHelper;
import com.seu.yang11.common.util.DateUtil;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionaireTemplateDAO.java, v 0.1 2012-4-25 下午11:57:48 narutoying Exp $
 */
public class QuestionaireTemplateDAO {
	private DataBaseOpenHelper dataBaseOpenHelper;

	public QuestionaireTemplateDAO(Context context) {
		dataBaseOpenHelper = new DataBaseOpenHelper(context);
	}

	public DataBaseOpenHelper getDataBaseOpenHelper() {
		return dataBaseOpenHelper;
	}

	public void setDataBaseOpenHelper(DataBaseOpenHelper dataBaseOpenHelper) {
		this.dataBaseOpenHelper = dataBaseOpenHelper;
	}

	/**
	 * 以下是对外的api
	 */
	public int insert(QuestionaireTemplateDO questionaireTemplateDO) {
		SQLiteDatabase db = dataBaseOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBConfigs.QUESTIONAIRE_TEMPLATE_NAME,
				questionaireTemplateDO.getName());
		values.put(DBConfigs.QUESTIONAIRE_TEMPLATE_CREATE_TIME, DateUtil
				.convertToString(questionaireTemplateDO.getCreateTime(),
						DateUtil.defaultPattern));
		values.put(DBConfigs.QUESTIONAIRE_TEMPLATE_DESCRIPTION,
				questionaireTemplateDO.getDescription());
		values.put(DBConfigs.QUESTIONAIRE_TEMPLATE_QUESTION_STEPS,
				questionaireTemplateDO.getQuestionSteps());
		return (int) db.insert(DBConfigs.TABLE_QUESTIONAIRE_TEMPLATE, null,
				values);
	}

	public QuestionaireTemplateDO findById(int id) {
		QuestionaireTemplateDO result = new QuestionaireTemplateDO();
		SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from "
				+ DBConfigs.TABLE_QUESTIONAIRE_TEMPLATE + " where id = ?",
				new String[] { id + "" });
		if (cursor.moveToFirst()) {
			result.setId(cursor.getInt(cursor
					.getColumnIndex(DBConfigs.QUESTIONAIRE_TEMPLATE_ID)));
			result.setName(cursor.getString(cursor
					.getColumnIndex(DBConfigs.QUESTIONAIRE_TEMPLATE_NAME)));
			result.setCreateTime(DateUtil.convertToDate(
					cursor.getString(cursor
							.getColumnIndex(DBConfigs.QUESTIONAIRE_TEMPLATE_CREATE_TIME)),
					DateUtil.defaultPattern));
			result.setQuestionSteps(cursor.getString(cursor
					.getColumnIndex(DBConfigs.QUESTIONAIRE_TEMPLATE_QUESTION_STEPS)));
			return result;
		} else {
			return null;
		}
	}
}
