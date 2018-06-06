package com.zhangqin.framework.web.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhangqin.framework.web.importer.annotation.ExcelCell;

/**
 * Excel工具类
 * 
 * @author zhangqin
 *
 */
public class ExcelUtils {
	/**
	 * OFFICE_EXCEL_2003_POSTFIX : xls格式，2003版本
	 */
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";

	/**
	 * OFFICE_EXCEL_2010_POSTFIX : xlsx格式，2007版本
	 */
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

	/**
	 * POINT : 字符"."
	 */
	public static final String POINT = ".";

	public static <T> List<T> readExcel(MultipartFile file, Class<T> clazz) {
		// 文件为空
		if (null == file || file.isEmpty()) {
			return null;
		}

		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件后缀
		String suffix = getSuffix(fileName);
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (OFFICE_EXCEL_2003_POSTFIX.equals(suffix)) { // xls(2003)格式
			return readXls(is, clazz);
		} else if (OFFICE_EXCEL_2010_POSTFIX.equals(suffix)) { // xlsx(2007)格式
			return readXlsx(is,clazz);
		}

		return null;
	}

	private static <T> List<T> readXls(InputStream is, Class<T> clazz) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			return readSheet(workbook, clazz);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static <T> List<T> readXlsx(InputStream is, Class<T> clazz) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			return readSheet(workbook, clazz);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static <T> List<T> readSheet(Workbook workbook, Class<T> clazz) {
		List<T> list = Lists.newArrayList();

		// 总sheet数
		int sheetCount = workbook.getNumberOfSheets();

		// 循环遍历sheet页
		for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {
			Sheet sheet = workbook.getSheetAt(sheetNum);
			if (sheet == null) {
				continue;
			}
			List<T> sheetList = readRow(sheet, clazz);
			list.addAll(sheetList);
		}
		return list;
	}

	private static <T> List<T> readRow(Sheet sheet, Class<T> clazz) {
		List<T> list = Lists.newArrayList();
		int lastRowNum = sheet.getLastRowNum();
		for (int rowNum = 0; rowNum <= lastRowNum; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				continue;
			}
			T t = readCell(row, clazz);
			list.add(t);
		}

		return list;
	}

	private static <T> T readCell(Row row, Class<T> clazz) {
		T obj = null;
		List<Field> fieldList = Lists.newArrayList();

		List<ExcelCell> annotationList = Lists.newArrayList();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (AnnotatedElementUtils.hasAnnotation(field, ExcelCell.class)) {
				field.setAccessible(true);
				fieldList.add(field);
				// 获取注解
				ExcelCell annotation = field.getAnnotation(ExcelCell.class);
				annotationList.add(annotation);
			}
		}

		try {
			obj = clazz.newInstance();

			for (int index = 0; index < fieldList.size(); index++) {
				// 获取单元格
				Cell cell = row.getCell(index);
				Object value = readValue(cell);

				fieldList.get(index).set(obj, value);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return obj;
	}

	private static Object readValue(Cell cell) {
		Object value = null;
		switch (cell.getCellType()) {
		// 布尔类型
		case Cell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		// 数值类型
		case Cell.CELL_TYPE_NUMERIC:
			value = getScienceNum(cell);
			break;
		default:
			value = cell.getStringCellValue();
			break;
		}
		return value;
	}

	/**
	 * @Description: 科学计数法格式化
	 * @param cell
	 * @param obj
	 * @return
	 * @author liwb
	 * @date 2016年9月17日
	 */
	private static Object getScienceNum(Cell cell) {
		Object obj = null;

		// 如果是日期类型
		if (DateUtil.isCellDateFormatted(cell)) {
			return cell.getDateCellValue();
		} else {
			// 如果是纯数字
			obj = cell.getNumericCellValue();

			// 解决数字，默认加0的问题
			if (cell.toString().endsWith(".0")) {
				obj = new DecimalFormat("#").format(Double.parseDouble(cell.toString()));
			}
		}

		// if (NumberUtils.isENum(String.valueOf(obj))) {
		// DecimalFormat df = new DecimalFormat("0");
		// obj = df.format(obj);
		// }
		return obj;
	}

	/**
	 * @Description: Data类型格式化
	 * @param cell
	 * @return
	 * @author liwb
	 * @date 2016年9月17日
	 */
	private static Object getDateValue(Cell cell) {
		Object obj;
		if (DateUtil.isCellDateFormatted(cell)) {
			obj = cell.getDateCellValue();
		} else {
			// 如果是纯数字
			obj = cell.getNumericCellValue();
		}
		return obj;
	}

	/**
	 * 获取后缀
	 * 
	 * @param fileName
	 * @return
	 */
	private static String getSuffix(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		if (fileName.contains(POINT)) {
			StringBuilder sb = new StringBuilder(fileName);
			return sb.substring(sb.lastIndexOf(POINT) + 1, sb.length());
		}
		return null;
	}
}
