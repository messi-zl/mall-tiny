package com.macro.mall.tiny.config;

import com.macro.mall.tiny.modules.ums.model.UmsResource;
import com.macro.mall.tiny.modules.ums.service.UmsAdminService;
import com.macro.mall.tiny.modules.ums.service.UmsResourceService;
import com.macro.mall.tiny.modules.ums.service.impl.UmsAdminServiceImpl;
import com.macro.mall.tiny.security.component.DynamicSecurityService;
import com.macro.mall.tiny.security.config.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mall-security模块相关配置
 * Created by macro on 2019/11/9.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(MallSecurityConfig.class);

    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsResourceService resourceService;

    @Bean
    public UserDetailsService userDetailsService() { //JwtAuthenticationTokenFilter中才会用到
        //获取登录用户信息
        logger.info("在自己安全config中定义UserDetailsService的bean");
        return username -> adminService.loadUserByUsername(username); //userDetailsService(): SpringSecurity定义的核心接口，用于根据用户名获取用户信息，需要自行实现
    }

    //动态权限 有则用，没有则不用
    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                logger.info("在自己安全config中，定义DynamicSecurityService的Bean,并重写其loadDataSource方法，得到全部的resourceUrl");
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resourceList = resourceService.list();
                if (!resourceList.isEmpty()){
                    logger.info("现资源表中数据：："+resourceList.toString());
                }
                for (UmsResource resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":zl：" + resource.getName()));//这里的写法需要与自己UserDetalis中对应
                }
                for (String resourceUrl:map.keySet()) {
                    logger.info("动态权限key:"+resourceUrl);
                    logger.info("动态权限key对应的value:"+map.get(resourceUrl));
                }
                return map; //返回(所有资源url集)
            }
        };
    }
}
