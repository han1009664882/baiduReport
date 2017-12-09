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
public class CreativeServiceTest {
    private static CommonService factory;
    private CreativeService creativeService;
    private static final int BEAN_COUNT = 2;
    private static String[] fields = {"title", "creativeId", "description1", "pcDestinationUrl"};
    private static String SUCCESS = "success";
    private static final Long ADGROUPID = 2088608210L;
    private static final int IDTYPE = 7;
    private static final int ISTMP = 0;
    private static final String DESC1 = "add_creative_by_requelqi";
    private static final String MODIFY_DESC1 = "modify_creative_by_requelqi";
    private static final String URL = "https://www.baidu.com";

    @Before
    public void setUp() {
        try {
            factory = ServiceFactory.getInstance();
            creativeService = factory.getService(CreativeService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private List<CreativeType> innerGet(List<CreativeType> creativeTypes) throws ApiException {
        GetCreativeRequest request = new GetCreativeRequest();
        List<Long> ids = new ArrayList<Long>();
        for (CreativeType creativeType : creativeTypes) {
            ids.add(creativeType.getCreativeId());
        }
        request.setCreativeFields(Arrays.asList(fields));
        request.setIds(ids);
        request.setIdType(IDTYPE);
        request.setGetTemp(ISTMP);
        GetCreativeResponse response = creativeService.getCreative(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(creativeService, true);
        List<CreativeType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }

    private List<CreativeType> innerAdd() throws ApiException {
        AddCreativeRequest request = new AddCreativeRequest();
        List<CreativeType> list = new ArrayList<CreativeType>();
        for (int i = 0; i < BEAN_COUNT; i++) {
            CreativeType creativeType = new CreativeType();
            creativeType.setTitle(UUID.randomUUID().toString().substring(0, 29));
            creativeType.setAdgroupId(ADGROUPID);
            creativeType.setDescription1(DESC1);
            creativeType.setPcDestinationUrl(URL);
            list.add(creativeType);
        }
        request.setCreativeTypes(list);
        AddCreativeResponse response = creativeService.addCreative(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(creativeService, true);
        List<CreativeType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }

    private List<CreativeType> innerUpdate(List<CreativeType> creativeTypes) throws ApiException {
        UpdateCreativeRequest request = new UpdateCreativeRequest();
        for (CreativeType creativeType : creativeTypes) {
            creativeType.setTitle(UUID.randomUUID().toString().substring(0, 29));
            creativeType.setDescription1(MODIFY_DESC1);
        }
        request.setCreativeTypes(creativeTypes);
        UpdateCreativeResponse response = creativeService.updateCreative(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(creativeService, true);
        List<CreativeType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }

    private void innerDelete(List<CreativeType> creativeTypes) throws ApiException {
        DeleteCreativeRequest request = new DeleteCreativeRequest();
        List<Long> ids = new ArrayList<Long>();
        for (CreativeType creativeType : creativeTypes) {
            ids.add(creativeType.getCreativeId());
        }
        request.setCreativeIds(ids);
        DeleteCreativeResponse response = creativeService.deleteCreative(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(creativeService, true);
        List<CreativeType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
    }

    @Test
    public void testCommonService() throws ApiException {
        List<CreativeType> datas = this.innerAdd();
        datas = this.innerGet(datas);
        datas = this.innerUpdate(datas);
        this.innerDelete(datas);
    }
}
