package com.zhangqin.framework.web.gpe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhangqin.framework.web.gpe.enums.BoolValue;
import com.zhangqin.framework.web.gpe.enums.DockType;
import com.zhangqin.framework.web.gpe.enums.TextAlign;

/**
 * 
 * ClassName: GpeField 
 * @Description: GPE字段注解</br>
 * “only primitive type, String, Class, annotation, enumeration are permitted or 1-dimensional arrays thereof”</br>
 * 因无法知道开发者真的设置注解值false，还是使用的默认值false，故使用BoolEnum代替boolean。
 * @author zhangqin
 * @date 2017年9月21日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GpeField {
	/**
	 * 
	 * @Description: 字段标题
	 * @return String  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	String title() default "";

	/**
	 * 
	 * @Description: 是否必须字段
	 * @return BoolValue  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	BoolValue must() default BoolValue.NULL;

	/**
	 * 
	 * @Description: 是否隐藏
	 * @return BoolValue 
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	BoolValue hidden() default BoolValue.NULL;

	/**
	 * 
	 * @Description: 水平对齐方式
	 * @return AlignEnum  
	 * @author zhangq
	 * @date 2017年9月21日
	 */
	TextAlign align() default TextAlign.NULL;

	/**
	 * 
	 * @Description: 网格是否显示
	 * @return BoolValue  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	BoolValue gshow() default BoolValue.NULL;

	/**
	 * 
	 * @Description: 是否打印
	 * @return BoolValue  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	BoolValue pshow() default BoolValue.NULL;

	/**
	 * 
	 * @Description: 是否导出
	 * @return BoolValue  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	BoolValue eshow() default BoolValue.NULL;

	/**
	 * 
	 * @Description: 网格宽度，0.自动，-1.默认值
	 * @return int
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	int gwidth() default -1;

	/**
	 * 
	 * @Description: 打印宽度,0.自动、-1使用列表宽度
	 * @return int 
	 * @author zhangq
	 * @date 2017年9月21日
	 */
	int pwidth() default -1;

	/**
	 * 
	 * @Description: 导出宽度,0.自动、-1使用列表宽度
	 * @return int  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	int ewidth() default -1;

	/**
	 * 
	 * @Description: 网格格式化
	 * @return String  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	String gformat() default "";

	/**
	 * 
	 * @Description: 打印格式化，为空时默认使用列表格式化
	 * @return String  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	String pformat() default "";

	/**
	 * 
	 * @Description: 导出格式化，为空时默认使用列表格式化
	 * @return String 
	 * @author zhangqin
	 * @date 2017年3月28日
	 */
	String eformat() default "";

	/**
	 * 
	 * @Description: 是否支持排序
	 * @return BoolValue  
	 * @author zhangq
	 * @date 2017年9月21日
	 */
	BoolValue sortable() default BoolValue.NULL;

	/**
	 * 
	 * @Description: 字段显示顺序
	 * @return int 
	 * @author zhangq
	 * @date 2017年9月21日
	 */
	int sort() default 0;

	/**
	 * 
	 * @Description: 列合并标题</br>
	 * 相同ctitle且排列连续的列，自动进行colspan。</br>
	 * 当存在有colspan时，没有colspan则自动进行rowspan。
	 * @return String  
	 * @author zhangq
	 * @date 2017年9月21日
	 */
	String ctitle() default "";

	/**
	 * 
	 * @Description: 是否冻结列
	 * @return boolean  
	 * @author zhangq
	 * @date 2017年11月16日
	 */
	boolean frozen() default false;

	/**
	 * 
	 * @Description: 扩展坞集合
	 * @return DockType[]  
	 * @author zhangqin
	 * @date 2017年12月14日
	 */
	DockType[] docks() default {};
	
	/**
	 * 是否存需要计算合计,仅针对数值类型有效
	 * @return
	 */
	boolean sum() default false;
}