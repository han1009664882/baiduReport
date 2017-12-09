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
public class KeywordServiceTest {

    private static CommonService factory;
    private KeywordService keywordService;
    private static final int BEAN_COUNT = 2;
    private static String[] fields = {"keyword", "keywordId"};
    private static String SUCCESS = "success";
    private static final Long ADGROUPID = 2088608210L;
    private static final int IDTYPE = 11;
    private static final int ISTMP = 0;
    @Before
    public void setUp() {
        try {
            factory = ServiceFactory.getInstance();
            keywordService = factory.getService(KeywordService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private List<KeywordType> innerGet(List<KeywordType> keywordTypes) throws ApiException {
        GetWordRequest request = new GetWordRequest();
        List<Long> ids = new ArrayList<Long>();
        for (KeywordType keywordType : keywordTypes) {
            ids.add(keywordType.getKeywordId());
        }
        request.setWordFields(Arrays.asList(fields));
        request.setIds(ids);
        request.setIdType(IDTYPE);
        request.setGetTemp(ISTMP);
        GetWordResponse response = keywordService.getWord(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(keywordService, true);
        List<KeywordType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }

    private List<KeywordType> innerAdd() throws ApiException {
        AddWordRequest request = new AddWordRequest();
        List<KeywordType> list = new ArrayList<KeywordType>();
        for (int i = 0; i < BEAN_COUNT; i++) {
            KeywordType keywordType = new KeywordType();
            keywordType.setKeyword(UUID.randomUUID().toString().substring(0, 29));
            keywordType.setAdgroupId(ADGROUPID);
            list.add(keywordType);
        }
        request.setKeywordTypes(list);
        AddWordResponse response = keywordService.addWord(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(keywordService, true);
        List<KeywordType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }

    private List<KeywordType> innerUpdate(List<KeywordType> keywordTypes) throws ApiException {
        UpdateWordRequest request = new UpdateWordRequest();
        for (KeywordType keywordType : keywordTypes) {
            keywordType.setKeyword(UUID.randomUUID().toString().substring(0, 29));
        }
        request.setKeywordTypes(keywordTypes);
        UpdateWordResponse response = keywordService.updateWord(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(keywordService, true);
        List<KeywordType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }

    private void innerDelete(List<KeywordType> keywordTypes) throws ApiException {
        DeleteWordRequest request = new DeleteWordRequest();
        List<Long> ids = new ArrayList<Long>();
        for (KeywordType keywordType : keywordTypes) {
            ids.add(keywordType.getKeywordId());
        }
        request.setKeywordIds(ids);
        DeleteWordResponse response = keywordService.deleteWord(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(keywordService, true);
        List<KeywordType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
    }

    @Test
    public void testCommonService() throws ApiException {
        List<KeywordType> datas = this.innerAdd();
        datas = this.innerGet(datas);
        datas = this.innerUpdate(datas);
        this.innerDelete(datas);
    }
}
