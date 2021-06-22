package com.sim.wifi.upgrade.modules.permissions.service.impl;

import com.sim.wifi.upgrade.modules.permissions.model.AbUserRole;
import com.sim.wifi.upgrade.modules.permissions.mapper.AbUserRoleMapper;
import com.sim.wifi.upgrade.modules.permissions.service.AbUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zl
 * @since 2021-06-22
 */
@Service
public class AbUserRoleServiceImpl extends ServiceImpl<AbUserRoleMapper, AbUserRole> implements AbUserRoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbUserRoleServiceImpl.class);
}
