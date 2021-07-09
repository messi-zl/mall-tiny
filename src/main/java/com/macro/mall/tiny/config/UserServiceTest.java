package com.macro.mall.tiny.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Description:
 * @CreateBy: li.zheng871@sim。com
 * @DateTime: 2021/7/5
 */
public class UserServiceTest implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
