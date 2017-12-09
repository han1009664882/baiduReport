package com.sohu.java.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

/** 
 *  
 * @author Nan 
 * 2015-11 
 */  
public class HttpClientUtil {  
    private static PoolingHttpClientConnectionManager cm;
    private static String EMPTY_STR = "";  
    private static String UTF_8 = "UTF-8";  
      
    private static void init(){  
        if(cm == null){  
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(50);//整个连接池最大连接数  
            cm.setDefaultMaxPerRoute(5);//每路由最大连接数，默认值是2  

            //创建http request的配置信息  
            //RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(NewsConstant.REQUEST_TIMEOUT)  
              //                              .setSocketTimeout(NewsConstant.REQUEST_SOCKET_TIME).build(); 
        }  
    }  
      
    /** 
     * 通过连接池获取HttpClient 
     * @return 
     */  
    private static CloseableHttpClient getHttpClient(){
        init();  
        CloseableHttpClient closedHttpClient = HttpClients.custom().setConnectionManager(cm).build();

        /*
        // 设置连接的超时时间5s  
        closedHttpClient.getHttpConnectionManager().getParams()  
                .setConnectionTimeout(connectionTimeout);  
        // 设置读取数据的超时时间8s  
        closedHttpClient.getHttpConnectionManager().getParams()  
                .setSoTimeout(soTimeout);
        //初始化httpclient客户端  
        closedHttpClient.setConnectionManager(httpClientConnectionManager)  
                        .setDefaultRequestConfig(requestConfig).setUserAgent(NewsConstant.USER_AGENT).setRedirectStrategy(redirectStrategy).build();
         */
        return closedHttpClient;  

  
    }  
    
    
    /********单例模式声明开始********************/  
    //类初始化时，自行实例化，饿汉式单例模式  
    /*
    private static final HttpClientUtil httpClient = new HttpClientUtil();  

    public static HttpClientUtil getHttpClientInstance(){  
        return httpClient;  
    }  
    */  
    /************************单例模式声明结束********/  
 
    
    /** 
     *  
     * @param url 
     * @return 
     */  
    public static String httpGetRequest(String url){  
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);  
    }  
      
    public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException{  
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);  
          
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);  
          
        HttpGet httpGet = new HttpGet(ub.build());
        return getResult(httpGet);  
    }  
      
    public static String httpGetRequest(String url, Map<String, Object> headers,   
            Map<String, Object> params) throws URISyntaxException{  
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);  
          
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);  
          
        HttpGet httpGet = new HttpGet(ub.build());
        for (Map.Entry<String, Object> param: headers.entrySet()) {  
            httpGet.addHeader(param.getKey(), param.getValue().toString());  
        }  
        return getResult(httpGet);  
    } 
    
    public static boolean httpGetRequest(String url, String fileName){  
        HttpGet httpGet = new HttpGet(url);
        return getFile(httpGet, fileName);  
    } 
    
    public static boolean httpPostRequest1(String url, String fileName){  
    	HttpPost httpPost = new HttpPost(url);
        return getFile(httpPost, fileName);  
    } 
      
    public static String httpPostRequest(String url){  
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);  
    }  
    
    public static String httpPostRequest(String url, String params) throws UnsupportedEncodingException{  
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(params, UTF_8));
        return getResult(httpPost);  
    }  
      
    public static String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException{  
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        return getResult(httpPost);  
    }  
      
    public static String httpPostRequest(String url, Map<String, Object> headers,   
            String params) throws UnsupportedEncodingException{  
        HttpPost httpPost = new HttpPost(url);
          
        for (Map.Entry<String, Object> param: headers.entrySet()) {  
            httpPost.addHeader(param.getKey(), param.getValue().toString());  
        }  
          
        httpPost.setEntity(new StringEntity(params, UTF_8));
        return getResult(httpPost);  
    } 
    
    public static boolean httpPostRequest(String url, Map<String, Object> headers,   
            String params, String destFileName) throws UnsupportedEncodingException{  
        HttpPost httpPost = new HttpPost(url);
          
        for (Map.Entry<String, Object> param: headers.entrySet()) {  
            httpPost.addHeader(param.getKey(), param.getValue().toString());  
        }  
          
        httpPost.setEntity(new StringEntity(params, UTF_8));
        return getFile(httpPost,destFileName);  
    }
    
    public static String httpPostRequest(String url, Map<String, Object> headers,   
            Map<String, Object> params) throws UnsupportedEncodingException{  
        HttpPost httpPost = new HttpPost(url);
          
        for (Map.Entry<String, Object> param: headers.entrySet()) {  
            httpPost.addHeader(param.getKey(), param.getValue().toString());  
        }  
          
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
          
        return getResult(httpPost);  
    }  
      
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params){
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param: params.entrySet()) {  
            pairs.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
        }  
          
        return pairs;  
    }  
      
      
    /** 
     * 处理Http请求 
     * @param request 
     * @return 
     */  
    private static String getResult(HttpRequestBase request){
 
        CloseableHttpClient httpClient = getHttpClient();
        try{  
            CloseableHttpResponse response = httpClient.execute(request);
            //获取响应状态码  
            int statusCode = response.getStatusLine().getStatusCode(); 
            //获取消息实体
            HttpEntity entity = response.getEntity();
            if(entity!=null){  
                //long len = entity.getContentLength();// -1 表示长度未知  
                String result = EntityUtils.toString(entity);
                response.close();  
                //httpClient.close();  
                return result;  
            }  
        }catch(ClientProtocolException e){
            e.printStackTrace();  
        }catch(IOException e){  
            e.printStackTrace();  
        }finally{  
              
        }  
          
        return EMPTY_STR;  
    }  

      
     /**
     * 下载文件
     * @param  url
     * @param  destFileName   xxx.jpg/xxx.png/xxx.txt
     * @throws ClientProtocolException
     * @throws IOException
     */
    private static boolean getFile(HttpRequestBase request, String destFileName){

    	CloseableHttpClient httpClient = getHttpClient();
    	try{
            CloseableHttpResponse response = httpClient.execute(request);
            //获取响应状态码  
            int statusCode = response.getStatusLine().getStatusCode(); 
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            File file =new File(destFileName);
            try{
                FileOutputStream fout =new FileOutputStream(file);
                int l = -1;
                byte[] tmp =new byte[1024];
                while((l = in.read(tmp)) != -1) {
                    fout.write(tmp,0, l);
                    // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
                }
                fout.flush();
                fout.close();
            }finally{
                // 关闭低层流。
                in.close();
            }
        }catch(ClientProtocolException e){
            e.printStackTrace(); 
            return false;
        }catch(IOException e){  
            e.printStackTrace();
            return false;
        }finally{  
              
        } 
    	return true;
    }  
    
    public static void main( String[] args )
    { 
        httpGetRequest("https://apidata.baidu.com/data/v2/getFile.do?t=1456988726&u=19349504&i=d4691ad25e5fd4326c390cdda04d14e4&f=%2Fapireport%2Fapi%2Freport%2F0%2F19349504%2F8dc9004fdb2c36c06288bfb05f3521b3.csv&h=600&s=36febb4be3dd21bb0c0cd4d30c5cfbc0", "D:/TEMP/20160303.csv");
    }     
}  