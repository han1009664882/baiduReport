package com.sohu.java.httpclient;

import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		HttpClientUtil hcu = new HttpClientUtil();

		String url = "https://api.baidu.com/json/sms/v3/AccountService/getAccountInfo";
		String token = "6fa0199afe094833ce1c37e4068c581f";
		String userName = "baidu-HQ-01-2160247";
		String passWord = "gtJa070523zxJ";
		String action = "";
		JSONObject header = new JSONObject();
		header.put("token", token);
		header.put("username", userName);
		header.put("password", passWord);
		header.put("action", action);
		JSONObject body = new JSONObject();
		JSONObject params = new JSONObject();
		params.put("header", header);
		params.put("body", body);
		System.out.println(params.toString());

		// use apache http client
		try {
			System.out.println(hcu.httpPostRequest(url, params.toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// use jdk http client
		HttpJSONConn hjc = new HttpJSONConn();
		System.out.println(hjc.post(url, params.toString(), "getAccountInfo"));

	}
}
