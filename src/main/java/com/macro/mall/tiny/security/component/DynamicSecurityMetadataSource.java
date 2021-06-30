package com.macro.mall.tiny.security.component;

import cn.hutool.core.util.URLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 动态权限数据源，用于获取动态权限规则
 * Created by macro on 2020/2/7.
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static final Logger logger = LoggerFactory.getLogger(DynamicSecurityMetadataSource.class);

    private static Map<String, ConfigAttribute> configAttributeMap = null;
    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    @PostConstruct
    public void loadDataSource() {
        logger.info("加载configAttributeMap……");
        configAttributeMap = dynamicSecurityService.loadDataSource();
        logger.info("加载configAttributeMap完成,configAttributeMap={}",configAttributeMap.toString());

    }

    public void clearDataSource() {
        if (configAttributeMap !=null) configAttributeMap.clear();//避免下面报空指针
        configAttributeMap = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        clearDataSource();//不然不会再去loadDataSource的
        if (configAttributeMap == null) this.loadDataSource();//configAttributeMap("所有resourceUrl","id：zl：name")
        List<ConfigAttribute>  configAttributes = new ArrayList<>();
        //获取当前访问的路径
        String url = ((FilterInvocation) o).getRequestUrl();
        logger.info("当前request的url={}",url);
        String path = URLUtil.getPath(url);
        logger.info("当前url的path={}",path);
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();//map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()))
        //获取访问该路径所需资源
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            if (pathMatcher.match(pattern, path)) { //key值匹配，则将value add进来
                logger.info("当前请求路径={},与configAttributeMap的key值={}，匹配，add all value",path,pattern);
                configAttributes.add(configAttributeMap.get(pattern));
            }
        }
        // 未设置操作请求权限，返回空集合。request的url能在所有resourceUrl中找到则add进来
        logger.info("configAttributes={}",configAttributes.toString());
        return configAttributes;//("id:zl:name")
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
