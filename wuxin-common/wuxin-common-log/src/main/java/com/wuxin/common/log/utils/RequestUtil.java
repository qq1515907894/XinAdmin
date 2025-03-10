package com.wuxin.common.log.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>
 * 请求工具类
 * <p>
 *
 * @author wuxin
 * @className RequestUtil
 * @create 2025-03-10 15:08
 * @Version 1.0
 */
public class RequestUtil {
	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		return "Client IP address: " + ipAddress.split(",")[0];
	}
}
