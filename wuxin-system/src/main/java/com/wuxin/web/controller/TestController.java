package com.wuxin.web.controller;

import com.wuxin.common.log.annotation.Log;
import com.wuxin.exeption.BusinessException;
import com.wuxin.web.domain.UserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 测试集控制类
 * <p>
 *
 * @author wuxin
 * @className TestController
 * @create 2025-03-12 16:17
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

	@Log
	@GetMapping("/business")
	public void throwBusinessException() {
		throw new BusinessException(4001, "业务逻辑错误测试");
	}

	@GetMapping("/nullpointer")
	public void throwNullPointerException() {
		throw new NullPointerException("空指针异常");
	}

	@PostMapping("/validate")
	public void validateRequest(@Valid @RequestBody UserRequest request) {
		// 仅用于测试参数校验异常
	}
}
