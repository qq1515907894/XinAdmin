package com.wuxin.exeption;

import lombok.Data;
import lombok.Getter;

/**
 * <p>
 * 自定义异常类
 * <p>
 *
 * @author wuxin
 * @className BusinessException
 * @create 2025-03-12 16:11
 * @Version 1.0
 */
@Getter
public class BusinessException extends RuntimeException{
	private int code;  // 错误码

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

}
