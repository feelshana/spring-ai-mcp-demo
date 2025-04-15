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



	@Tool(description = "通过产品名，查询用户数据，如查询咪咕视频的活跃用户")
	public List<AllUserDo> getUsers(
			@ToolParam(description = "咪咕系的产品名称") String productName,
			@ToolParam(description = "月份,未提及则为上月") String month
	) {
		QueryWrapper<AllUserDo> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(productName)){
			wrapper.lambda().eq(AllUserDo::getProductName , productName);
		}
		if(StringUtils.isNotBlank(month)){
			wrapper.lambda().eq(AllUserDo::getPeriodId , month);
		}
		return allUserMapper.selectList(wrapper);

	}


}
