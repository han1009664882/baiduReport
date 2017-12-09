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
import java.util.List;

/**
 * @author linyongqi@baidu.com on 4/2/15.
 */
public class ReportServiceTest {

	private static CommonService factory;
	private static ReportService reportService;
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static String SUCCESS = "success";
	private static String fileId = "87d27902308d471f3df08f804a38a156";

	@Before
	public void setUp() {
		try {
			factory = ServiceFactory.getInstance();
			reportService = factory.getService(ReportService.class);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	public void getRealTimeData() throws Exception {
		GetRealTimeDataRequest request = new GetRealTimeDataRequest();
		ReportRequestType type = new ReportRequestType();
		List<String> performanceData = Arrays.asList(new String[] {
				"impression", "click", "cost", "cpc", "ctr", "cpm",
				"conversion" });
		type.setPerformanceData(performanceData);
		type.setLevelOfDetails(5);
		type.setUnitOfTime(5);
		type.setReportType(3);
		type.setStartDate(simpleDateFormat.parse("2016-08-21 00:00:00"));
		type.setEndDate(simpleDateFormat.parse("2016-08-22 00:00:00"));
		request.setRealTimeRequestType(type);
		GetRealTimeDataResponse response = reportService
				.getRealTimeData(request);
		List<RealTimeResultType> datas = response.getData();
		System.out.println(datas);
		ResHeader rheader = ResHeaderUtil.getResHeader(reportService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
	}

	public void getRealTimeQueryData() throws Exception {
		GetRealTimeQueryDataRequest request = new GetRealTimeQueryDataRequest();
		ReportRequestType type = new ReportRequestType();
		List<String> performanceData = Arrays.asList(new String[] {
				"impression", "click" });
		type.setPerformanceData(performanceData);
		type.setLevelOfDetails(7);
		type.setUnitOfTime(5);
		type.setReportType(6);
		type.setStartDate(simpleDateFormat.parse("2016-08-21 12:00:00"));
		type.setEndDate(simpleDateFormat.parse("2016-08-22 00:00:00"));
		request.setRealTimeQueryRequestType(type);
		GetRealTimeQueryDataResponse response = reportService
				.getRealTimeQueryData(request);
		List<RealTimeQueryResultType> datas = response.getData();
		System.out.println(datas);
		ResHeader rheader = ResHeaderUtil.getResHeader(reportService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
	}

	public void getRealTimePairData() throws Exception {
		GetRealTimePairDataRequest request = new GetRealTimePairDataRequest();
		ReportRequestType type = new ReportRequestType();
		List<String> performanceData = Arrays.asList(new String[] {
				"impression", "click" });
		type.setPerformanceData(performanceData);
		type.setLevelOfDetails(12);
		type.setUnitOfTime(5);
		type.setReportType(15);
		type.setStartDate(simpleDateFormat.parse("2016-08-21 12:00:00"));
		type.setEndDate(simpleDateFormat.parse("2016-08-22 00:00:00"));
		request.setRealTimePairRequestType(type);
		GetRealTimePairDataResponse response = reportService
				.getRealTimePairData(request);
		List<RealTimePairResultType> datas = response.getData();
		System.out.println(datas);
		ResHeader rheader = ResHeaderUtil.getResHeader(reportService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
	}

	public void getProfessionalReportId() throws Exception {
		GetProfessionalReportIdRequest request = new GetProfessionalReportIdRequest();
		ReportRequestType type = new ReportRequestType();
		List<String> performanceData = Arrays.asList(new String[] { "cost",
				"cpc", "click", "impression", "ctr", "cpm", "position" });
		type.setPerformanceData(performanceData);
		type.setLevelOfDetails(2);
		type.setUnitOfTime(5);
		type.setReportType(3);
		type.setStartDate(simpleDateFormat.parse("2016-08-21 00:00:00"));
		type.setEndDate(simpleDateFormat.parse("2016-08-22 00:00:00"));
		request.setReportRequestType(type);
		GetProfessionalReportIdResponse response = reportService
				.getProfessionalReportId(request);
		List<GetProfessionalReportIdData> datas = response.getData();
		System.out.println(datas);
		fileId = response.getData().get(0).getReportId();
		ResHeader rheader = ResHeaderUtil.getResHeader(reportService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
	}

	public void getReportState() throws Exception {
		GetReportStateRequest request = new GetReportStateRequest();
		request.setReportId(fileId);
		GetReportStateResponse response = reportService.getReportState(request);
		List<GetReportStateData> datas = response.getData();
		System.out.println(datas);
		ResHeader rheader = ResHeaderUtil.getResHeader(reportService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		System.out.println(response.getData().get(0).getIsGenerated());
	}

	public void getReportFileUrl() throws Exception {
		GetReportFileUrlRequest request = new GetReportFileUrlRequest();
		request.setReportId(fileId);
		GetReportFileUrlResponse response = reportService
				.getReportFileUrl(request);
		List<GetReportFileUrlData> datas = response.getData();
		System.out.println(datas);
		ResHeader rheader = ResHeaderUtil.getResHeader(reportService, true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		System.out.println(response.getData().get(0).getReportFilePath());
	}

	@Test
	public void getReport() throws Exception {
//		getRealTimeData();
//		getRealTimeQueryData();
//		getRealTimePairData();
//		getProfessionalReportId();
		getReportState();
		getReportFileUrl();
	}

}
