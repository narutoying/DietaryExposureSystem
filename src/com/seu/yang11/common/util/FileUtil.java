/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: FileUtil.java, v 0.1 2012-5-12 ÏÂÎç4:15:45 narutoying Exp $
 */
public class FileUtil {

	public static void loadDbFile(int chinaProvinceCityZone, File file,
			Resources resources, String packageName) {
		InputStream rawResource = resources
				.openRawResource(chinaProvinceCityZone);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				rawResource);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] buffer = new byte[8192];
			int count = 0;
			try {
				while ((count = bufferedInputStream.read(buffer)) > 0) {
					fileOutputStream.write(buffer, 0, count);
				}
				fileOutputStream.close();
				bufferedInputStream.close();
			} catch (IOException e) {
			}
		} catch (FileNotFoundException e) {
		}

	}

}
