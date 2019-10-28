package com.example.srm.utils;

import okhttp3.*;
import okhttp3.Request.Builder;
import okhttp3.internal.framed.Header;

import java.io.IOException;
import java.util.List;



public class HttpUtil {
	private static OkHttpClient client = new OkHttpClient();
	
	public static String get(String url) throws IOException{
		return get(url, 0);
	}
	
	public static String get(String url, Header... headers) throws IOException{
		return get(url, 0, headers);
	}

	/**
	 * 
	 * @param url 请求地址
	 * @param deep  重定向深度
	 * @param headers  添加请求头
	 */
	public static String get(String url, int deep, Header... headers) throws IOException{
		Builder builder = new Request.Builder().url(url);
		if(headers != null && headers.length > 0){
			for(Header header : headers){
				builder.addHeader(header.name.toString(), header.value.toString());
			}
		}
		Request request = builder.build();
		Response response = client.newCall(request).execute();
		if(response.isSuccessful()){
			return response.body().string();
		}else{
			if(response.code() >= 300 && response.code() <= 400) {
				String location = response.header("Location");
				if(location != null && deep < 3){
					return get(location, ++deep, headers);
				}				
			}
			throw new IOException("Unexpected code" + response.code());
		}
	}
	
	public static final MediaType MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");
	
	/**
	 * post提交json数据
	 */
	public static String postByJSON(String url, String json) throws IOException{
		return postByJSON(url, json, 0);
	}
	/**
	 * post提交json数据
	 */
	public static String postByJSON(String url, String json, Header... headers) throws IOException{
		return postByJSON(url, json, 0, headers);
	}
	/**
	 * post提交json数据
	 * @param url  请求地址
	 * @param json json格式的请求参数
	 * @param deep  重定向深度
	 * @param headers  请求头
	 */
	public static String postByJSON(String url, String json, int deep, Header... headers) throws IOException{
		RequestBody body = RequestBody.create(MEDIATYPE_JSON, json);
		Builder builder = new Request.Builder().url(url).post(body);
		if(headers != null && headers.length > 0){
			for(Header header : headers) {
				builder.addHeader(header.name.toString(), header.value.toString());
			}
		}
		Request request = builder.build();
		Response response = client.newCall(request).execute();
		if(response.isSuccessful()) {
			return response.body().string();
		}else {
			if(response.code() >= 300 && response.code() < 400) {
				String location = response.header("Location");
				if(location != null && deep < 3) {
					return postByJSON(location, json, ++deep, headers);
				}
			}
			throw new IOException("Unexpected code" + response.code());
		}
	}
	/**
	 * post提交键值对数据
	 */
	public static String postByRequestBody(String url, List<NameValuePair> params) throws IOException{
		return postByRequestBody(url, 0, params);
	}
	/**
	 * post提交键值对数据
	 */
	public static String postByRequestBody(String url, List<NameValuePair> params, Header... headers) throws IOException{
		return postByRequestBody(url, 0, params, headers);
	}
	/**
	 * post提交键值对数据
	 * @param url  请求地址
	 * @param deep  重定向深度
	 * @param params  请求参数
	 * @param headers  请求头
	 */
	public static String postByRequestBody(String url, int deep, List<NameValuePair> params, Header... headers) throws IOException{
		okhttp3.FormBody.Builder builder = new FormBody.Builder();
		if(params != null) {
			for(NameValuePair param : params) {
				builder.add(param.name, param.value);
			}
		}
		RequestBody body = builder.build();
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.build();
		Response response = client.newCall(request).execute();
		if(response.isSuccessful()) {
			return response.body().string();
		}else {
			if(response.code() >= 300 && response.code() < 400) {
				String location = response.header("Location");
				if(location != null && deep < 3) {
					return postByRequestBody(location, ++deep, params, headers);
				}
			}
			throw new IOException("Unexpected code" + response);
		}
	}
	public static class NameValuePair{
		String name;
		String value;
		public NameValuePair() {
		}		
		public NameValuePair(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}
}
