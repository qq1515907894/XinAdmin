package com.wuxin.common.log.aspect;

import com.wuxin.common.log.annotation.Log;
import com.wuxin.common.log.domain.SysUserLog;
import com.wuxin.common.log.services.SysUserLogService;
import com.wuxin.common.log.utils.RequestUtil;
import com.wuxin.exeption.BusinessException;
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

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * <p> 全局日志管理 </p>
 *
 * @author wuxin
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
	public void logPointcut() {}

	/**
	 * 前置通知，记录请求信息
	 */
	@Before("logPointcut()")
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();

		Signature signature = joinPoint.getSignature();
		if (!(signature instanceof MethodSignature methodSignature)) {
			return;
		}
		Method method = methodSignature.getMethod();
		Log log = method.getAnnotation(Log.class);

		// 生成请求ID
		String requestId = UUID.randomUUID().toString();
		MDC.put("requestId", requestId);

		// 构建日志信息
		StringBuffer logInfo = new StringBuffer("\n--- 请求日志 ---");
		if (log == null || !log.briefLog()) {
			logInfo.append("\n请求ID: ").append(requestId);
			logInfo.append("\nSession: ").append(request.getSession().getId());
		}
		logInfo.append("\n访问URI: ").append(request.getRequestURI());
		logInfo.append("\n访问IP: ").append(RequestUtil.getIpAddress(request));
		String declaringTypeName = signature.getDeclaringTypeName();
		String className = declaringTypeName.replace("com.wuxin.web.controller.", "");
		logInfo.append("\n类方法: ").append(className).append(".").append(signature.getName());

		// 处理参数
		Object[] args = joinPoint.getArgs();
		List<Object> filteredArgs = new ArrayList<>();
		Parameter[] parameters = method.getParameters();
		for (int i = 0; i < args.length; i++) {
			if (!(args[i] instanceof HttpServletRequest)) {
				String paramName = parameters[i].getName();
				String paramType = parameters[i].getType().getSimpleName();
				if (log == null || !log.lineFeed()) {
					filteredArgs.add(paramName + "(" + paramType + ")=" + JSONObject.toJSONString(args[i]));
				} else {
					filteredArgs.add("\n" + paramName + "(" + paramType + ")=" + JSONObject.toJSONString(args[i]));
				}
			}
		}

		logInfo.append("\n参数: ").append(filteredArgs);
		logger.info("\u001B[32m" + logInfo + "\u001B[0m"); // 绿色日志
	}

	/**
	 * 返回通知
	 */
	@AfterReturning(pointcut = "logPointcut()", returning = "result")
	public void doAfterReturning(JoinPoint joinPoint, Object result) {
		handleLog(joinPoint, null, result);
	}

	/**
	 * 异常通知：方法抛出异常时记录日志
	 */
	@AfterThrowing(pointcut = "logPointcut()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
		System.out.println("进入异常处理逻辑: " + e.getMessage());
		handleLog(joinPoint, e, null);
	}

	/**
	 * 统一日志处理逻辑
	 */
	private void handleLog(JoinPoint joinPoint, Exception e, Object result) {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();

		Signature signature = joinPoint.getSignature();
		if (!(signature instanceof MethodSignature)) {
			return;
		}
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		Log log = method.getAnnotation(Log.class);

		SysUserLog userLog = new SysUserLog();

		// 处理异常
		if (e != null) {
			System.out.println("异常类型：" + e.getClass().getName());

			// 处理 BusinessException
			int errorCode = 5000; // 默认错误码
			String errorMessage = e.getMessage();

			if (e instanceof BusinessException businessException) {
				errorCode = businessException.getCode();
				errorMessage = businessException.getMessage();
			} else if (e.getCause() instanceof BusinessException businessException) {
				errorCode = businessException.getCode();
				errorMessage = businessException.getMessage();
			}

			userLog.setErrCode(errorCode);
			userLog.setErrMsg(errorMessage);

			logger.error("\u001B[31m进入异常处理逻辑: 错误码={}，错误信息={} \u001B[0m", errorCode, errorMessage);
		}

		if (log != null && log.saveFlag()) {
			userLog.setRequestId(MDC.get("requestId"));
			userLog.setSessionId(request.getSession().getId());
			userLog.setChannel(log.channel());
			userLog.setName(log.name());
			userLog.setAction(signature.getDeclaringTypeName() + "." + method.getName());
			userLog.setUrl(request.getRequestURI());
			userLog.setParams(JSONObject.toJSONString(request.getParameterMap()));
			userLog.setIp(RequestUtil.getIpAddress(request));
			userLog.setLogTime(new Date());



			sysUserLogService.insert(userLog);
		}
		MDC.clear();
	}



}
