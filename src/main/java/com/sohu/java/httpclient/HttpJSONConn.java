package com.sohu.java.httpclient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpJSONConn {
	
	public static String post(String path,String params, String systemName){
        HttpURLConnection httpConn=null;
        BufferedReader in=null;
        PrintWriter out=null;
        try {
            URL url=new URL(path);
            httpConn=(HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("accept", "*/*");
            httpConn.setRequestProperty("connection", "Keep-Alive");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setConnectTimeout(10000);
            httpConn.setReadTimeout(10000);
            httpConn.setUseCaches(false);
            httpConn.connect();
            out = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8"));
            out.println(params);
            out.flush();

            if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){
                StringBuffer content=new StringBuffer();
                String tempStr="";
                in=new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
                while((tempStr=in.readLine())!=null){
                    content.append(tempStr);
                }
                return content.toString();
            }else{
            	System.out.println(systemName+"不工作了！！！");
            	return null;
            }
        } catch (Exception e) {
        	System.out.println(systemName+"不工作了！！！");
        	e.printStackTrace();
        }finally{
            if (in != null)
            {
                try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            if (out != null)
            {
                out.close();
            }
            if (httpConn != null)
            {
                httpConn.disconnect();
            }
        }
		return null;
    }

	public static String jsonPost(String path,String params){
        HttpURLConnection httpConn=null;
        BufferedReader in=null;
        PrintWriter out=null;
        try {
            URL url=new URL(path);
            httpConn=(HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("accept", "*/*");
            httpConn.setRequestProperty("connection", "Keep-Alive");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setConnectTimeout(10000);
            httpConn.setReadTimeout(10000);
            httpConn.setUseCaches(false);
            httpConn.connect();
            out = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8"));
            out.println(params);
            out.flush();

            if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){
                StringBuffer content=new StringBuffer();
                String tempStr="";
                in=new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
                while((tempStr=in.readLine())!=null){
                    content.append(tempStr);
                }
                return content.toString();
            }else{
            	System.out.println("不工作了！！！");
            	return null;
            }
        } catch (Exception e) {
        	System.out.println("不工作了！！！");
        	e.printStackTrace();
        }finally{
            if (in != null)
            {
                try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            if (out != null)
            {
                out.close();
            }
            if (httpConn != null)
            {
                httpConn.disconnect();
            }
        }
		return null;
    }
}
