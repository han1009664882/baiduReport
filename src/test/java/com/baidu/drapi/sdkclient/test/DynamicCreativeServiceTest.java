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

import java.util.ArrayList;
import java.util.List;

/**
 * @author linyongqi@baidu.com on 1/26/15.
 */
public class DynamicCreativeServiceTest {
    private static CommonService factory;
    private static DynamicCreativeService dynamicCreativeService;
    private static String SUCCESS = "success";

    @Before
    public void setUp() {
        try {
            factory = ServiceFactory.getInstance();
            dynamicCreativeService = factory.getService(DynamicCreativeService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private List<DynCreativeType> getDynCreative(List<DynCreativeType> params) throws ApiException {
        GetDynCreativeRequest request=new GetDynCreativeRequest();
        List<Long> ids=new ArrayList<Long>();
        for(DynCreativeType t:params){
            ids.add(t.getDynCreativeId());
        }
        List<String> fields=new ArrayList<String>();
        fields.add("dynCreativeId");
        fields.add("campaignId");
        fields.add("adgroupId");
        fields.add("bindingType");
        fields.add("url");
        fields.add("murl");
        request.setDynCreativeFields(fields);
        request.setIds(ids);
        request.setIdType(13);
        GetDynCreativeResponse response=dynamicCreativeService.getDynCreative(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(dynamicCreativeService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return response.getData();
    }


    public List<DynCreativeType> addDynCreative() throws ApiException {
        AddDynCreativeRequest request=new AddDynCreativeRequest();

        DynCreativeType dynCreative=new DynCreativeType();
        dynCreative.setCampaignId(67536212L);
        dynCreative.setAdgroupId(2088608210L);
        dynCreative.setBindingType(3);
        dynCreative.setTitle("sdktest_1");
        dynCreative.setMurl("http://www.baidu.com");
        dynCreative.setUrl("http://www.baidu.com");
        List<DynCreativeType> dynCreativeFragments=new ArrayList<DynCreativeType>();
        dynCreativeFragments.add(dynCreative);
        request.setDynCreativeTypes(dynCreativeFragments);
        AddDynCreativeResponse response=dynamicCreativeService.addDynCreative(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(dynamicCreativeService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return response.getData();
    }
    public List<DynCreativeType> updateDynCreatives(List<DynCreativeType> params) throws ApiException {
        UpdateDynCreativeRequest request=new UpdateDynCreativeRequest();
        for(DynCreativeType t:params){
            t.setTitle("modify-"+t.getTitle());
        }
        request.setDynCreativeTypes(params);
        UpdateDynCreativeResponse response=dynamicCreativeService.updateDynCreative(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(dynamicCreativeService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return response.getData();
    }
    public void deleteDynCreatives(List<DynCreativeType> params) throws ApiException{
        DeleteDynCreativeRequest request=new DeleteDynCreativeRequest();
        List<Long> ids=new ArrayList<Long>();
        for(DynCreativeType t:params){
            ids.add(t.getDynCreativeId());
        }
        request.setDynCreativeIds(ids);
        DeleteDynCreativeResponse response=dynamicCreativeService.deleteDynCreative(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(dynamicCreativeService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
    }
    @Test
    public void testDynamicCreativeService() throws ApiException{
        List<DynCreativeType> values=this.addDynCreative();
        values=this.updateDynCreatives(values);
        values=this.getDynCreative(values);
        this.deleteDynCreatives(values);
    }
}
