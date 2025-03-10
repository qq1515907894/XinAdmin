package life.wuxin.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 *
 * <p>
 *
 * @author wuxin
 * @className GlobalExceptionHandler
 * @create 2024-05-03 15:10
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	//.....拦截异常方法
	/**
	 * 缺少请求体异常处理器
	 * @param e 缺少请求体异常 使用get方式请求 而实体使用@RequestBody修饰
	 */
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	// public R parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
	// 	String requestURI = httpServletRequest.getRequestURI();
	// 	log.error("请求地址'{}',请求体缺失'{}'", requestURI, e.getMessage());
	// 	return ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR.getCode(), sysError);
	// }
}
