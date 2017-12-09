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
public class CampaignServiceTest {

	private static CommonService factory;
	private static CampaignService campaignService;
	private static final int BEAN_COUNT = 1;
	private static String[] fields = { "campaignName", "campaignId" };
	private static String SUCCESS = "success";

	@Before
	public void setUp() {
		try {
			factory = ServiceFactory.getInstance();
			campaignService = factory.getService(CampaignService.class);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	private List<CampaignType> innerGet(List<CampaignType> campaignTypes)
			throws ApiException {
		GetCampaignRequest request = new GetCampaignRequest();
		List<Long> ids = new ArrayList<Long>();
		for (CampaignType campaignType : campaignTypes) {
			ids.add(campaignType.getCampaignId());
		}
		request.setCampaignFields(Arrays.asList(fields));
		request.setCampaignIds(ids);
		GetCampaignResponse response = campaignService.getCampaign(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(campaignService, true);
		List<CampaignType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<CampaignType> innerAdd() throws ApiException {
		AddCampaignRequest request = new AddCampaignRequest();
		List<CampaignType> list = new ArrayList<CampaignType>();
		for (int i = 0; i < BEAN_COUNT; i++) {
			CampaignType campaignType = new CampaignType();
			campaignType.setCampaignName(UUID.randomUUID().toString()
					.substring(0, 29));
			// campaignType.setBudget(Math.random() * 1000D);
			list.add(campaignType);
		}
		request.setCampaignTypes(list);
		AddCampaignResponse response = campaignService.addCampaign(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(campaignService, true);
		List<CampaignType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<CampaignType> innerUpdate(List<CampaignType> campaignTypes)
			throws ApiException {
		UpdateCampaignRequest request = new UpdateCampaignRequest();
		for (CampaignType campaignType : campaignTypes) {
			campaignType.setCampaignName(UUID.randomUUID().toString()
					.substring(0, 29));
			campaignType.setBudget(Math.random() * 9000D);
		}
		request.setCampaignTypes(campaignTypes);
		UpdateCampaignResponse response = campaignService
				.updateCampaign(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(campaignService, true);
		List<CampaignType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private void innerDelete(List<CampaignType> campaignTypes)
			throws ApiException {
		DeleteCampaignRequest request = new DeleteCampaignRequest();
		List<Long> ids = new ArrayList<Long>();
		for (CampaignType campaignType : campaignTypes) {
			ids.add(campaignType.getCampaignId());
		}
		request.setCampaignIds(ids);
		DeleteCampaignResponse response = campaignService
				.deleteCampaign(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(campaignService, true);
		List<CampaignType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
	}

	@Test
	public void testCommonService() throws ApiException {
		List<CampaignType> datas = this.innerAdd();
		datas = this.innerGet(datas);
		datas = this.innerUpdate(datas);
		this.innerDelete(datas);
	}
}
