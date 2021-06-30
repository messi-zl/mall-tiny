package com.macro.mall.tiny.security.component;

import com.macro.mall.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 * Created by macro on 2018/4/26.
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private UserDetailsService userDetailsService; //:SpringSecurity定义的核心接口，用于根据用户名获取用户信息，需要自行实现
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    //每次客户端请求都会经过这个方法
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        logger.info("JwtAuthenticationTokenFilter中进入doFilterInternal方法");
        String authHeader = request.getHeader(this.tokenHeader);//Authorization作为request的header
        logger.info("JwtFilter中拿到request中{}的值={}",this.tokenHeader,authHeader);
        logger.info("对request.getHeader(tokenHeader),拿到authHeader："+authHeader);
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {//第一次登录的时候不会进入该方法,登录成功以后获取了token才会进入该方法
            logger.info("若authHeader有值且以指定的tokenHead开头");
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer " 客户端传入的token
            logger.info("对authHeader进行截取（tokenHead不要），得到token值："+authToken);
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            logger.info("JwtAuthenticationTokenFilter中由授权token");
            logger.info("由request中token得到该用户名={}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("其token未过期，故上下文设置Authentication");
                    logger.info("JwtAuthenticationTokenFilter检查token是否正常未更改，那么上下文setAuthentication");
                    SecurityContextHolder.getContext().setAuthentication(authentication); //set容器里面authentication值
                }
            }
        }
        chain.doFilter(request, response);//会去调用DynamicSecurityFilter的doFilter方法。因为在项目启动的时候DynamicSecurityFilter已经在SecurityConfig加载完
    }
}
