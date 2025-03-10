package com.wuxin.common.log.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 日志实体
 * <p>
 *
 * @author wuxin
 * @className SysUserLog
 * @create 2025-03-10 14:48
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserLog {
	// 请求ID
	private String requestId;
	// 会话ID
	private String sessionId;
	// 渠道
	private String channel;
	// 名称
	private String name;
	// 动作
	private String action;
	// URL
	private String url;
	// 参数
	private String params;
	// IP地址
	private String ip;
	// 错误信息
	private String errMsg;
	// 日志时间
	private Date logTime;
}
