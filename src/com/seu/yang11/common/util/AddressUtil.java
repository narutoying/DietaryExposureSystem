/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: AddressUtil.java, v 0.1 2012-5-12 下午4:27:31 narutoying Exp $
 */
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AddressUtil {

	//获取省的地址列表,//file-->数据库文件
	public static Map<Integer, List> getProvince(File file) {

		String sql = "select ProSort ,ProName from T_Province ";
		SQLiteDatabase db = null;
		Cursor c = null;
		Map<Integer, List> provinceData = new HashMap<Integer, List>();
		//List provinceList = null;
		try {
			db = SQLiteDatabase.openOrCreateDatabase(file, null);
			c = db.rawQuery(sql, null);
			List provinceList1 = new ArrayList();
			List provinceList2 = new ArrayList();
			while (c.moveToNext()) {
				Map provinceMap = new HashMap();
				provinceMap.put(c.getString(1), c.getInt(0));
				provinceList1.add(provinceMap);
				provinceList2.add(c.getString(1));
			}
			provinceData.put(0, provinceList1);
			provinceData.put(1, provinceList2);
		} catch (Exception e) {
			Log.d("WineStock", "getProvince:" + e.getMessage());
		} finally {
			if (c != null) {
				c.close();
			}
			if (db != null) {
				db.close();
			}
		}
		return provinceData;
	}

	//获取对应省下面城市的列表,//file-->数据库文件,id-->指对应省的ID
	public static Map<Integer, List> getCityByPid(int id, File file) {
		String sql = "select CitySort,CityName  from T_City where ProID= " + id;
		SQLiteDatabase db = null;
		Cursor c = null;
		Map<Integer, List> cityData = new HashMap<Integer, List>();
		//List cityList = null;
		try {
			db = SQLiteDatabase.openOrCreateDatabase(file, null);
			c = db.rawQuery(sql, null);
			List cityList1 = new ArrayList();
			List cityList2 = new ArrayList();
			while (c.moveToNext()) {
				Map cityMap = new HashMap();
				cityMap.put(c.getString(1), c.getInt(0));
				cityList1.add(cityMap);
				cityList2.add(c.getString(1));
			}
			cityData.put(0, cityList1);
			cityData.put(1, cityList2);

		} catch (Exception e) {
			Log.d("WineStock", "getCityByPid:" + e.getMessage());
		} finally {
			if (c != null) {
				c.close();
			}
			if (db != null) {
				db.close();
			}
		}
		return cityData;
	}

	//获取对应市下面区的列表,//file-->数据库文件,id-->指对应市的ID
	public static Map<Integer, List> getAreaByPid(int id, File file) {
		String sql = "select ZoneID, ZoneName  from T_Zone where CityID= " + id;
		SQLiteDatabase db = null;
		Cursor c = null;
		Map<Integer, List> zoneData = new HashMap<Integer, List>();
		try {
			db = SQLiteDatabase.openOrCreateDatabase(file, null);
			c = db.rawQuery(sql, null);
			List zoneList1 = new ArrayList();
			List zoneList2 = new ArrayList();
			while (c.moveToNext()) {
				Map zoneMap = new HashMap();
				zoneMap.put(c.getString(1), c.getInt(0));
				zoneList1.add(zoneMap);
				zoneList2.add(c.getString(1));
			}
			zoneData.put(0, zoneList1);
			zoneData.put(1, zoneList2);
		} catch (Exception e) {
			Log.d("WineStock", "getAreaByPid:" + e.getMessage());
		} finally {
			if (c != null) {
				c.close();
			}
			if (db != null) {
				db.close();
			}
		}
		return zoneData;
	}

	//获取对应区下面街道的列表
	public static List<String> getStreetByZoneId(int zoneId, File file) {
		String sql = "select street_name  from T_Street where zone_id= "
				+ zoneId;
		SQLiteDatabase db = null;
		Cursor c = null;
		List<String> streetList = null;
		try {
			db = SQLiteDatabase.openOrCreateDatabase(file, null);
			c = db.rawQuery(sql, null);
			streetList = new ArrayList<String>();
			while (c.moveToNext()) {
				streetList.add(c.getString(0));
			}
		} catch (Exception e) {
			Log.d("WineStock", "getStreetByZoneId:" + e.getMessage());
		} finally {
			if (c != null) {
				c.close();
			}
			if (db != null) {
				db.close();
			}
		}
		return streetList;
	}

}
