package com.wuxin.web.controller;

import com.wuxin.common.log.annotation.Log;
import com.wuxin.web.domain.TestUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * <p>
 *
 * @author wuxin
 * @className HelloController
 * @create 2024-04-27 11:19
 * @Version 1.0
 */
@Slf4j
@RestController
// @CrossOrigin(origins = "*", maxAge = 3600)
public class HelloController {

	@Log()
	@GetMapping("/hello")
	public String sayHello() {
		log.info("hello world");
		return "Hello, World!";
	}

	@Log(lineFeed = true)
	@PostMapping("/hay")
	public String sayHello(String hay,int hay2,float hay3,double hay4,boolean hay5) {
		return "已收到：" + hay + hay2;
	}

	@Log()
	@PostMapping("/me")
	public TestUser me(@RequestBody TestUser testUser) {
		return testUser;
	}

	@Log(saveFlag = true)
	@GetMapping("/abnormal")
	public void abnormal() {
		throw new RuntimeException("测试异常");
	}
}
