package com.ahucoding.rocket.mcpserver.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author roylin
 * @since 2025/4/14 14:48
 */
@Data
@TableName("hhweb_video_app_instrument_panel_play_entertainment_m")
public class EntertainmentDo {

    private String sceneType;
    @TableField("video_length_type")
    private String videoType;
    @TableField("con_class_1")
    private String classType;
    private String pid;
    private String pname;
    private String publishDate;
    private Double uv;
    private Double vv;
    private float ts;
    private float avgTs;
    private String periodId;


}
