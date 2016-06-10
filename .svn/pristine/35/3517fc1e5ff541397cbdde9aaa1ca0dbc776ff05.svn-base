package com.trgis.ticg.gateway.aliyun.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.trgis.ticg.gateway.aliyun.client.client.Client;
import com.trgis.ticg.gateway.aliyun.client.client.Request;
import com.trgis.ticg.gateway.aliyun.client.constant.Constants;
import com.trgis.ticg.gateway.aliyun.client.constant.HttpHeader;
import com.trgis.ticg.gateway.aliyun.client.constant.HttpSchema;
import com.trgis.ticg.gateway.aliyun.client.enums.Method;
import com.trgis.ticg.gateway.exception.GatewayMethodParameterException;
import com.trgis.ticg.gateway.exception.GatewayPathVarcharException;

/**
 * 阿里云网关接口实现
 * 
 * @author zhangqian
 *
 */
@Service
public class AliyunAPIClient {

	private static final Logger log = LoggerFactory.getLogger(AliyunAPIClient.class);

	// APP KEY
	private final static String APP_KEY = "23381188";
	// APP密钥
	private final static String APP_SECRET = "973ea7d33ce650173472181705e8dd51";
	// API域名
	private final static String HOST = "api.trmap.cn";

	private static final String PARAMSTART = "{{";

	private static final String PARAMEND = "}}";

	/**
	 * 调用网关
	 * 
	 * @param path
	 * @param params
	 * @param method
	 * @return 
	 * @throws Exception
	 */
	public String invoke(String path, Map<String, String> params, Method method) throws Exception {
		log.debug("invoke method {}:{}", path, method);
		if (method != null && StringUtils.isNotBlank(path)) {
			switch (method) {
			case GET:
				String url = getUrl(path, params);
				Map<String, String> headers = new HashMap<String, String>();
				// （可选）响应内容序列化格式,默认application/json,目前仅支持application/json
				headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");

				Request request = new Request(Method.GET, HttpSchema.HTTP + HOST + url, APP_KEY, APP_SECRET,
						Constants.DEFAULT_TIMEOUT);
				request.setHeaders(headers);

				// 调用服务端
				HttpResponse response = Client.execute(request);
				String json = readStreamAsStr(response.getEntity().getContent());
				return json;
			default:
			}

		} else {
			throw new GatewayMethodParameterException("网关API调用参数错误。");
		}
		return null;
	}

	/**
	 * 生成get请求的链接
	 * 
	 * 链接分为2部分，路径参数和请求参数
	 * 
	 * @param path  /poisearch/{{name}}/{{id}}
	 * @param params
	 * @return
	 * @throws GatewayPathVarcharException
	 */
	private String getUrl(String path, Map<String, String> params) throws GatewayPathVarcharException {
		String regex = "\\{\\{[0-9a-zA-Z]*\\}\\}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(path);
		while (m.find()) {
			String key = m.group();
			String pureKey = StringUtils.removeEndIgnoreCase(StringUtils.removeStartIgnoreCase(key, PARAMSTART),
					PARAMEND);
			String value = StringUtils.trimToEmpty(params.get(pureKey));
			if (StringUtils.isBlank(value)) {
				log.error("网关请求地址解析时参数错误。{}:{}", path, params);
				throw new GatewayPathVarcharException("网关请求链接地址解析时参数错误。");
			}
			path = StringUtils.replace(path, key, value);
			params.remove(pureKey);
		}
		log.info("path varchar replace complete.");
		StringBuffer sbf = new StringBuffer(path);
		if (!params.isEmpty()) {
			log.info("also has params:{}.go on append url params.", params);
			if (!StringUtils.endsWith(sbf, "\\?")) {
				sbf.append("?");
			}

			int count = params.size();
			if (count == 1) {
				String key = params.keySet().iterator().next();
				String value = params.get(key);
				sbf.append(key + "=" + value);
			} else if (count > 1) {
				Iterator<String> paramIter = params.keySet().iterator();
				boolean isFirst = true;
				while (paramIter.hasNext()) {
					if (!isFirst) {
						sbf.append("&");
					} else {
						isFirst = false;
					}
					String key = paramIter.next();
					String value = params.get(key);
					sbf.append(key + "=" + value);
				}
			}
		}
		log.info("path done.");
		return sbf.toString();
	}

	/**
	 * 打印Response
	 *
	 * @param response
	 * @throws IOException
	 */
	private static void print(HttpResponse response) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(response.getStatusLine().getStatusCode()).append(Constants.LF);
		for (Header header : response.getAllHeaders()) {
			sb.append(header.toString()).append(Constants.LF);
		}
		sb.append(readStreamAsStr(response.getEntity().getContent())).append(Constants.LF);
		System.out.println(sb.toString());
	}

	/**
	 * 将流转换为字符串
	 *
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readStreamAsStr(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		WritableByteChannel dest = Channels.newChannel(bos);
		ReadableByteChannel src = Channels.newChannel(is);
		ByteBuffer bb = ByteBuffer.allocate(4096);

		while (src.read(bb) != -1) {
			bb.flip();
			dest.write(bb);
			bb.clear();
		}
		src.close();
		dest.close();

		return new String(bos.toByteArray(), Constants.ENCODING);
	}

	public static void main(String[] args) throws GatewayPathVarcharException {

	}

}
