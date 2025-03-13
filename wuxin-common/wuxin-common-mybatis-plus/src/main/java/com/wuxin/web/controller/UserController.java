package com.wuxin.web.controller;

import com.wuxin.web.domain.User;
import com.wuxin.web.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @author wuxin
 * @className UserController
 * @create 2025-03-13 14:47
 * @Version 1.0
 */

@RestController
@RequestMapping("/users")

public class UserController {

	@Resource
	private UserService userService;

	// 获取所有用户
	@GetMapping
	public List<User> getAllUsers() {
		return userService.list();
	}

	// 添加用户
	@PostMapping
	public boolean addUser(@RequestBody User user) {
		return userService.save(user);
	}

	// 根据 ID 查询用户
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getById(id);
	}

	// 删除用户
	@DeleteMapping("/{id}")
	public boolean deleteUser(@PathVariable Long id) {
		return userService.removeById(id);
	}
}
