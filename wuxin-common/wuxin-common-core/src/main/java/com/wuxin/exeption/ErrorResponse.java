package com.wuxin.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 统一异常响应格式
 * <p>
 *
 * @author wuxin
 * @className ErrorResponse
 * @create 2025-03-12 16:12
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	private int code;
	private String message;
	private LocalDateTime timestamp;

	public ErrorResponse(int code, String message) {
		this.code = code;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

}
