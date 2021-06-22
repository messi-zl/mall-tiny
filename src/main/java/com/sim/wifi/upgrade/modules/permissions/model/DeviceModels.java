package com.sim.wifi.upgrade.modules.permissions.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zl
 * @since 2021-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_models")
@ApiModel(value="DeviceModels对象", description="")
public class DeviceModels implements Serializable {

    private static final long serialVersionUID=1L;

    private Date createdOn;

    private Date changedOn;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String checkMode;

    private Integer checkPeriod;

    private Integer newVersionCheckPeriod;

    private Boolean allowUpgrade;

    private Boolean checkPiracy;

    private Integer upgradeLimit;

    private String upgradeType;

    private String upgradeRules;

    private Integer globalStatistics;

    private String description;

    private Integer createdByFk;

    private Integer changedByFk;


}
