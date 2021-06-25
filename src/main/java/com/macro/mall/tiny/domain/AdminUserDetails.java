package com.macro.mall.tiny.domain;

import com.macro.mall.tiny.modules.ums.model.UmsAdmin;
import com.macro.mall.tiny.modules.ums.model.UmsResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 * Created by macro on 2018/4/26.
 */
public class AdminUserDetails implements UserDetails { //UserDetails:SpringSecurity定义用于封装用户信息的类（主要是用户信息和权限），需要自行实现,Spring Security所使用的用户
                                                       //JwtAuthenticationTokenFilter中会被使用到
    private UmsAdmin umsAdmin;
    private List<UmsResource> resourceList;
    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsResource> resourceList) {
        this.umsAdmin = umsAdmin;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//角色和权限是两回事,重写getAuthorities方法，将用户的角色和权限关联起来
        //写法类似这样
/*      Collection<SimpleGrantedAuthority> collection = new ArrayList<>();
        for (UmsResource role :resourceList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getId()+":"+role.getName());
            collection.add(simpleGrantedAuthority);
        }
        return collection;*/
        //返回当前用户的角色
        return resourceList.stream()
                .map(role ->new SimpleGrantedAuthority(role.getId()+":"+role.getName()))  //role其实就是resourceList的迭代名
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
