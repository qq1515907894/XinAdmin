package com.wuxin.web.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuxin.web.domain.User;
import com.wuxin.web.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * <p>
 *
 * @author wuxin
 * @className UserServiceImpl
 * @create 2025-03-13 14:46
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
