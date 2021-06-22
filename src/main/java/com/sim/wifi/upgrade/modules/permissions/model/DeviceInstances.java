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
@TableName("device_instances")
@ApiModel(value="DeviceInstances对象", description="")
public class DeviceInstances implements Serializable {

    private static final long serialVersionUID=1L;

    private Date createdOn;

    private Date changedOn;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String model;

    private String callId;

    private String mac;

    private String sn;

    private String uuid;

    private String hardVersion;

    private String softVersion;

    private Integer run;

    private Integer manual;

    private Integer doing;

    private Integer createdByFk;

    private Integer changedByFk;


}
