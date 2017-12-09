package com.baidu.drapi.sdkclient.test;

import com.baidu.drapi.autosdk.core.CommonService;
import com.baidu.drapi.autosdk.core.ResHeader;
import com.baidu.drapi.autosdk.core.ResHeaderUtil;
import com.baidu.drapi.autosdk.core.ServiceFactory;
import com.baidu.drapi.autosdk.exception.ApiException;
import com.baidu.drapi.autosdk.sms.service.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author linyongqi@baidu.com on 12/17/14.
 */
public class ToolkitServiceTest {

    private static CommonService factory;
    private static ToolkitService toolkitService;
    private static String[] fields = {"userId", "cost"};
    private static String SUCCESS = "success";
    private static int OP_LEVEL=5;
    private static Integer[] OP_TYPE={1,2,3,4,5};
    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() {
        try {
            factory = ServiceFactory.getInstance();
            toolkitService = factory.getService(ToolkitService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testToolkitService() throws Exception{
        GetOperationRecordRequest request=new GetOperationRecordRequest();
        request.setStartDate(simpleDateFormat.parse("2016-08-15"));
        request.setEndDate(simpleDateFormat.parse("2016-08-15"));
        request.setOptLevel(OP_LEVEL);
        request.setOptTypes(Arrays.asList(OP_TYPE));
        GetOperationRecordResponse response=toolkitService.getOperationRecord(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(toolkitService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        System.out.println(response.getData().size());
        for(int i=0;i<response.getData().size();i++){
        	if(response.getData().get(i).getOptType() == 3){
        		System.out.println(response.getData().get(i));
        	}
        	
        }
    }
}
