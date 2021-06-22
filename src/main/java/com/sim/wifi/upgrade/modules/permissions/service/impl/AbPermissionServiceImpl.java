package com.sim.wifi.upgrade.modules.permissions.service.impl;

import com.sim.wifi.upgrade.modules.permissions.model.AbPermission;
import com.sim.wifi.upgrade.modules.permissions.mapper.AbPermissionMapper;
import com.sim.wifi.upgrade.modules.permissions.service.AbPermissionService;
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
public class AbPermissionServiceImpl extends ServiceImpl<AbPermissionMapper, AbPermission> implements AbPermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbPermissionServiceImpl.class);
}
