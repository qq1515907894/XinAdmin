package com.wuxin;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

/**
 * <p>
 * 启动类
 * <p>
 *
 * @author wuxin
 * @className WuXinApplication
 * @create 2024-04-27 11:13
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.wuxin","com.wuxin.web"})
public class WuXinApplication {

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void printAllMappings() {
		Map<?, ?> map = applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
		map.forEach((key, value) -> System.out.println(key));
	}

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(WuXinApplication.class);
		application.setApplicationStartup(new BufferingApplicationStartup(2048));
		application.run(args);
		System.out.println("(♥◠‿◠)ﾉﾞ  无心轻量后台管理系统启动成功   ლ(´ڡ`ლ)ﾞ");
		System.out.println("http://localhost:8888");

	}
}
