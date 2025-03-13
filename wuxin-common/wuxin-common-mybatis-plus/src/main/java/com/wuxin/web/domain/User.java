package com.wuxin.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 用户类
 * <p>
 *
 * @author wuxin
 * @className User
 * @create 2025-03-13 14:42
 * @Version 1.0
 */
@Data
@TableName("user") // 对应数据库表
public class User {
	@TableId(value = "id", type = IdType.AUTO) // 主键自增
	private Long id;
	private String name;
	private Integer age;
	private String email;
}
