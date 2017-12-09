package com.baidu.drapi.sdkclient.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baidu.drapi.autosdk.core.CommonService;
import com.baidu.drapi.autosdk.core.ResHeader;
import com.baidu.drapi.autosdk.core.ResHeaderUtil;
import com.baidu.drapi.autosdk.core.ServiceFactory;
import com.baidu.drapi.autosdk.exception.ApiException;
import com.baidu.drapi.autosdk.sms.service.*;

/**
 * @author guojidong@baidu.com on 7/21/14.
 */

public class BulkJobServiceTest {
	
	private static CommonService factory;
	private static BulkJobService bulkJobService;
	private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String SUCCESS = "success";
	private static String fileId = "adcf938b22ba2cc4eb7702329086778f";
	private static long planId = 67536212L;

	@Before
	public void setUp() {
	    try {
	        factory = ServiceFactory.getInstance();
	        bulkJobService = factory.getService(BulkJobService.class);
	    } catch (ApiException e) {
	        e.printStackTrace();
	    }
	}
	
	public void getAllObjects() throws Exception{
		GetAllObjectsRequest request = new GetAllObjectsRequest();
		List<String> acs = new ArrayList();
		acs.add("all");
		request.setAccountFields(acs);
		List<Long> ids = new ArrayList();
		ids.add(planId);
		request.setCampaignIds(ids);
		
		GetAllObjectsResponse response = bulkJobService.getAllObjects(request);
		fileId = response.getData(0).getFileId();
		System.out.println("the file id is :" + fileId);
		ResHeader rheader = ResHeaderUtil.getResHeader(bulkJobService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
	}
	
	public void getAllChangedObjects() throws Exception{
		GetAllChangedObjectsRequest request = new GetAllChangedObjectsRequest();
		
		List<String> acs = new ArrayList();
		acs.add("all");
		request.setAccountFields(acs);
		request.setCampaignFields(acs);
		List<Long> ids = new ArrayList();
		ids.add(planId);
		request.setCampaignIds(ids);
		request.setStartTime(simpleDateFormat.parse("2016-08-20 12:00:00"));
		GetAllChangedObjectsResponse response = bulkJobService.getAllChangedObjects(request);
		fileId = response.getData(0).getFileId();
		System.out.println("the file id is : " + fileId);
		ResHeader rheader = ResHeaderUtil.getResHeader(bulkJobService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
	}
	
	public void getUserCache() throws Exception{
		GetUserCacheRequest request = new GetUserCacheRequest();
		request.setFileId(fileId);
		GetUserCacheResponse response = bulkJobService.getUserCache(request);
		System.out.println(response.getData().get(0));
		ResHeader rheader = ResHeaderUtil.getResHeader(bulkJobService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
	}
	
	public void getFilePath() throws Exception{
		GetFilePathRequest request = new GetFilePathRequest();
		request.setFileId(fileId);
		GetFilePathResponse response = bulkJobService.getFilePath(request);
		FilePathType fpt= response.getData().get(0);
		System.out.println(fpt);
		ResHeader rheader = ResHeaderUtil.getResHeader(bulkJobService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        System.out.println(response.getData().get(0));
	}
	
	public void getFileStatus() throws Exception{
		GetFileStatusRequest request = new GetFileStatusRequest();
		request.setFileId(fileId);
		GetFileStatusResponse response = bulkJobService.getFileStatus(request);
		int isGenerated = response.getData(0).getIsGenerated();
		System.out.println(isGenerated);
		ResHeader rheader = ResHeaderUtil.getResHeader(bulkJobService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        System.out.println("file Stauts"+response.getData().get(0).getIsGenerated());
	}
	
	public void cancelDownload() throws Exception{
		CancelDownloadRequest request = new CancelDownloadRequest();
		request.setFileId(fileId);
		CancelDownloadResponse response = bulkJobService.cancelDownload(request);  //�Ѿ����صĻ�����ȡ����
		int isCanceled = response.getData().get(0).getIsCanceled();
		System.out.println(isCanceled);
		ResHeader rheader = ResHeaderUtil.getResHeader(bulkJobService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
	}
	
	public void getChangedItemId() throws Exception{
		GetChangedItemIdRequest request = new GetChangedItemIdRequest();
		List<Long> ids = new ArrayList();
		ids.add(planId);
		request.setIds(ids);
		request.setType(3);
		request.setItemType(5);
		request.setStartTime(simpleDateFormat.parse("2016-08-20 12:00:00"));
		GetChangedItemIdResponse response = bulkJobService.getChangedItemId(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(bulkJobService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
	}
	
	public void getChangedId() throws Exception{
		GetChangedIdRequest request = new GetChangedIdRequest();
		request.setStartTime(simpleDateFormat.parse("2016-08-13 00:00:00"));
		request.setCampaignLevel(true);
		request.setAdgroupLevel(true);
		request.setKeywordLevel(true);
		
		GetChangedIdResponse response = bulkJobService.getChangedId(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(bulkJobService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
	}
	
	public void getChangedScale() throws Exception{
		GetChangedScaleRequest request = new GetChangedScaleRequest();
		List<Long> ids = new ArrayList();
		ids.add(planId);
		request.setCampaignIds(ids);
		request.setChangedCampaignScale(true);
		request.setChangedAdgroupScale(true);
		request.setChangedKeywordScale(true);
		request.setStartTime(simpleDateFormat.parse("2016-08-20 12:00:00"));
		GetChangedScaleResponse response = bulkJobService.getChangedScale(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(bulkJobService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
	}
	
	public void gjd_test() throws ParseException, JsonParseException, JsonMappingException, IOException{
		SimpleDateFormat time=new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a",Locale.ENGLISH); 
		Date date = time.parse("Jul 24, 2015 11:32:09 PM");
		System.out.println(date); 
		
		ObjectMapper mapper = new ObjectMapper();
		//在JacksonUtil的静态语句块中添加这句
		mapper.getDeserializationConfig().setDateFormat(time);  
		
		String json = "{\"date\":\"Jul 24, 2015 11:32:09 PM\",\"uid\":1000,\"uname\":\"xiao liao\",\"upwd\":\"123\",\"number\":12.0,\"isstudent\":true}";  
        Student student1 = mapper.readValue(json,Student.class);  
        System.out.println("json2:"+student1.date);  
		
	}
	
	static class Student{
		public int uid;
		public String uname;
		public String upwd;
		public double number;
		public boolean isstudent;
		public Date date;
	}
	
	@Test
	public void bulkTest() throws Exception{
//		getAllObjects();
		getAllChangedObjects();
		
//		getFileStatus();
//		getFilePath();
//		getUserCache();
//		cancelDownload();
//		getChangedItemId();
//		getChangedId();
//		getChangedScale();
	}
}
