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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author linyongqi@baidu.com on 12/12/14.
 */
public class AdgroupServiceTest {


    private static CommonService factory;
    private AdgroupService adgroupService;
    private static final int BEAN_COUNT = 2;
    private static String[] fields = {"adgroupName", "adgroupId"};
    private static String SUCCESS = "success";
    private static final Long CAMPAIGNID = 63805864L;
    private static final Double MAXPRICE = 30D;

    @Before
    public void setUp() {
        try {
            factory = ServiceFactory.getInstance();
            adgroupService = factory.getService(AdgroupService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private List<AdgroupType> innerGet(List<AdgroupType> adgroupTypes) throws ApiException {
        GetAdgroupRequest request = new GetAdgroupRequest();
        List<Long> ids = new ArrayList<Long>();
        for (AdgroupType adgroupType : adgroupTypes) {
            ids.add(adgroupType.getAdgroupId());
        }
        request.setAdgroupFields(Arrays.asList(fields));
        request.setIds(ids);
        request.setIdType(5);
        GetAdgroupResponse response = adgroupService.getAdgroup(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(adgroupService, true);
        List<AdgroupType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }

    private List<AdgroupType> innerAdd() throws ApiException {
        AddAdgroupRequest request = new AddAdgroupRequest();
        List<AdgroupType> list = new ArrayList<AdgroupType>();
        for (int i = 0; i < BEAN_COUNT; i++) {
            AdgroupType adgroupType = new AdgroupType();
            adgroupType.setAdgroupName(UUID.randomUUID().toString().substring(0, 29));
            adgroupType.setCampaignId(CAMPAIGNID);
            adgroupType.setMaxPrice(MAXPRICE);
            list.add(adgroupType);
        }
        request.setAdgroupTypes(list);
        AddAdgroupResponse response = adgroupService.addAdgroup(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(adgroupService, true);
        List<AdgroupType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }

    private List<AdgroupType> innerUpdate(List<AdgroupType> adgroupTypes) throws ApiException {
        UpdateAdgroupRequest request = new UpdateAdgroupRequest();
        for (AdgroupType adgroupType : adgroupTypes) {
            adgroupType.setAdgroupName(UUID.randomUUID().toString().substring(0, 29));
        }
        request.setAdgroupTypes(adgroupTypes);
        UpdateAdgroupResponse response = adgroupService.updateAdgroup(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(adgroupService, true);
        List<AdgroupType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }

    private void innerDelete(List<AdgroupType> adgroupTypes) throws ApiException {
        DeleteAdgroupRequest request = new DeleteAdgroupRequest();
        List<Long> ids = new ArrayList<Long>();
        for (AdgroupType adgroupType : adgroupTypes) {
            ids.add(adgroupType.getAdgroupId());
        }
        request.setAdgroupIds(ids);
        DeleteAdgroupResponse response = adgroupService.deleteAdgroup(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(adgroupService, true);
        List<AdgroupType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
    }

    @Test
    public void testCommonService() throws ApiException {
        List<AdgroupType> datas = this.innerAdd();
        datas = this.innerGet(datas);
        datas = this.innerUpdate(datas);
        System.out.println(datas);
        this.innerDelete(datas);
    }
}
