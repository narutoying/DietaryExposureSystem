/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWriter {
	// ����cell���������ĸ�λ�ֽڽض�
	private static short XLS_ENCODING = HSSFCell.ENCODING_UTF_16;

	// ���Ƹ�������ʽ
	private static String NUMBER_FORMAT = "#,##0.00";

	// �������ڸ�ʽ
	private static String DATE_FORMAT = "yyyy-MM-dd"; // "m/d/yy h:mm"

	private OutputStream out = null;

	private HSSFWorkbook workbook = null;

	private HSSFSheet sheet = null;

	private HSSFRow row = null;

	public ExcelWriter() {
	}

	/**
	 * ��ʼ��Excel
	 * 
	 */
	public ExcelWriter(OutputStream out) {
		this.out = out;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}

	/**
	 * ����Excel�ļ�
	 * 
	 * @throws IOException
	 */
	public void export() throws FileNotFoundException, IOException {
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new IOException(" ���ɵ���Excel�ļ�����! ", e);
		} catch (IOException e) {
			throw new IOException(" д��Excel�ļ�����! ", e);
		}

	}

	/**
	 * ����һ��
	 * 
	 * @param index
	 *            �к�
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * ��ȡ��Ԫ���ֵ
	 * 
	 * @param index
	 *            �к�
	 */
	public String getCell(int index) {
		HSSFCell cell = this.row.getCell((short) index);
		String strExcelCell = "";
		if (cell != null) { // add this condition
			// judge
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_FORMULA:
				strExcelCell = "FORMULA ";
				break;
			case HSSFCell.CELL_TYPE_NUMERIC: {
				strExcelCell = String.valueOf(cell.getNumericCellValue());
			}
				break;
			case HSSFCell.CELL_TYPE_STRING:
				strExcelCell = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strExcelCell = "";
				break;
			default:
				strExcelCell = "";
				break;
			}
		}
		return strExcelCell;
	}

	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index
	 *            �к�
	 * @param value
	 *            ��Ԫ�����ֵ
	 */
	public void setCell(int index, int value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index
	 *            �к�
	 * @param value
	 *            ��Ԫ�����ֵ
	 */
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // �����µ�cell��ʽ
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // ����cell��ʽΪ���Ƶĸ�������ʽ
		cell.setCellStyle(cellStyle); // ���ø�cell����������ʾ��ʽ
	}

	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index
	 *            �к�
	 * @param value
	 *            ��Ԫ�����ֵ
	 */
	public void setCell(int index, String value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value);
	}

	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index
	 *            �к�
	 * @param value
	 *            ��Ԫ�����ֵ
	 */
	public void setCell(int index, Calendar value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value.getTime());
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // �����µ�cell��ʽ
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // ����cell��ʽΪ���Ƶ����ڸ�ʽ
		cell.setCellStyle(cellStyle); // ���ø�cell���ڵ���ʾ��ʽ
	}

	public void test() {
		File f = new File("/sdcard/test.xls");
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e1) {
			}
		}
		ExcelWriter e = new ExcelWriter();

		try {
			e = new ExcelWriter(new FileOutputStream(f));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		e.createRow(0);
		e.setCell(0, "������� ");
		e.setCell(1, "����");
		e.setCell(2, "��ֵ");
		e.setCell(3, "�Ѷ�");
		e.setCell(4, "����");
		e.setCell(5, "֪ʶ��");

		e.createRow(1);
		e.setCell(0, "t1");
		e.setCell(1, 1);
		e.setCell(2, 3.0);
		e.setCell(3, 1);
		e.setCell(4, "��Ҫ");
		e.setCell(5, "רҵ");

		try {
			e.export();
			//	   System.out.println(" ����Excel�ļ�[�ɹ�] ");
		} catch (IOException ex) {
			//	   System.out.println(" ����Excel�ļ�[ʧ��] ");
			ex.printStackTrace();
		}
	}

}