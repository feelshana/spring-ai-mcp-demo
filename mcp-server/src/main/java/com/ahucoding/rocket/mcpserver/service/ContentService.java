package com.ahucoding.rocket.mcpserver.service;

import com.ahucoding.rocket.mcpserver.dataobject.AllUserDo;
import com.ahucoding.rocket.mcpserver.dataobject.EntertainmentDo;
import com.ahucoding.rocket.mcpserver.mapper.AllUserMapper;
import com.ahucoding.rocket.mcpserver.mapper.EntertainmentMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ContentService {

    @Autowired
    private EntertainmentMapper entertainmentMapper;
    @Autowired
    private SqlSession sqlSession;


//    @Tool(description = "查询咪咕视频文娱类内容的播放数据" +
//            "返回的pname字段代表内容名，classType代表场景名，videoType代表内容类型，classType代表一级分类，ts代表播放时长(分钟)," +
//            "不指定年，问月时，需要使用current_time工具获取当前的时间，判断正确的年份，如果没有指定时间，那么默认用今年的二月作为时间" +
//            "")
//    public List<EntertainmentDo> ListEntertainment(
//            @ToolParam(description = "播放月份,未提及则为今年2月,今年是2025年，格式为yyyyMM") String month,
//            @ToolParam(description = "一级分类，包含：电视剧、电影、综艺、短剧、综艺晚会。未提及则不传，不要乱传") String classType,
//            @ToolParam(description = "内容名") String pname
////            @ToolParam(description = "上映日期,格式为yyyyMMdd,查询范围时，传两个值,代表开始日期与结束日期") List<String> publishDate
//    ) {
//        QueryWrapper<EntertainmentDo> wrapper = new QueryWrapper<>();
//        if (StringUtils.isBlank(month)) {
//            month = "202502";
//        }
//        wrapper.lambda().eq(EntertainmentDo::getPeriodId, month);
//
//        if (StringUtils.isBlank(classType)) {
//            classType = "全部";
//        }
//        wrapper.lambda().eq(EntertainmentDo::getClassType, classType);
//        if (StringUtils.isNotBlank(pname)) {
//            wrapper.lambda().eq(EntertainmentDo::getPname, pname);
//        }
//

    ////        if (!CollectionUtils.isEmpty(publishDate)) {
    ////            if (StringUtils.isNotBlank(publishDate.get(0))) {
    ////                wrapper.lambda().gt(EntertainmentDo::getPublishDate, publishDate.get(0));
    ////            }
    ////            if (publishDate.size() > 1 && publishDate.get(1) != null) {
    ////            }
    ////            wrapper.lambda().lt(EntertainmentDo::getPublishDate, publishDate.get(1));
    ////        }
//
//
//        return entertainmentMapper.selectList(wrapper);
//
//    }

    @Tool(description = "咪咕视频/视频文娱内容月明细表的DDL语句，只包含具体文娱的播放数据，注意: 分析整体播放情况时候不要使用，" +
            "只有涉及到查询具体文娱内容/查询某个分类下的热门文娱内容/文娱内容top10等，需要展示具体内容名称的时候调用" )
    public String getEntertainmentTableSql() {

        return "###建表语句" +
                "CREATE TABLE `hhweb_video_app_instrument_panel_play_entertainment_m` (\n" +
                "  `scene_type`  COMMENT '场景',\n" +
                "  `video_length_type` COMMENT '内容类型',\n" +
                " `is_auto_play`  COMMENT '是否自动启播',\n"+
                " `is_preload`  COMMENT '是否预加载',\n"+
                "  `con_class_1`  COMMENT '一级分类',包含：电视剧、电影、综艺、短剧、综艺晚会，sql条件只能为如上值\n" +
                "  `pid`  COMMENT 'PID',  \n" +
                "  `pname` COMMENT '内容名',\n" +
                "  `publish_date` varchar(100) DEFAULT NULL COMMENT '上线日期',\n" +
                "  `uv` COMMENT '播放用户数',\n" +
                "  `vv` COMMENT '播放次数',\n" +
                "  `ts` COMMENT '播放时长(分钟)',\n" +
                "  `avg_ts` COMMENT '人均播放时长(分钟)',\n" +
                "  `period_id` COMMENT '日期,格式为yyyyMM'\n" +
                ")\n"+
                "###注意事项：" +
                "1.任何情况下，sql查询需要加上条件is_auto_play='全部' and is_preload='全部'";
    }


    @Tool(description = "咪咕视频/视频体育内容月明细表的DDL语句，只包含具体体育赛事的明细播放数据，注意：分析整体播放情况时候不要使用，" +
            "只有涉及到查询具体体育赛事/某个分类下的热门体育内容。需要展示具体内容名称的时候调用" +
            "")
    public String getSportTableSql() {

        return "###建表语句：" +
                "CREATE TABLE `hhweb_video_app_instrument_panel_play_sport_m` ( \n" +
                "  `scene_type`  COMMENT '场景',\n" +
                " `is_auto_play`  COMMENT '是否自动启播',\n"+
                " `is_preload`  COMMENT '是否预加载',\n"+
                "  `con_class_1` COMMENT '一级分类',\n" +
                "  `video_length_type`  COMMENT '内容类型',包含：全部、直播、长视频、短视频，sql条件只能为如上值\n" +
                "  `media_mame`  COMMENT '体育赛事名，查询的时候需排查掉'全部'这个名称,\n" +
                "  `uv` bigint  COMMENT '播放用户数',\n" +
                "  `vv` bigint COMMENT '播放次数',\n" +
                "  `ts` bigint COMMENT '播放时长(分钟)',\n" +
                "  `avg_ts` bigint COMMENT '人均播放时长(分钟)',\n" +
                "  `period_id` COMMENT '日期，格式为yyyyMM' \n" +
                ");\n "+
                "###注意事项" +
                "1.任何情况下，sql查询需要加上条件is_auto_play='全部' and is_preload='全部'" +
                "2.任何情况下，sql查询需要加上条件video_length_type='全部'，提及到具体值时进行替换";
    }

//    @Tool(description = "查询咪咕视频内容播放统计表的DDL语句，查询统计数据的时候使用，该表不涉及具体的内容，只包含各个维度的统计数据，" +
//            "维度值如下：" +
//            "内容类型：长视频/短视频/直播" +
//            "会员属性1：第三方支付/新模式会员" +
//            "会员属性2：体育会员/VIP会员"+
//            "会员属性3：NBA会员/UFC会员/中超/五大联赛/体育通包/其他会员/其他体育会员/意甲/法甲/英超/西甲/足球通会员"
//    )
//    public String getOverallTableSql() {
//
//        return "CREATE TABLE `hhweb_video_app_instrument_panel_play_overview_merge_m` (\n" +
//                "  `scene_type` COMMENT '场景',\n" +
//                "  `video_length_type` COMMENT '内容类型',\n" +
//                "  `is_auto_play` COMMENT '是否自动启播，查询的时候必须加上is_auto_play='全部'',\n" +
//                "  `is_preload`  COMMENT '是否预加载，查询的时候必须加上is_preload='全部''\n"+
//                "  `member_type` COMMENT '会员类型',查询的时候必须加上member_type='全部'\n" +
//                "  `member_attr1` COMMENT '会员属性1',\n" +
//                "  `member_attr2` COMMENT '会员属性2',\n" +
//                "  `member_attr3` COMMENT '会员属性3',\n" +
//                "  `uv` COMMENT '播放用户数',\n" +
//                "  `vv` COMMENT '播放次数',\n" +
//                "  `ts` COMMENT '播放时长(分钟)',\n" +
//                "  `avg_ts` COMMENT '人均播放时长(分钟)',\n" +
//                "  `period_id` COMMENT '日期，格式为yyyyMM' \n" +
//                ")";
//    }

    @Tool(description = "咪咕视频/视频的播放情况日报表的DDL，该表不包含内容名称，只包含内容的各个维度的统计数据，查热门内容/具体播放内容/播放内容TOP时不能使用该表（涉及到具体内容名），" +
            "查询天播放情况就使用该表（如查询体育内容xxx天播放数据，不涉及内容名称，涉及到内容维度的统计）" +
            "注意事项！：表中的scene_type_two/scene_type_three/scene_type_four/scene_type_five/con_class_1_name/play_type_name维度中，都包含了一条数据'全部'，查询维度具体值时需要将" +
            "该维度赋值为具体值，其他的维度条件必须赋值为'全部'，如查询大屏时，scene_type_two='大屏'，上述的各个维度字段='全部'；当不指定维度值时，所有维度值都需要赋值为全部，以便查出来为一条数据"
    )
    public String getOverallDayTableSql() {

        return "CREATE TABLE `hhweb_video_scene_play_sport_d` (\n" +
                " `scene_type` COMMENT '场景一级分类',\n" +
                " `scene_type_two` COMMENT '场景二级分类',包含:全部/大屏/小屏\n" +
                " `scene_type_three` COMMENT '场景三级分类',包含:APP/SDK/全部/公网/家庭/小程序/网页/H5\n" +
                " `scene_type_four` COMMENT '场景四级分类,包含：APK/H5/WWW/全部/其他类APP/内部触点SDK/小程序/手机APP',\n" +
                " `scene_type_five` COMMENT '场景五级分类',包含：H5/PC客户端/WWW/三方APK/全部/咪咕视频APP/咪咕视频PAD客户端/咪视界/咪视通/界客户端/咪视通分省版(咪咕极速)/头显(Launcher/APK)/头条小程序/嵌入其他子公司SDK/嵌入省公司SDK(掌厅/网厅等)/微信小程序/快手小程序/百度小程序/移动云VR APP/车载客户端(APK)/音箱客户端(APK)\n" +
                " `play_type_name` COMMENT '播放类型',包含：全部/回看/未知/点播/直播\n" +
                " `con_class_1_name` COMMENT '一级分类'," +
                "包含：/MV/PGC/体育/全部/军事/动漫/娱乐/少儿/广告/微短剧/搞笑/新闻资讯/旅游/未知/汽车/游戏电竞/猎奇/生活/电影/电视剧/直播/知识/科技/纪实/综合/综艺/财经/音乐\n" +
                " `idx_uv` int(11) COMMENT '播放用户数',\n" +
                " `idx_vv` int(11) COMMENT '播放次数',\n" +
                " `duration` double COMMENT '播放时长(分钟)',\n" +
                " `per_duration`  COMMENT '人均播放时长(分钟)',\n" +
                " `period_id` COMMENT '日期(yyyyMMdd)'\n" +
                " )\n"+
                "###注意事项" +
                "1：表中的scene_type_two/scene_type_three/scene_type_four/scene_type_five/con_class_1_name/play_type_name维度中，都包含了一条数据'全部'，查询维度具体值时需要将\" +\n" +
                "            \"该维度赋值为具体值，其他未提及的维度条件必须赋值为'全部'，如查询大屏时，scene_type_two='大屏'，上述的各个维度字段='全部'；当不指定维度值时，所有维度值都需要赋值为全部，以便查出来为一条数据\"";
    }


    @Tool(description = "咪咕视频/视频的播放情况月报表的DDL语句，该表不包含内容名称，只包含内容的各个维度的统计数据，查热门内容/具体播放内容/播放内容TOP时不能使用该表（涉及到具体内容名），" +
            "查询月播放情况就使用该表（如查询体育内容xxx月播放数据，不涉及内容名称，涉及到内容维度的统计）" +
            "注意事项！：表中的scene_type_two/scene_type_three/scene_type_four/scene_type_five/con_class_1_name/play_type_name维度中，都包含了一条数据'全部'，查询维度具体值时需要将" +
            "该维度赋值为具体值，其他的维度条件必须赋值为'全部'，如查询大屏时，scene_type_two='大屏'，上述的各个维度字段='全部'；当不指定维度值时，所有维度值都需要赋值为全部，以便查出来为一条数据"
    )
    public String getOverallMonthTableSql() {

        return "CREATE TABLE `hhweb_video_scene_play_sport_m` (\n" +
                " `scene_type` COMMENT '场景一级分类',\n" +
                " `scene_type_two` COMMENT '场景二级分类',包含:全部/大屏/小屏\n" +
                " `scene_type_three` COMMENT '场景三级分类',包含:APP/SDK/全部/公网/家庭/小程序/网页/H5\n" +
                " `scene_type_four` COMMENT '场景四级分类,包含：APK/H5/WWW/全部/其他类APP/内部触点SDK/小程序/手机APP',\n" +
                " `scene_type_five` COMMENT '场景五级分类',包含：H5/PC客户端/WWW/三方APK/全部/咪咕视频APP/咪咕视频PAD客户端/咪视界/咪视通/界客户端/咪视通分省版(咪咕极速)/头显(Launcher/APK)/头条小程序/嵌入其他子公司SDK/嵌入省公司SDK(掌厅/网厅等)/微信小程序/快手小程序/百度小程序/移动云VR APP/车载客户端(APK)/音箱客户端(APK)\n" +
                " `play_type_name` COMMENT '播放类型',包含：全部/回看/未知/点播/直播\n" +
                " `con_class_1_name` COMMENT '一级分类'," +
                "包含：/MV/PGC/体育/全部/军事/动漫/娱乐/少儿/广告/微短剧/搞笑/新闻资讯/旅游/未知/汽车/游戏电竞/猎奇/生活/电影/电视剧/直播/知识/科技/纪实/综合/综艺/财经/音乐\n" +
                " `idx_uv` int(11) COMMENT '播放用户数',\n" +
                " `idx_vv` int(11) COMMENT '播放次数',\n" +
                " `duration` double COMMENT '播放时长(分钟)',\n" +
                " `per_duration`  COMMENT '人均播放时长(分钟)',\n" +
                " `period_id` COMMENT '日期(yyyyMM)'\n" +
                " )\n"+
                "注意事项!：表中的scene_type_two/scene_type_three/scene_type_four/scene_type_five/con_class_1_name/play_type_name维度中，都包含了一条数据'全部'，查询维度具体值时需要将\" +\n" +
                "            \"该维度赋值为具体值，其他未提及的维度条件必须赋值为'全部'，如查询大屏时，scene_type_two='大屏'，上述的各个维度字段='全部'；当不指定维度值时，所有维度值都需要赋值为全部，以便查出来为一条数据\"";
    }



    @Tool(description = "查询咪咕视频/视频的订购/付费表的DDL语句，该表不包含内容名称，只包含各个维度的订购/付费指标，查热门内容/具体播放内容/播放内容TOP时不能使用该表（涉及到具体内容名），" +
            "查询XX分类的订购/付费情况就使用该表（不涉及内容名称，涉及到内容维度的统计）" +
            "注意事项！：表中的con_class_1/member_type/pay_sourc/member_attr维度中，都包含了一条数据'全部'，province_name维度中包含了一条数据'全国',查询维度具体值时需要将" +
            "该维度赋值为具体值，其他未提及的维度条件必须赋值为'全部'或'全国'"
    )
    public String getSportOrderTableSql() {

        return "CREATE TABLE `hhweb_video_app_instrument_panel_play_sport_order_m` ( \n" +
                "  `scene_type` COMMENT '场景',\n" +
                "  `con_class_1` COMMENT '一级分类',\n" +
                "  `member_type` COMMENT '会员属性,包含：全部/存量/新增',\n" +
                "  `pay_source`  COMMENT '付费来源,包含：全部/新模式会员/第三方支付',\n" +
                "  `member_attr`  COMMENT '会员属性,包含：全部/VIP会员/体育会员',\n" +
                "  `province_name` COMMENT '省份',\n" +
                "  `member_list`  COMMENT '赛事会员分类,包含：全部/NBA会员/UFC会员/中超/五大联赛/体育通包/其他会员/其他体育会员/意甲/法甲/英超/西甲/足球通会员',\n" +
                "  `order_uv`  COMMENT '订购会员数',\n" +
                "  `uv` bigint  COMMENT '会员且播放用户数',\n" +
                "  `period_id`  COMMENT '日期，格式为yyyyMM''  \n" +
                ") ";
    }






    @Tool(description = "执行SQL语句,返回结果数据集，注意:只能执行一个sql，多次执行需要多次调用")
    public List<Map<String, Object>> executeQuery(String  sql) {

        // 校验SQL合法性
        validateSql(sql);

        // 执行查询
        return sqlSession.selectList("DynamicQuery.executeDynamicQuery", sql);
    }

    private void validateSql(String sql) {
        String trimmedSql = sql.trim().toUpperCase();
        // 仅允许SELECT语句，且禁止敏感操作
        if (!trimmedSql.startsWith("SELECT") ||
                trimmedSql.matches("(?i).*\\b(DROP|DELETE|INSERT|UPDATE|ALTER|CREATE|TRUNCATE)\\b.*")) {
            throw new SecurityException("非法SQL语句");


        }
    }
}
