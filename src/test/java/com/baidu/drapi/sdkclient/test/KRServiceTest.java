package com.baidu.drapi.sdkclient.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.ResolutionSyntax;

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

public class KRServiceTest {

	private static CommonService factory;
	private static KRService krService;
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static String SUCCESS = "success";
	private static String fileId = "bbff76c3f8614376fe36938b222b7fe2";
	private static long planId = 48831740;

	@Before
	public void setUp() {
		try {
			factory = ServiceFactory.getInstance();
			krService = factory.getService(KRService.class);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	public void getKRByQuery() throws Exception {
		GetKRByQueryRequest request = new GetKRByQueryRequest();
		request.setQueryType(1);
		request.setQuery("鲜花");
		GetKRByQueryResponse response = krService.getKRByQuery(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(krService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		// System.out.println(response.getData());
	}

	public void getKRCustom() throws Exception {
		GetKRCustomRequest request = new GetKRCustomRequest();
		request.setId(planId);
		request.setIdType(3);
		GetKRCustomResponse response = krService.getKRCustom(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(krService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		// System.out.println(response.getData());
	}

	public void getEstimatedDataByBid() throws Exception {
		GetEstimatedDataByBidRequest request = new GetEstimatedDataByBidRequest();
		List<KREstimatedType> list = new ArrayList<KREstimatedType>();
		KREstimatedType kret = new KREstimatedType();
		kret.setWord("鲜花");
		kret.setBid((double) 50);
		list.add(kret);
		request.setWords(list);
		GetEstimatedDataByBidResponse response = krService
				.getEstimatedDataByBid(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(krService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		System.out.println(response.getData());
	}

	public void getEstimatedData() throws Exception {
		GetEstimatedDataRequest request = new GetEstimatedDataRequest();
		List<KREstimatedType> list = new ArrayList<KREstimatedType>();
		KREstimatedType kret = new KREstimatedType();
		kret.setWord("鲜花");
		kret.setBid((double) 50);
		list.add(kret);
		request.setWords(list);
		GetEstimatedDataResponse response = krService.getEstimatedData(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(krService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		// System.out.println(response.getData());
	}

	public void getKRFileIdByWords() throws Exception {
		GetKRFileIdByWordsRequest request = new GetKRFileIdByWordsRequest();
		List<String> list = new ArrayList<String>();
		list.add("百合");
		request.setSeedWords(list);
		GetKRFileIdByWordsResponse response = krService
				.getKRFileIdByWords(request);
		fileId = response.getData(0).getFileId();
		System.out.println("file MD5 is " + fileId);
		ResHeader rheader = ResHeaderUtil.getResHeader(krService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		System.out.println(response.getData().get(0).getFileId());
	}

	public void getFilePath() throws Exception {
		GetKRFileRequestParams request = new GetKRFileRequestParams();
		request.setFileId(fileId);
		GetKRFilePathResponse response = krService.getFilePath(request);
		System.out.println(response.getData(0).getFilePath());
		ResHeader rheader = ResHeaderUtil.getResHeader(krService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		System.out.println(response.getData().get(0).getFilePath());
	}

	public void getFileStatus() throws Exception {
		GetFileStatusRequest request = new GetFileStatusRequest();
		request.setFileId(fileId);
		GetFileStatusResponse response = krService.getFileStatus(request);
		System.out.println(response.getData(0).getIsGenerated());
		ResHeader rheader = ResHeaderUtil.getResHeader(krService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		System.out.println(response.getData().get(0).getIsGenerated());
	}

	@Test
	public void testKR() throws Exception {
		getKRByQuery();
		getKRCustom();
		getEstimatedDataByBid();
		getEstimatedData();
		getKRFileIdByWords();
		getFileStatus();
		getFilePath();

	}

}
