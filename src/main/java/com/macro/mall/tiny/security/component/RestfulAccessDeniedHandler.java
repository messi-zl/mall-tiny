package com.macro.mall.tiny.security.component;

import cn.hutool.json.JSONUtil;
import com.macro.mall.tiny.common.api.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义返回结果：没有权限访问时
 * Created by macro on 2018/4/26.
 */
//用来解决认证过的用户访问无权限资源时的异常
//Spring默认的AccessDeniedHandler中只有对页面请求的处理，而没有对Ajax的处理。而在项目开发是Ajax又是我们要常用的技术，
// 所以我们可以通过自定义AccessDeniedHandler来处理Ajax请求。我们在Spring默认的AccessDeniedHandlerImpl上稍作修改就可以了。
public class RestfulAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(CommonResult.forbidden(e.getMessage())));
        response.getWriter().flush();
    }
}
