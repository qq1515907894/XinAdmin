package life.wuxin.web;

import life.wuxin.common.log.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
public class HelloController {

	@Log()
	@GetMapping("/hello")
	public String sayHello() {
		log.info("hello world");
		return "Hello, World!";
	}

	@Log()
	@PostMapping("/hay")
	public String sayHello(String hay) {
		return "后台识别到已打开：" + hay;
	}

	@Log()
	@PostMapping("/me")
	public TestUser me(@RequestBody TestUser testUser) {
		return testUser;
	}
}
