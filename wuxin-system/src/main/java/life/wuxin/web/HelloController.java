package life.wuxin.web;

import org.springframework.web.bind.annotation.GetMapping;
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

@RestController
public class HelloController {
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello, World!";
	}
}
