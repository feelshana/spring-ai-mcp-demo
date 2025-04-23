package com.ahucoding.rocket.mcpserver.service;

import com.ahucoding.rocket.mcpserver.dataobject.AllUserDo;
import com.ahucoding.rocket.mcpserver.mapper.AllUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AllUserService  {

	@Autowired
	private AllUserMapper allUserMapper;



	@Tool(description = "查询咪咕相关产品的用户/活跃数据，提及年时，按月查询分析(data_type为month)，提及周/日时按日查询分析(data_type为day)")
	public String getUserTableSql() {

		return "CREATE TABLE `hhapp_2025_chatbi_active1_m` (\n" +
				"  `product_name` varchar(12) DEFAULT NULL,\n" +
				"  `province_name` varchar(12) DEFAULT NULL COMMENT '省份 全国',\n" +
				"  `pid2_2022` varchar(50) DEFAULT NULL COMMENT '场景，包含：APP/SDK/全部/公网/家庭/小程序/政企/网页/H5',\n" +
				"  `channel_level2` varchar(50) DEFAULT NULL COMMENT '渠道类型，包含：互联网公域流量运营/全部/公共池/复合型渠道/外部导流/广宣及MCN/省专渠道/终端预装/运营类渠道',\n" +
				"  `active_users` bigint DEFAULT NULL COMMENT '活跃用户数',\n" +
				"  `idx_yy_rate` varchar(10) DEFAULT NULL COMMENT '同比',\n" +
				"  `idx_mm_rate` varchar(10) DEFAULT NULL COMMENT '环比',\n" +
				"  `data_type` varchar(10) DEFAULT NULL COMMENT '日期类型,包含 day/month',\n" +
				"  `period_id` varchar(50) DEFAULT NULL COMMENT '日期，格式为yyyyMM(当data_type为month),yyyyMMdd(当data_type为day)', \n" +
				")";
	}


}
