package com.zhangqin.framework.web.importer.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.common.entity.ResponseData;
import com.zhangqin.framework.common.utils.ReflectUtils;
import com.zhangqin.framework.web.importer.annotation.ExcelCell;
import com.zhangqin.framework.web.importer.annotation.ExcelImport;

/**
 * 导出Excel模版
 * 
 * @author kun
 *
 */
public class ExportExcelTemplateMethodHandler extends AbstractImportMethodHandler<ResponseData<Boolean>> {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExportExcelTemplateMethodHandler.class);

	public ExportExcelTemplateMethodHandler(ExcelImport annotation, RequestMappingInfo mapping, String... paths) {
		super(annotation, mapping, paths);
	}

	@Override
	public ResponseData<Boolean> handler(HttpServletRequest request, HttpServletResponse response) {
		// 模版类型
		int type = 0;
		String typeString = request.getParameter("type");
		if(StringUtils.isNotBlank(typeString)) {
			type = Integer.parseInt(typeString);
		}
		
		// 获取Class
		Class<?> javaClass = getJavaClass()[type];

		// 获得所有标记ExcelCell注解的字段
		List<Field> list = ReflectUtils.getFieldList(javaClass, ExcelCell.class);

		// 导出模版中去除errorMsg
		Iterator<Field> iterator = list.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getName().equals("errorMsg")) {
				iterator.remove();
			}
		}

		// 导出模版
		exportTemplate(list, response);
		return null;
	}

	/**
	 * 导出Excel模版
	 * 
	 * @param list
	 * @param response
	 */
	private void exportTemplate(List<Field> list, HttpServletResponse response) {
		// 输出流
		OutputStream out = null;

		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();

		try {
			// 生成一个表格
			XSSFSheet sheet = workbook.createSheet("Sheet1");

			// 创建新的行
			XSSFRow row = sheet.createRow(0);

			// 遍历字段列表，绘制表头
			for (int i = 0; i < list.size(); i++) {
				// 获取注解
				ExcelCell annotation = list.get(i).getAnnotation(ExcelCell.class);

				// 创建单元格
				XSSFCell cell = row.createCell(i);
				cell.setCellValue(new XSSFRichTextString(annotation.title()));
			}

			// 标题
			String title = getAnnotation().title();
			// 文件名
			String fileName = (StringUtils.isNotBlank(title) ? title : "模版文件") + "_"
					+ DateFormatUtils.format(new Date(), "yyyyMMdd");

			// 输出
			out = response.getOutputStream();
			response.reset();
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
			response.setContentType("application/msexcel");
			workbook.write(out);
		} catch (Exception e) {
			logger.error("导出模版失败:{}", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("导出失败:{}", e);
			}
		}

	}

}
