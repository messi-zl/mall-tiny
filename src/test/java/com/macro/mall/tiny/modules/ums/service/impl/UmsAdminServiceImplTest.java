package com.macro.mall.tiny.modules.ums.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UmsAdminServiceImplTest {

    @Test
    void login() {
        UmsAdminServiceImpl umsAdminService = new UmsAdminServiceImpl();
        umsAdminService.login("admin","111");
    }
}