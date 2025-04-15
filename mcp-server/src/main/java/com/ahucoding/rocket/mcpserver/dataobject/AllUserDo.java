package com.ahucoding.rocket.mcpserver.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author roylin
 * @since 2025/4/14 14:48
 */
@Data
@TableName("hhapp_2025_chatbi_active1_m")
public class AllUserDo {

    private String productName;
    private String provinceName;
    @TableField("pid2_2022")
    private String pid22022;
    private String channelLevel2;
    private String activeUsers;
    private String idxYyRate;
    private String idxMmRate;
    private String periodId;


}
