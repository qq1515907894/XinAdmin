package com.wuxin.common.log.aspect;

import com.wuxin.common.log.annotation.Log;
import com.wuxin.common.log.domain.SysUserLog;
import com.wuxin.common.log.services.SysUserLogService;
import com.wuxin.common.log.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * <p>
 * 全局日志管理
 * <p>
 *
 * @author wuxin
 * @className GlobalLogAspect
 * @create 2025-03-10 14:37
 * @Version 1.0
 */
@Aspect
@Component
@Configuration
public class GlobalLogAspect {

	private static final Logger logger = LoggerFactory.getLogger(GlobalLogAspect.class);
	private final SysUserLogService sysUserLogService;

	public GlobalLogAspect(SysUserLogService sysUserLogService) {
		this.sysUserLogService = sysUserLogService;
	}
	/**
	 * 切入点：拦截 Controller 方法
	 */
	@Pointcut("execution(public * com.wuxin..*Controller.*(..))")
	public void Log() {}

	/*
	  前置通知，记录请求信息
	 */
	@Before("Log()")
	public void doBefore(JoinPoint joinPoint) {
		// 获取当前请求的ServletRequestAttributes
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		// 获取当前请求的HttpServletRequest
		HttpServletRequest request = servletRequestAttributes.getRequest();

		// 获取方法签名
		Signature signature = joinPoint.getSignature();
		if (!(signature instanceof MethodSignature methodSignature)) {
			return;
		}
		Method method = methodSignature.getMethod();

		// 获取Log注解
		Log log = method.getAnnotation(Log.class);

		// 生成请求ID
		String requestId = UUID.randomUUID().toString();
		// 将请求ID放入MDC中
		MDC.put("requestId", requestId);

		// 创建日志信息字符串
		StringBuffer logInfo = new StringBuffer();
		logInfo.append("\n--- 请求日志 ---");
		if (!log.briefLog()){
			logInfo.append("\n请求ID: ").append(requestId);
			logInfo.append("\nSession: ").append(request.getSession().getId());
		}
		logInfo.append("\n访问URI: ").append(request.getRequestURI());
		logInfo.append("\n访问IP: ").append(RequestUtil.getIpAddress(request));
		logInfo.append("\n类方法: ").append(joinPoint.getSignature().getDeclaringTypeName())
				.append(".").append(joinPoint.getSignature().getName());
		// 处理参数
		Object[] args = joinPoint.getArgs();
		List<Object> filteredArgs = new ArrayList<>();

		// 获取方法参数
		Parameter[] parameters = method.getParameters();
		for (int i = 0; i < args.length; i++) {
			// 排除 HttpServletRequest 类型的参数
			if (!(args[i] instanceof HttpServletRequest)) {
				// 获取参数名称
				String paramName = parameters[i].getName();
				// 获取参数类型（方法签名的声明类型）
				String paramType = parameters[i].getType().getSimpleName(); // 获取声明的类型

				if (!log.lineFeed()){
					// 记录参数名、声明类型和参数值
					filteredArgs.add(paramName + "(" + paramType + ")=" + JSONObject.toJSONString(args[i]));
				}else {
					// 记录参数名、声明类型和参数值
					filteredArgs.add("\n" + paramName + "(" + paramType + ")=" + JSONObject.toJSONString(args[i]));
				}
			}
		}

		logInfo.append("\n参数: ").append(filteredArgs);

		// 设置日志颜色
		String logMessage = logInfo.toString();
		String coloredLogMessage = "\u001B[32m" + logMessage + "\u001B[0m"; // 绿色
		logger.info(coloredLogMessage);
	}

	@AfterReturning(pointcut = "Log()", returning = "result")
	public void doAfterReturning(JoinPoint joinPoint,Object result) {
		handleLog(joinPoint, null, result);
	}

	/**
	 * 异常通知：方法抛出异常时记录日志
	 */
	@AfterThrowing(pointcut = "Log()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	private void handleLog(JoinPoint joinPoint, Exception e, Object result) {
		// 获取当前请求的ServletRequestAttributes
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		// 获取当前请求的HttpServletRequest
		HttpServletRequest request = servletRequestAttributes.getRequest();

		// 获取方法签名
		Signature signature = joinPoint.getSignature();
		if (!(signature instanceof MethodSignature)) {
			return;
		}
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		// 获取Log注解
		Log log = method.getAnnotation(Log.class);

		if (log != null && log.saveFlag()) {

			SysUserLog userLog = new SysUserLog();
			userLog.setRequestId(MDC.get("requestId"));
			userLog.setSessionId(request.getSession().getId());
			userLog.setChannel(log.channel());
			userLog.setName(log.name());
			userLog.setAction(signature.getDeclaringTypeName() + "." + method.getName());
			userLog.setUrl(request.getRequestURI());
			userLog.setParams(JSONObject.toJSONString(request.getParameterMap()));
			userLog.setIp(RequestUtil.getIpAddress(request));
			userLog.setLogTime(new Date());

			// 记录异常信息
			if (e != null) {
				userLog.setErrMsg(e.getMessage());

				String errorLog = "\u001B[31m" + "异常日志: " + e.getMessage() + "\u001B[0m"; // 红色
				logger.error(errorLog);
			}
			sysUserLogService.insert(userLog);
		}
			MDC.clear();
		}


}
