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
@TableName("rom_softwares")
@ApiModel(value="RomSoftwares对象", description="")
public class RomSoftwares implements Serializable {

    private static final long serialVersionUID=1L;

    private Date createdOn;

    private Date changedOn;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer deviceModelId;

    private String version;

    private String displayVersion;

    private String upgradeType;

    private Integer status;

    private Date startDatetime;

    private Integer upgradeLimit;

    private Integer dailyUpgradeLimit;

    private String versionRange;

    private String areaRange;

    private String file;

    private Integer fileSize;

    private String fileMd5;

    private String englishDescription;

    private String traditionalDescription;

    private String description;

    private Integer createdByFk;

    private Integer changedByFk;

    private String downloadUrl;

    private String fileDir;


}
