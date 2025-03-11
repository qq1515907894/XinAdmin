package com.wuxin.web.domain;

import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @author wuxin
 * @className TestUser
 * @create 2024-04-27 15:53
 * @Version 1.0
 */
@Data
public class TestUser {
	// 用户名
	private String name;
	// 年龄
	private Integer age;
	// 性别
	private String sex;
	// 地址
	private String address;
	// 电话
	private String phone;
	// 邮箱
	private String email;
	// 身份证
	private String idCard;
	// 备注
	private String remark;
	// 创建时间
	private String createTime;
	// 更新时间
	private String updateTime;
	// 创建人
	private String createUser;
	// 更新人
	private String updateUser;
	// 删除标志
	private String delFlag;
	// 版本号
	private String version;
}
