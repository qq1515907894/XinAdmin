package life.wuxin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

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
@SpringBootApplication
public class WuXinApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(WuXinApplication.class);
		application.setApplicationStartup(new BufferingApplicationStartup(2048));
		application.run(args);
		System.out.println("(♥◠‿◠)ﾉﾞ  无心轻量后台管理系统启动成功   ლ(´ڡ`ლ)ﾞ");
		System.out.println("http://localhost:8888");

	}
}
