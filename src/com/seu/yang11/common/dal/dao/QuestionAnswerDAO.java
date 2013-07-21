/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seu.yang11.common.dal.DBConfigs;
import com.seu.yang11.common.dal.dataobject.QuestionAnswerDO;
import com.seu.yang11.common.util.DataBaseOpenHelper;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionAnswerDAO.java, v 0.1 2012-4-25 ÏÂÎç11:58:02 narutoying Exp $
 */
public class QuestionAnswerDAO {
	private DataBaseOpenHelper dataBaseOpenHelper;

	public QuestionAnswerDAO(Context context) {
		dataBaseOpenHelper = new DataBaseOpenHelper(context);
	}

	public DataBaseOpenHelper getDataBaseOpenHelper() {
		return dataBaseOpenHelper;
	}

	public void setDataBaseOpenHelper(DataBaseOpenHelper dataBaseOpenHelper) {
		this.dataBaseOpenHelper = dataBaseOpenHelper;
	}

	public void insert(QuestionAnswerDO questionAnswerDO) {
		SQLiteDatabase db = dataBaseOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBConfigs.QUESTION_ANSWER_questionaireId,
				questionAnswerDO.getQuestionaireId());
		values.put(DBConfigs.QUESTION_ANSWER_questionTemplateId,
				questionAnswerDO.getQuestionTemplateId());
		values.put(DBConfigs.QUESTION_ANSWER_content,
				questionAnswerDO.getContent());
		db.insert(DBConfigs.TABLE_QUESTION_ANSWER, null, values);
	}

	public void update(QuestionAnswerDO questionAnswerDO) {
		SQLiteDatabase db = dataBaseOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBConfigs.QUESTION_ANSWER_questionaireId,
				questionAnswerDO.getQuestionaireId());
		values.put(DBConfigs.QUESTION_ANSWER_questionTemplateId,
				questionAnswerDO.getQuestionTemplateId());
		values.put(DBConfigs.QUESTION_ANSWER_content,
				questionAnswerDO.getContent());
		db.update(DBConfigs.TABLE_QUESTION_ANSWER, values,
				"questionaireId = ? and questionTemplateId = ?", new String[] {
						"" + questionAnswerDO.getQuestionaireId(),
						"" + questionAnswerDO.getQuestionTemplateId() });
	}

	public QuestionAnswerDO find(int questionaireId, int questionTemplateId) {
		QuestionAnswerDO result = null;
		SQLiteDatabase db = dataBaseOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "
				+ DBConfigs.TABLE_QUESTION_ANSWER
				+ " where questionaireId = ? and questionTemplateId = ?",
				new String[] { "" + questionaireId, "" + questionTemplateId });
		if (cursor.moveToFirst()) {
			result = new QuestionAnswerDO();
			result.setId(cursor.getInt(cursor
					.getColumnIndex(DBConfigs.QUESTION_ANSWER_id)));
			result.setContent(cursor.getString(cursor
					.getColumnIndex(DBConfigs.QUESTION_ANSWER_content)));
			result.setQuestionaireId(questionaireId);
			result.setQuestionTemplateId(questionTemplateId);
		}
		return result;
	}

	public List<QuestionAnswerDO> getAll(int questionaireId) {
		List<QuestionAnswerDO> result = new ArrayList<QuestionAnswerDO>();
		SQLiteDatabase db = dataBaseOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "
				+ DBConfigs.TABLE_QUESTION_ANSWER + " where "
				+ DBConfigs.QUESTION_ANSWER_questionaireId + "=?",
				new String[] { questionaireId + "" });
		if (cursor != null && cursor.moveToFirst()) {
			do {
				QuestionAnswerDO questionAnswerDO = new QuestionAnswerDO();
				questionAnswerDO.setId(cursor.getInt(cursor
						.getColumnIndex(DBConfigs.QUESTION_ANSWER_id)));
				questionAnswerDO.setContent(cursor.getString(cursor
						.getColumnIndex(DBConfigs.QUESTION_ANSWER_content)));
				questionAnswerDO
						.setQuestionaireId(cursor.getInt(cursor
								.getColumnIndex(DBConfigs.QUESTION_ANSWER_questionaireId)));
				questionAnswerDO
						.setQuestionTemplateId(cursor.getInt(cursor
								.getColumnIndex(DBConfigs.QUESTION_ANSWER_questionTemplateId)));
				result.add(questionAnswerDO);
			} while (cursor.moveToNext());
		}
		return result;
	}
	
	public Map<Integer, QuestionAnswerDO> getAllMap(int questionaireId) {
		Map<Integer, QuestionAnswerDO> result = new HashMap<Integer, QuestionAnswerDO>();
		List<QuestionAnswerDO> all = this.getAll(questionaireId);
		for(QuestionAnswerDO answerDO : all) {
			result.put(answerDO.getQuestionTemplateId(), answerDO);
		}
		return result;
	}

	public List<QuestionAnswerDO> getAll() {
		List<QuestionAnswerDO> result = new ArrayList<QuestionAnswerDO>();
		SQLiteDatabase db = dataBaseOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "
				+ DBConfigs.TABLE_QUESTION_ANSWER, null);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				QuestionAnswerDO questionAnswerDO = new QuestionAnswerDO();
				questionAnswerDO.setId(cursor.getInt(cursor
						.getColumnIndex(DBConfigs.QUESTION_ANSWER_id)));
				questionAnswerDO.setContent(cursor.getString(cursor
						.getColumnIndex(DBConfigs.QUESTION_ANSWER_content)));
				questionAnswerDO
						.setQuestionaireId(cursor.getInt(cursor
								.getColumnIndex(DBConfigs.QUESTION_ANSWER_questionaireId)));
				questionAnswerDO
						.setQuestionTemplateId(cursor.getInt(cursor
								.getColumnIndex(DBConfigs.QUESTION_ANSWER_questionTemplateId)));
				result.add(questionAnswerDO);
			} while (cursor.moveToNext());
		}
		return result;
	}
}
