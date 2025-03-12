package com.wuxin.web.domain;

/**
 * <p>
 * 用户类，测试用
 * <p>
 *
 * @author wuxin
 * @className UserRequest
 * @create 2025-03-12 16:20
 * @Version 1.0
 */
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class UserRequest {
	@NotNull(message = "用户名不能为空")
	private String username;

	@Min(value = 18, message = "年龄必须大于等于18")
	private Integer age;
}
