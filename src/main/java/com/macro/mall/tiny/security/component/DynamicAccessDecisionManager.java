package com.macro.mall.tiny.security.component;

import cn.hutool.core.collection.CollUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 动态权限决策管理器，用于判断用户是否有访问权限
 * Created by macro on 2020/2/7.
 */
public class DynamicAccessDecisionManager implements AccessDecisionManager {
    private static final Logger logger = LoggerFactory.getLogger(DynamicAccessDecisionManager.class);

    //只有不是白名单及options的请求才会到该方法，首先会拿到all资源集，然后比对
    //authentication未set值，会抛出错（两个地方会set值：登录成功后会set+jwt过滤器中token有值且有效会set）
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当接口未被配置资源时直接放行
        if (CollUtil.isEmpty(configAttributes)) {
            logger.info("该url在resourceUrl表中没配，放行");
            return;
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            //将访问所需资源或用户拥有资源进行比对
            String needAuthority = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) { //authentication若之前没有set值会报错
                if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
                    logger.info("configAttributes中有元素={}，与当前用户有权限的元素={}相等，放行",needAuthority.trim(),grantedAuthority.getAuthority());
                    logger.info("当前的请求上下文中Authentication.getAuthorities()值:"+grantedAuthority+",与资源表中配置的configAttributes集合中其中一元素.getAttribute()值:"+needAuthority.trim()+"。匹配一致，放行");
                    return;
                }
            }
        }
        logger.warn("sorry,无权限");
        throw new AccessDeniedException("抱歉，您没有访问权限");//抛出AccessDeniedException异常后，由安全config的restfulAccessDeniedHandler()处理
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
