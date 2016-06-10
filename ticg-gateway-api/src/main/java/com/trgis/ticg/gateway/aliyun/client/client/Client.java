package com.trgis.ticg.gateway.aliyun.client.client;

import org.apache.http.HttpResponse;

import com.trgis.ticg.gateway.aliyun.client.util.HttpUtil;

public class Client {
	/**
	 * 发送请求
	 *
	 * @param request
	 *            request对象
	 * @return HttpResponse
	 * @throws Exception
	 */
	public static HttpResponse execute(Request request) throws Exception {
		switch (request.getMethod()) {
		case GET:
			return HttpUtil.httpGet(request.getUrl(), request.getHeaders(), request.getAppKey(), request.getAppSecret(),
					request.getTimeout(), request.getSignHeaderPrefixList());
		case POST_FORM:
			return HttpUtil.httpPost(request.getUrl(), request.getHeaders(), request.getFormBody(), request.getAppKey(),
					request.getAppSecret(), request.getTimeout(), request.getSignHeaderPrefixList());
		case POST_STRING:
			return HttpUtil.httpPost(request.getUrl(), request.getHeaders(), request.getStringBody(),
					request.getAppKey(), request.getAppSecret(), request.getTimeout(),
					request.getSignHeaderPrefixList());
		case POST_BYTES:
			return HttpUtil.httpPost(request.getUrl(), request.getHeaders(), request.getBytesBody(),
					request.getAppKey(), request.getAppSecret(), request.getTimeout(),
					request.getSignHeaderPrefixList());
		case PUT_FORM:
			return HttpUtil.httpPut(request.getUrl(), request.getHeaders(), request.getFormBody(), request.getAppKey(),
					request.getAppSecret(), request.getTimeout(), request.getSignHeaderPrefixList());
		case PUT_STRING:
			return HttpUtil.httpPut(request.getUrl(), request.getHeaders(), request.getStringBody(),
					request.getAppKey(), request.getAppSecret(), request.getTimeout(),
					request.getSignHeaderPrefixList());
		case PUT_BYTES:
			return HttpUtil.httpPut(request.getUrl(), request.getHeaders(), request.getBytesBody(), request.getAppKey(),
					request.getAppSecret(), request.getTimeout(), request.getSignHeaderPrefixList());
		case DELETE:
			return HttpUtil.httpDelete(request.getUrl(), request.getHeaders(), request.getAppKey(),
					request.getAppSecret(), request.getTimeout(), request.getSignHeaderPrefixList());
		default:
			throw new IllegalArgumentException(String.format("unsupported method:%s", request.getMethod()));
		}
	}
}
