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
public class SearchServiceTest {

	private static CommonService factory;
	private static SearchService searchService;

	private static String[] fields = { "userId", "cost" };
	private static String SUCCESS = "success";
	private static int OP_LEVEL = 1;
	private static Integer[] OP_TYPE = { 1, 2, 3, 4, 5 };
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static Integer[] TABIDS = { 1, 2, 3, 4, 5 };

	@Before
	public void setUp() {
		try {
			factory = ServiceFactory.getInstance();
			searchService = factory.getService(SearchService.class);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchMaterialInfo() throws Exception {
		GetMaterialInfoBySearchRequest request = new GetMaterialInfoBySearchRequest();
		request.setSearchWord("鲜花");
		request.setSearchLevel(2);
		request.setSearchType(0);

		GetMaterialInfoBySearchResponse response = new GetMaterialInfoBySearchResponse();
		response = searchService.getMaterialInfoBySearch(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(searchService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		System.out.println(response.getData().get(0).getMaterialSearchInfos()
				.get(0).getMaterialInfo(1));
	}

	@Test
	public void testGetCountById() throws Exception {
		GetCountByIdRequest request = new GetCountByIdRequest();
		request.setCountType(3);
		GetCountByIdResponse response = new GetCountByIdResponse();
		response = searchService.getCountById(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(searchService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		System.out.println("here" + response.getData());
	}

	@Test
	public void testGetTab() throws Exception {
		GetTabRequest request = new GetTabRequest();
		request.setIdType(11);
		request.setTabIds(Arrays.asList(TABIDS));
		GetTabResponse response = new GetTabResponse();
		response = searchService.getTab(request);
		ResHeader rhHeader = ResHeaderUtil.getResHeader(searchService, true);
		Assert.assertTrue(SUCCESS.equals(rhHeader.getDesc())
				&& rhHeader.getStatus() == 0);
		System.out.println(response.getData());
	}
}
