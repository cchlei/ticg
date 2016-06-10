package com.trgis.ticg.gateway.demo.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trgis.ticg.demo.model.DemoUser;
import com.trgis.ticg.gateway.aliyun.client.AliyunAPIClient;
import com.trgis.ticg.gateway.aliyun.client.enums.Method;
import com.trgis.ticg.gateway.demo.api.DemoServiceGateway;

/**
 * 使用加壳的方式替换接口实现
 * 
 * @author zhangqian
 *
 */
@Service
public class DemoServiceGatewayImpl implements DemoServiceGateway {

	private static final String API_GATEWAY_POISEARCH = "/poisearch";

	@Autowired
	private AliyunAPIClient client;

	@Override
	public DemoUser findUser(String id) {
		System.out.println("This is a gateway impl");
		Map<String, String> params = new HashMap();
		params.put("query", "钟楼");
		try {
			String json = client.invoke(API_GATEWAY_POISEARCH, params, Method.GET);
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DemoUser(id);
	}

}
