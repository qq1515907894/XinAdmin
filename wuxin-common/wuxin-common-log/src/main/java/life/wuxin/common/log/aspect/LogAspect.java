package life.wuxin.common.log.aspect;

import java.lang.annotation.*;

/**
 * @Description 日志记录用户访问
 * @Author Lxj
 * @Date 2020/2/23 19:50
 * */

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import org.aspectj.lang.ProceedingJoinPoint;
import life.wuxin.common.log.annotation.Log;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 日志拦截打印实现类
 *
 * @author fulin
 * @since 2023/6/16 10:56
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

	/**
	 * 日志拦截打印实现类
	 *
	 * @param jp ProceedingJoinPoint
	 * @return 返回参数
	 */
	@Around(value = "@annotation(life.wuxin.common.log.annotation.Log)")
	@SneakyThrows
	public Object aroundLog(ProceedingJoinPoint jp) {
		Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		// 获取请求参数
		Object[] args = jp.getArgs();
		String[] parameterNames = new DefaultParameterNameDiscoverer().getParameterNames(method);
		HashMap<Object, Object> map = MapUtil.newHashMap();
		if (ArrayUtil.isNotEmpty(args)) {
			for (int i = 0; i < args.length; i++) {
				assert parameterNames != null;
				map.put(parameterNames[i], args[i]);
			}
		}
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String name = method.getName();
		log.info("拦截的方法:{}, 入参: {}", name, JSONUtil.toJsonStr(map));
		Object proceed = jp.proceed();
		log.info("拦截的方法:{}, 返参: {}", name, JSONUtil.toJsonStr(proceed));
		stopWatch.stop();
		log.info("{}方法 执行时长: {}ms", name, stopWatch.getLastTaskTimeMillis());
		return proceed;
	}
}




