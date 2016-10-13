/** 
 * @(#)viewHistorylogShortListController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import com.cicl.frame.core.web.editor.DatePropertyEditor;
import com.cicl.frame.core.web.editor.TimestampPropertyEditor;

/**
 * Class AbstractAnnotationController
 * <ul>
 * <li>annotation support
 * <li>1) Annotation mapping conventional support, like conventional controller class name.
 * <li>2) Annotation @InitBinder function, global binding for all subclass.
 * <li>3) Annotation @ExceptionHandler function, global exception handling for all subclass.
 * </ul>
 * 
 * <ul>
 * 
 * <li>1) url mapping
 * <ul>
 * <li>a)默认按照命名约定映射,如/demoajax/list.do 映射到DemoAjaxController的list方法
 * <li>b)如果需要按照特定的命名映射，在类或者方法前注解@RequestMapping("specialurl")
 * </ul>
 * <li>2) view resolve
 * <ul>
 * <li>a).do表示是要返回由jsp+jstl渲染的html页面，.json表示是要返回jackson渲染的json字符串.
 * <p>
 * 返回HTML
 * </p>
 * <li>b)函数return null表示返回与请求同名的view,如/demoajax/list.do,则等同于/demoajax/list的view
 * name.
 * <li>c)函数返回String表示返回对应名字的view.
 * <li>d)函数返回ModelAndView对象, 使用ModelAndView的viewName属性按照b和c的规则返回view.
 * <p>
 * 返回文件
 * </p>
 * <li>e)函数return void表示直接写response返回.
 * <p>
 * 返回json
 * </p>
 * <li>f)函数如果注解了@ResponseBody，则把return的Object渲染为json字符串返回客户端.
 * </ul>
 * <li>3) validation
 * <ul>
 * <li>a)常规约束校验:字段长度、是否可空、基于正则的表达式校验等 注解@Valid，在调用前由spring容器按照JSR-303校验(使用
 * {@link org.hibernate.validato.HibernateValidator}).
 * <li>b)业务校验:用户是否注册了，email是否注册了等 调用自定义的Validator进行业务校验。
 * 
 * <li>最后统一进行是否存在错误信息判断，并渲染为json字符串返回客户端，按全局或者字段错误信息显示.
 * </ul>
 * <li>4) Type Conversion/formatting
 * <ul>
 * <p>
 * request
 * </p>
 * <li>a)Http request-->form bean, 定义全局 @DataBinder函数, 由PropertyEditor按照特定格式反序列化
 * <p>
 * response
 * </p>
 * <li>a)model-->html, 由<fmt的tag标签格式化
 * <li>b)model/Java object-->json, 定义全局ObjectMapper对象, 由Serializer按照特定格式序列化
 * </ul>
 * <li>5) CRUD(增删查改)基本接口命名约定：
 * <ul>
 * <li>list.do 显示列表页面
 * <li>search.json 查询列表操作
 * <li>list2csv.do 列表转换为csv文件
 * <li>show.do 显示对象detai的页面
 * <li>edit.do 修改对象的输入界面
 * <li>update.json 修改对象操作
 * <li>add.do 新增对象的输入界面
 * <li>save.json 新增对象操作
 * <li>remove.json 删除对象操作
 * <li>//listprint.do 打印列表页面
 * <li>//list2xls.do 列表转换为xls文件
 * <li>//load.json 查询对象detail操作
 * <li>//print.do 显示对象detail的print页面
 * </ul>
 * 
 * <li>6) i18n国际化
 * <ul>
 * <li>a)jstl view由 fmt:message的tag实现
 * <li>b)javascript控件由自带的国际化文件实现
 * <li>c)validation message，由Validator实现getMessage接口
 * </ul>
 * <li>7) CoC约定优于配置
 * <ul>
 * <li>1)mapping： 参见第1点
 * <li>2)binding: {@link org.springframework.core.Conventions}
 * <li>3)model key: set model, set json
 * {@link org.springframework.core.Conventions}
 * <li>4)message key: field, error message key
 * <li>5)bean rejection: 所有单例的bean都是使用注解,命名约定 
 * </ul>
 * </ul>
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-3 下午03:30:56
 */


public class AbstractAnnotationController extends AbstractMessageSourceAwareController{
	private static final Log LOG = LogFactory.getLog(AbstractAnnotationController.class);
	/*
	 * Conventional method name declare, subclass can redefine
	 */
	public static final String METHOD_LIST = "list";
	public static final String METHOD_SHOW = "show";
	public static final String METHOD_ADD = "add";
	public static final String METHOD_EDIT = "edit";
	public static final String METHOD_RELOAD = "reload";
	
	public AbstractAnnotationController() {
		super();
	}

	/**
	 * Determine the conventional class type name
	 * <code>Class</code> 
	 * <code>com.myapp.ProductController</code> becomes <code>product</code>;
	 * <code>com.myapp.MyProductController</code> becomes <code>myproduct</code>;
	 * @param clazz
	 * @return
	 */
	public static String getControllerShortName(Class<?> clazz) {
		return ClassUtils.getShortName(clazz).replaceFirst("Controller", "").toLowerCase();
	}
	
	/**
	 * Determine the conventional class type name for url mapping
	 * <code>Class</code> 
	 * <li><code>com.myapp.ProductController</code> becomes <code>/product/*</code>;
	 * <li><code>com.myapp.MyProductController</code> becomes <code>/myproduct/*</code>;
	 * @param clazz
	 * @return
	 */
	public static String getClassMapping(Class<?> clazz) {
		return "/" + getControllerShortName(clazz) + "/*";
	}

	/**
	 * <li><code>product</code> becomes <code>/product/*</code>;
	 * <li><code>myproduct</code> becomes <code>/myproduct/*</code>;
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getClassMapping(String classShortName) {
		return "/" + classShortName + "/*";
	}

	public static String getMethodMapping(Class<?> clazz, String method) {
		return "/" + getControllerShortName(clazz) + "/" + method;
	}
	
	public static String getMethodMapping(String prefolder, Class<?> clazz, String method) {
		return prefolder + getMethodMapping(clazz, method);
	}

	public static String getMethodMapping(String classShortName, String method) {
		return "/" + classShortName + "/" + method;
	}

	/**
	 * Registe global propertyEditor
	 *
	 * @param binder
	 * @param request
	 */
	@SuppressWarnings("unused")
	@InitBinder
	private void initBinder(WebDataBinder binder, WebRequest request) {
		// Type Conversion
		DatePropertyEditor.registerCustomEditor(binder);
		TimestampPropertyEditor.registerCustomEditor(binder);
	}

	/**
	 * Global exception handler
	 * 
	 * @param e
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	@ExceptionHandler
	private String handleException(Exception e, HttpServletRequest request) {
		LOG.error(e.getMessage(), e);
		request.setAttribute("exception", e);
		return "error";
	}
}
