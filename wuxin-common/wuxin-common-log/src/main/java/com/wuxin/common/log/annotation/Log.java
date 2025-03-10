package com.wuxin.common.log.annotation;
import java.lang.annotation.*;

/**
 *  * 自定义操作日志记录注解
 * @author 无心
 */
@Target({ ElementType.PARAMETER,ElementType.METHOD }) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented//生成文档
public @interface Log {

		/**
		 * 渠道 (默认: "web")
		 */
		String channel() default "web";
		/**
		 * 功能名称
		 */
		String name() default "";
		/**
		 * 方法名称
		 */
		String action() default "";

		/**
		 * 是否保存到数据库 (默认: false)
		 */
		boolean saveFlag() default false;
	}
