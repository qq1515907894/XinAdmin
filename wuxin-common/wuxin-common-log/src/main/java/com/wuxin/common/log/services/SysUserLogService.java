package com.wuxin.common.log.services;

import com.wuxin.common.log.domain.SysUserLog;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志存储服务
 * <p>
 *
 * @author wuxin
 * @className SysUserLogService
 * @create 2025-03-10 14:46
 * @Version 1.0
 */
@Service
public class SysUserLogService {
	public void insert(SysUserLog log) {
		// 这里可以对接 MySQL、MongoDB、Elasticsearch 等存储
		System.out.println("保存日志: " + log);
	}
}
