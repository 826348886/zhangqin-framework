package com.zhangqin.framework.web.gpe.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.zhangqin.framework.common.enums.BaseEnum;
import com.zhangqin.framework.web.gpe.bean.GpeBean;
import com.zhangqin.framework.web.gpe.bean.GpeFieldBean;
import com.zhangqin.framework.web.gpe.enums.UseFor;

/**
 * GPE导出工具类
 * 
 * @author zhangqin
 *
 */
public class GpeExportUtils {
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(GpeExportUtils.class);

	/**
	 * 
	 * <p>
	 * Title: 私有构造函数
	 * </p>
	 * <p>
	 * Description: 成员方法均为静态，禁止new对象
	 * </p>
	 */
	private GpeExportUtils() {
	}

	/**
	 * 导出excel
	 * 
	 * @param clazz
	 * @param list
	 * @param response
	 */
	public static void export(Class<?> clazz, List<?> list, HttpServletResponse response) {
		// 输出流
		OutputStream out = null;

		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		try {
			// 获取GPE
			GpeBean gpe = GpeUtils.getGpeBean(clazz, UseFor.EXPORT);

			// 生成一个表格
			XSSFSheet sheet = workbook.createSheet("Sheet1");
			
			// 设置列宽度
			setColumnWidth(sheet,gpe.getFields());
			
			// 获取内容单元格样式列表，每一列一个样式
			List<XSSFCellStyle> styleList = getContentStyleList(workbook, gpe.getFields());
			
			// 表头标题
			String headerTitle = gpe.getHeader().getTitle();
			// 文件名
			String fileName = (StringUtils.isNotBlank(headerTitle) ? headerTitle : "导出文件") + "_"
					+ DateFormatUtils.format(new Date(), "yyyyMMdd");

			// 创建标题行
			int rowNo = 0;
			XSSFCellStyle headerStyle = getHeaderStyle(workbook);
			rowNo = createTitileRow(gpe, sheet, rowNo,headerStyle);

			// 创建表头行
			rowNo = createHeaderRow(gpe, sheet, rowNo,headerStyle);
			
			// 创建内容行
			rowNo = createContentRow(clazz, gpe, sheet, list, styleList, rowNo);
			
			// 输出
			out = response.getOutputStream();
			response.reset();
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
			response.setContentType("application/msexcel");
			workbook.write(out);
		} catch (Exception e) {
			logger.error("导出失败:{}", e);
		} finally {
			try {
				if (workbook != null) {
					//workbook.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("导出失败:{}", e);
			}
		}

	}

	/**
	 * 创建标题行
	 */
	private static int createTitileRow(GpeBean gpe, XSSFSheet sheet, int rowNo, XSSFCellStyle headerStyle) {
		// 是否显示标题
		boolean isShowTitle = gpe.getHeader().isEshow();
		// 标题内容
		String headerTitle = gpe.getHeader().getTitle();
		if (isShowTitle && StringUtils.isNotBlank(headerTitle)) {
			// 创建新的行
			XSSFRow row = sheet.createRow(0);

			// 每个单元格设置样式（解决单元格没有边框的问题）
			int size = gpe.getFields().size();
			for (int i = 0; i <= size; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(headerStyle);
			}

			// 取第一个单元格填写标题和做合并处理
			XSSFCell cell = row.getCell(0);
			cell.setCellValue(new XSSFRichTextString(headerTitle));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, size));

			// 行号++
			rowNo++;
		}

		return rowNo;
	}

	/**
	 * 创建表头行
	 * 
	 * @param gpe
	 * @param sheet
	 * @param rowNo
	 */
	private static int createHeaderRow(GpeBean gpe, XSSFSheet sheet, int rowNo,XSSFCellStyle headerStyle) {
		// 转为二维数组
		List<List<GpeFieldBean>> twoDimensionalList = GpeUtils.convertToTwoDimensionalList(gpe.getFields());
		
		int rowNo1 = rowNo;
		int rowNo2 = rowNo +1;
		
		// 第1行表头
		XSSFRow row1 = sheet.createRow(rowNo1);
		rowNo++;
		
		// 第2行表头（默认不创建，存在行列合并时才创建）
		XSSFRow row2 = null;
		// 列合并计数
		int colspanCount = 0;
		// 第一个数组
		List<GpeFieldBean> list1 = twoDimensionalList.get(0);
		int skipCellCount = 0;
		// 遍历创建每一列
		for (int i = 0; i < list1.size(); i++) {
			// 获取当前字段属性
			GpeFieldBean field1 = twoDimensionalList.get(0).get(i);
			// 开始行
			int firstRow1 = rowNo1;
			// 结束行
			int lastRow1 = rowNo1 + (field1.getRowspan() == 0 ? 0 : field1.getRowspan() - 1);
			// 开始列
			int firstCol1 = i + colspanCount;
			// 结束列
			int lastCol1 = i + (field1.getColspan() == 0 ? 0 : field1.getColspan() - 1) + colspanCount;
			// 存在列合并时，更新列合并计数
			if (field1.getColspan() > 1) {
				colspanCount = colspanCount + field1.getColspan() - 1;
			}
			// 创建一个单元格
			XSSFCell cell = row1.createCell(firstCol1 + 1);
			
			XSSFRichTextString text = new XSSFRichTextString(field1.getTitle());
			cell.setCellValue(text);
			if(firstRow1!=lastRow1 || firstCol1!=lastCol1) {
				sheet.addMergedRegion(new CellRangeAddress(firstRow1, lastRow1, firstCol1 + 1, lastCol1 + 1));
			}
			cell.setCellStyle(headerStyle);
			// 当此字段为列合并后的字段时，创造第2行表头
			if (field1.getColspan() > 1) {
				// 仅当row2不存在时，创建一个新的行
				if (null == row2) {
					row2 = sheet.createRow(rowNo2);
					rowNo++;
				}
				// 第二个数组
				List<GpeFieldBean> list2 = twoDimensionalList.get(1);
				// 已经处理的合并数
				int colspanCount2 = 0;
				// 迭代第二个数组
				Iterator<GpeFieldBean> iterator = list2.iterator();
				while (iterator.hasNext()) {
					if (colspanCount2 < field1.getColspan()) {
						// 获取当前字段属性
						GpeFieldBean field2 = iterator.next();
						// 开始行
						// int firstRow2 = rowNo2;
						// 开始列
						int firstCol2 = i + colspanCount2 + skipCellCount;
						// 创建一个单元格
						XSSFCell cell2 = row2.createCell(firstCol2+1);
						XSSFRichTextString text2 = new XSSFRichTextString(field2.getTitle());
						cell2.setCellValue(text2);
						cell2.setCellStyle(headerStyle);
						//sheet.addMergedRegion(new CellRangeAddress(firstRow2, firstRow2, firstCol2, firstCol2));
						// 更新已处理的单元格
						colspanCount2++;
						// 移除当前
						iterator.remove();
					} else {
						break;
					}
				}
				skipCellCount += field1.getColspan() - 1;
			}
		}
		
		// 序号Cell
		XSSFCell rowNoCell = row1.createCell(0);
		rowNoCell.setCellValue("序号");
		if(rowNo1!=(row2 == null ? rowNo1 : rowNo1 + 1)) {
			sheet.addMergedRegion(new CellRangeAddress(rowNo1, row2 == null ? rowNo1 : rowNo1 + 1, 0, 0));
		}
		rowNoCell.setCellStyle(headerStyle);
		return rowNo;
	}

	/**
	 * 创建内容行
	 * @param clazz
	 * @param gpe
	 * @param sheet
	 * @param list
	 * @param rowNo
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static int createContentRow(Class<?> clazz,GpeBean gpe, XSSFSheet sheet, List<?> list,List<XSSFCellStyle> styleList, int rowNo) throws IllegalArgumentException, IllegalAccessException {
		Map<Integer,Object> footerMap = Maps.newHashMap();
		
		// 反射获取所有所有属性的值，保存到Map中
		Map<String, Field> fieldMap = new HashMap<String, Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			fieldMap.put(field.getName(), field);
		}
		
		// 创建明细
		int no = 1;
		for (Object t : list) {
			// 新建数据行
			XSSFRow row = sheet.createRow(rowNo);
			rowNo++;
			
			// 创建序号cell
			XSSFCell rowNoCell = row.createCell(0);
			rowNoCell.setCellValue(no++);
			rowNoCell.setCellStyle(styleList.get(0));
			
			// 循环每一列
			for (int i = 0; i < gpe.getFields().size(); i++) {
				// 获取注解字段值
				GpeFieldBean gpeField = gpe.getFields().get(i);
				Field originalField = fieldMap.get(gpeField.getField());
				
				// 枚举值找到于是的字段
				if (null == originalField && gpeField.getField().endsWith("Desc")) {
					originalField = fieldMap.get(gpeField.getField().substring(0, gpeField.getField().indexOf("Desc")));
				}
				
				// 设置单元格的值
				if (null == originalField) {
					continue;
				}
				
				// 创建单元格
				XSSFCell cell = row.createCell(i + 1);
				cell.setCellStyle(styleList.get(i + 1));
				
				Object value = originalField.get(t);
				setCellValue(cell, gpeField, originalField, value);
				// 计算合计值
				calcSumValue(gpeField, originalField, footerMap, i, value);
			}
		}
		
		// 存在合计数据，则添加合计行
		if(!footerMap.isEmpty()) {
			// 合计行
			XSSFRow footerRow = sheet.createRow(rowNo);
			
			XSSFCell rowNoCell = footerRow.createCell(0);
			rowNoCell.setCellValue("合计：");
			rowNoCell.setCellStyle(styleList.get(0));
			
			for (int i = 0; i < gpe.getFields().size(); i++) {
				XSSFCell cell = footerRow.createCell(i + 1);
				cell.setCellStyle(styleList.get(i + 1));
				if (footerMap.containsKey(i)) {
					cell.setCellValue(Double.parseDouble(footerMap.get(i).toString()));
				}
			}
		}
		return rowNo;
	}
	
	/**
	 * 设置单元格的值
	 * @param cell
	 * @param gpeField
	 * @param originalField
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	private static void setCellValue(XSSFCell cell,GpeFieldBean gpeField, Field originalField,Object value) {
		if (null == value) {
			return;
		}
		
		// 字段类型
		Class<?> type = originalField.getType();
		
		// 枚举类型
		if (BaseEnum.class.isAssignableFrom(type)) {
			try {
				BaseEnum<? extends Enum<?>, ?> en = (BaseEnum<? extends Enum<?>, ?>) value;
				if (null != en) {
					cell.setCellValue(en.getDesc());
				}
			} catch (Exception e) {
				logger.error("导出枚举值失败:{}", e);
			}
			return;
		}
		
		// 获取该字段的类型
		String typeSimpleName = originalField.getType().getSimpleName();
		
		// 根据不同的类型计算合计值
		switch (typeSimpleName) {
			case "BigDecimal":
			case "double":
			case "Double":
			case "long":
			case "Long":
			case "short":
			case "Short":
			case "int":
			case "Integer":
				// 转换double再显示，避免出现小三角
				cell.setCellValue(Double.parseDouble(value.toString()));
				break;
			case "Date":
				// 格式化日期
				Date date = (Date) value;
				SimpleDateFormat sdf = new SimpleDateFormat(gpeField.getEformat());
				cell.setCellValue(sdf.format(date));
				break;
			default:
				// 其他类型均当做字符串处理
				cell.setCellValue(value.toString());
				break;
		}
	}
	
	/**
	 * 计算合计值
	 * @param gpeField
	 * @param originalField
	 * @param footerMap
	 * @param cellIndex
	 * @param value
	 */
	private static void calcSumValue(GpeFieldBean gpeField, Field originalField, Map<Integer, Object> footerMap, int cellIndex,
			Object value) {
		// 是否需要统计合计
		if (!gpeField.isSum()) {
			return;
		}
		
		// 获取该字段的类型
		String typeSimpleName = originalField.getType().getSimpleName();
		
		// 根据不同的类型计算合计值
		switch (typeSimpleName) {
			case "BigDecimal":
				if (footerMap.containsKey(cellIndex)) {
					BigDecimal originalDecimalValue = (BigDecimal) footerMap.get(cellIndex);
					BigDecimal augendDecimalValue = (BigDecimal) value;
					footerMap.put(cellIndex, originalDecimalValue.add(augendDecimalValue));
				} else {
					footerMap.put(cellIndex, value);
				}
				break;
			case "double":
			case "Double":
				if (footerMap.containsKey(cellIndex)) {
					double originalDoubleValue = (double) footerMap.get(cellIndex);
					double augendDoubleValue = (double) value;
					footerMap.put(cellIndex, originalDoubleValue + augendDoubleValue);
				} else {
					footerMap.put(cellIndex, value);
				}
				break;
			case "long":
			case "Long":
				if (footerMap.containsKey(cellIndex)) {
					long originalLongValue = (long) footerMap.get(cellIndex);
					long augendLongValue = (long) value;
					footerMap.put(cellIndex, originalLongValue + augendLongValue);
				} else {
					footerMap.put(cellIndex, value);
				}
				break;
			case "short":
			case "Short":
				if (footerMap.containsKey(cellIndex)) {
					short originalShortValue = (short) footerMap.get(cellIndex);
					short augendShortValue = (short) value;
					footerMap.put(cellIndex, (short)(originalShortValue + augendShortValue));
				} else {
					footerMap.put(cellIndex, (short)value);
				}
				break;
			case "int":
			case "Integer":
				if (footerMap.containsKey(cellIndex)) {
					int originalIntValue = (int) footerMap.get(cellIndex);
					int augendIntValue = (int) value;
					footerMap.put(cellIndex, originalIntValue + augendIntValue);
				} else {
					footerMap.put(cellIndex, value);
				}
				break;
			default:
				break;
		}
	}
	
	/**
	 * 获取表头样式
	 * 
	 * @param workbook
	 * @return
	 */
	private static XSSFCellStyle getHeaderStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = createBasicCellStyle(workbook);
		style.setAlignment(HorizontalAlignment.CENTER);

		// 生成一个字体
		XSSFFont font = workbook.createFont();
		// 字体大小
		font.setFontHeightInPoints((short) 14);
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}
	
	/**
	 * 获取内容单元格样式列表，每一列一个样式
	 * 
	 * @param workbook
	 * @param fields
	 * @return
	 */
	private static List<XSSFCellStyle> getContentStyleList(XSSFWorkbook workbook, List<GpeFieldBean> fields) {
		// 存放每一列的样式
		List<XSSFCellStyle> styleList = new ArrayList<XSSFCellStyle>();

		// 序号列样式
		XSSFCellStyle rowNoStyle = createBasicCellStyle(workbook);
		rowNoStyle.setAlignment(HorizontalAlignment.CENTER);
		styleList.add(rowNoStyle);

		// 便利每一个列，设置样式
		for (GpeFieldBean field : fields) {
			// 生成一个样式
			XSSFCellStyle style = createBasicCellStyle(workbook);
			// 水平对其方式
			switch (field.getAlign()) {
				case LEFT:
					style.setAlignment(HorizontalAlignment.LEFT);
					break;
				case RIGHT:
					style.setAlignment(HorizontalAlignment.RIGHT);
					break;
				case CENTER:
					style.setAlignment(HorizontalAlignment.CENTER);
					break;
				default:
					style.setAlignment(HorizontalAlignment.LEFT);
					break;
			}
			// 添加到列表
			styleList.add(style);
		}
		return styleList;
	}
	
	/**
	 * 设置列宽度
	 * 
	 * @param sheet
	 * @param fields
	 */
	private static void setColumnWidth(XSSFSheet sheet, List<GpeFieldBean> fields) {
		// 设置序号列宽度
		sheet.setColumnWidth(0, 60 * 256 / 6);
		// 设置其他每一列宽度
		for (int i = 0; i < fields.size(); i++) {
			int ewidth = fields.get(i).getEwidth();
			if (ewidth > 0) {
				// api里使用一个字符的1/256的宽度作为一个单位，且最大宽度不能超过255。而ewidth的宽度是以像素做为参照的，这里进做个简单的大概的转换。
				ewidth = ewidth > 1530 ? 1530 : ewidth;
				sheet.setColumnWidth(i + 1, ewidth * 256 / 6);
			}
		}
	}
	
	/**
	 * 创建一个基础单元格样式
	 * @param style
	 */
	private static XSSFCellStyle createBasicCellStyle(XSSFWorkbook workbook) {
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 边框线条
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// 上边框
		style.setBorderTop(BorderStyle.THIN);
		// 下边框
		style.setBorderBottom(BorderStyle.THIN);
		// 左边框
		style.setBorderLeft(BorderStyle.THIN);
		// 右边框
		style.setBorderRight(BorderStyle.THIN);
		// 背景色
		style.setFillForegroundColor(IndexedColors.WHITE.index);
		// 水平对齐方式
		style.setAlignment(HorizontalAlignment.LEFT);
		// 垂直对齐方式
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}

}
