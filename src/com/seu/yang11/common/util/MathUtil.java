/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util;

import java.math.BigDecimal;

/**
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: MathUtil.java, v 0.1 2012-5-13 ����3:50:23 narutoying Exp $
 */
public class MathUtil {

	public static void main(String[] args) {

		double a = 2.0;
		double b = 5.0;
		double c = divide(a, b, 2);
		System.out.println(addZero(c) + "%");

		double d = divide(a, 1, 2);
		System.out.println(addZero(d) + "%");

	}

	public static double divide(double v1, double v2, int scale) {
		if (v2 == 0.0) {
			return 0.0;
		}
		double c = div(v1, v2, scale + 2);
		// System.out.println(c+"%");
		return div(c * 100.0, 1, scale);
	}

	/**
	 * �ṩ����?����?�ĳ���?�㡣��?�������������?����scale����ָ �����ȣ��Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @param scale
	 *            ��ʾ��ʾ��Ҫ��?��С�����Ժ�λ��
	 * @return ?����������
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * �ṩ��?��С��λ��������?��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * @param scale
	 *            С���������λ
	 * @return ����������?��
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String addZero(double v) {
		StringBuffer s = null;
		String temp = String.valueOf(v);
		//String[] tempArray = temp.split(".");
		int point = temp.indexOf(".");
		int length = temp.length();
		int n = length - 1 - point;

		if (n > 0) {
			s = new StringBuffer(temp);
			for (int i = 0; i < n; i++) {
				s.append(0);
			}
			return s.toString();
		} else {
			return temp;
		}

	}
}
