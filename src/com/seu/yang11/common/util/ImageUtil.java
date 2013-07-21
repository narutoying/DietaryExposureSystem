/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util;

import android.content.Context;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: ImageUtil.java, v 0.1 2012-8-27 обнГ9:44:18 narutoying Exp $
 */
public class ImageUtil {
	public static int getImageId(Context context, String imageName) {
		return context.getResources().getIdentifier("drawable/" + imageName,
				null, context.getPackageName());
	}
}
