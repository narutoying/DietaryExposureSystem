/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.dal.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seu.yang11.common.dal.DBConfigs;
import com.seu.yang11.common.dal.dataobject.QuestionTemplateDO;
import com.seu.yang11.common.util.DataBaseOpenHelper;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: QuestionTemplateDAO.java, v 0.1 2012-4-25 下午11:58:23 narutoying Exp $
 */
public class QuestionTemplateDAO {
	private DataBaseOpenHelper dataBaseOpenHelper;

	public QuestionTemplateDAO(Context context) {
		dataBaseOpenHelper = new DataBaseOpenHelper(context);
	}

	public DataBaseOpenHelper getDataBaseOpenHelper() {
		return dataBaseOpenHelper;
	}

	public void setDataBaseOpenHelper(DataBaseOpenHelper dataBaseOpenHelper) {
		this.dataBaseOpenHelper = dataBaseOpenHelper;
	}

	/**
	 * 通过问卷模板id获取对应的问题模板DO对象
	 * 
	 * @param questionaireTemplateId
	 */
	public List<QuestionTemplateDO> findByQuestionaireTemplateId(
			int questionaireTemplateId) {
		List<QuestionTemplateDO> result = new ArrayList<QuestionTemplateDO>();
		SQLiteDatabase db = dataBaseOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "
				+ DBConfigs.TABLE_QUESTION_TEMPLATE
				+ " where questionaireTemplateId = ?",
				new String[] { questionaireTemplateId + "" });
		if (cursor != null && cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				int questionaireTemplateId2 = cursor.getInt(cursor
						.getColumnIndex("questionaireTemplateId"));
				int index = cursor.getInt(cursor.getColumnIndex("qIndex"));
				int step = cursor.getInt(cursor.getColumnIndex("step"));
				int type = cursor.getInt(cursor.getColumnIndex("type"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String content = cursor.getString(cursor
						.getColumnIndex("content"));
				String validator = cursor.getString(cursor
						.getColumnIndex("validator"));
				QuestionTemplateDO questionTemplateDO = new QuestionTemplateDO();
				questionTemplateDO.setId(id);
				questionTemplateDO.setqIndex(index);
				questionTemplateDO
						.setQuestionaireTemplateId(questionaireTemplateId2);
				questionTemplateDO.setStep(step);
				questionTemplateDO.setType(type);
				questionTemplateDO.setTitle(title);
				questionTemplateDO.setContent(content);
				questionTemplateDO.setValidator(validator);
				questionTemplateDO.setImage(cursor.getString(cursor
						.getColumnIndex("image")));
				result.add(questionTemplateDO);
			} while (cursor.moveToNext());
			return result;
		} else {
			return Collections.emptyList();
		}
	}

	public QuestionTemplateDO findById(int questionTemplateId) {
		SQLiteDatabase db = dataBaseOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "
				+ DBConfigs.TABLE_QUESTION_TEMPLATE + " where id = ?",
				new String[] { questionTemplateId + "" });
		if (cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int questionaireTemplateId2 = cursor.getInt(cursor
					.getColumnIndex("questionaireTemplateId"));
			int index = cursor.getInt(cursor.getColumnIndex("qIndex"));
			int step = cursor.getInt(cursor.getColumnIndex("step"));
			int type = cursor.getInt(cursor.getColumnIndex("type"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			String validator = cursor.getString(cursor
					.getColumnIndex("validator"));
			QuestionTemplateDO questionTemplateDO = new QuestionTemplateDO();
			questionTemplateDO.setId(id);
			questionTemplateDO.setqIndex(index);
			questionTemplateDO
					.setQuestionaireTemplateId(questionaireTemplateId2);
			questionTemplateDO.setStep(step);
			questionTemplateDO.setType(type);
			questionTemplateDO.setTitle(title);
			questionTemplateDO.setContent(content);
			questionTemplateDO.setValidator(validator);
			questionTemplateDO.setImage(cursor.getString(cursor
					.getColumnIndex("image")));
			return questionTemplateDO;
		}
		return null;
	}
}
