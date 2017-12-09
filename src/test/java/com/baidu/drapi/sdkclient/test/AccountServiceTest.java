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

import java.util.*;

/**
 * @author linyongqi@baidu.com on 12/12/14.
 */
public class AccountServiceTest {

    private static CommonService factory;
    private static AccountService accountService;
    private static String[] fields = {"userId", "cost"};
    private static String SUCCESS = "success";

    @Before
    public void setUp() {
        try {
            factory = ServiceFactory.getInstance();
            accountService = factory.getService(AccountService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private AccountInfo getAccoutInfo() throws ApiException {
        GetAccountInfoRequest request = new GetAccountInfoRequest();
        request.setAccountFields(Arrays.asList(fields));
        GetAccountInfoResponse response = accountService.getAccountInfo(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(accountService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        List<AccountInfo> datas = response.getData();
        return datas.get(0);
    }

    @Test
    public void testAccountService() throws ApiException {
        AccountInfo accountInfo = getAccoutInfo();
        accountInfo.setBudget(777D);
        UpdateAccountInfoRequest request = new UpdateAccountInfoRequest();
        request.setAccountInfo(accountInfo);
        UpdateAccountInfoResponse response = accountService.updateAccountInfo(request);
        List<AccountInfo> datas = response.getData();
        System.out.println(datas);
    }

}
