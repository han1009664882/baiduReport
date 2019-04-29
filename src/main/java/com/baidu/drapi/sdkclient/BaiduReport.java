package com.baidu.drapi.sdkclient;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.baidu.drapi.autosdk.core.CommonService;
import com.baidu.drapi.autosdk.core.ServiceFactory;
import com.baidu.drapi.autosdk.exception.ApiException;
import com.baidu.drapi.autosdk.sms.service.GetProfessionalReportIdData;
import com.baidu.drapi.autosdk.sms.service.GetProfessionalReportIdRequest;
import com.baidu.drapi.autosdk.sms.service.GetProfessionalReportIdResponse;
import com.baidu.drapi.autosdk.sms.service.GetReportFileUrlRequest;
import com.baidu.drapi.autosdk.sms.service.GetReportFileUrlResponse;
import com.baidu.drapi.autosdk.sms.service.GetReportStateRequest;
import com.baidu.drapi.autosdk.sms.service.GetReportStateResponse;
import com.baidu.drapi.autosdk.sms.service.ReportRequestType;
import com.baidu.drapi.autosdk.sms.service.ReportService;
import com.sohu.java.httpclient.HttpClientUtil;

public class BaiduReport {

    private static CommonService factory;
    private static ReportService reportService;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    private static String fileId = "";
    private static String fileURL = "";
    private static String fileDir = "";
    private static String regionDir = "";
    private static String fileName = "";
    private static String fileSuffix = ".csv";
    private static String userName = "";
    private static String startDate = "";
    private static String endDate = "";
    private static String startTime = " 00:00:00";
    private static String endTime = " 23:59:59";
    private static int deviceType = 1;

    private static int interval = 30; // 间隔30秒

    private static Properties properties = null;
    private static final String FILE_NAME = "baidu-api.properties";

    static {
        properties = new Properties();
        InputStream is = null;
        try {
            // is =
            // Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_NAME);
            is = ClassLoader.getSystemResourceAsStream(FILE_NAME);

            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getValue(String key) {
        return (String) properties.getProperty(key);
    }

    private void readProperties() {
        userName = getValue("username");
        startDate = getValue("startDate");
        if ("".equals(startDate))
            startDate = sf.format(new Date().getTime() - 60 * 60 * 24 * 1000);
        endDate = getValue("endDate");
        if ("".equals(endDate))
            endDate = sf.format(new Date().getTime() - 60 * 60 * 24 * 1000);
        deviceType = Integer.valueOf(getValue("deviceType"));
        fileDir = getValue("fileDirectory");
        regionDir = getValue("regionDirectory");
    }

    private void printParameter() {
        System.out.println("userName: " + userName);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);
        System.out.println("deviceType: " + deviceType);
        System.out.println("fileDirectory: " + fileDir);
    }

    // setup context
    public static void setUp() {
        try {
            factory = ServiceFactory.getInstance();
            reportService = factory.getService(ReportService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    // get report id
    public void getProfessionalReportId(int deviceType, int level, int reportType) throws Exception {
        GetProfessionalReportIdRequest request = new GetProfessionalReportIdRequest();
        ReportRequestType type = new ReportRequestType();
        List<String> performanceData = Arrays
                .asList(new String[]{"cost", "cpc", "click", "impression", "ctr", "cpm", "position"});
        type.setPerformanceData(performanceData);
        type.setLevelOfDetails(level); // 2:account granularity	14:keyword
        type.setUnitOfTime(5); // 5: daily; 4: weekly; 3: monthly; 1: yearly; 7:
        // hourly; 8: (endDate-StartDate)ly
        type.setReportType(reportType); //3:region	14:keyword
        type.setDevice(deviceType); // 0 : all; 1: computer; 2: mobile
        System.out.println(startDate + startTime);
        type.setStartDate(simpleDateFormat.parse(startDate + startTime));
        type.setEndDate(simpleDateFormat.parse(endDate + endTime));
        request.setReportRequestType(type);
        GetProfessionalReportIdResponse response = reportService.getProfessionalReportId(request);
        List<GetProfessionalReportIdData> datas = response.getData();
        System.out.println(datas);
        fileId = response.getData().get(0).getReportId();
        System.out.println("ReportId: " + fileId);
        // ResHeader rheader = ResHeaderUtil.getResHeader(reportService, true);
    }

    // get report state
    public boolean getReportState() throws Exception {
        GetReportStateRequest request = new GetReportStateRequest();
        request.setReportId(fileId);
        GetReportStateResponse response = reportService.getReportState(request);
        // List<GetReportStateData> datas=response.getData();
        // System.out.println(datas);
        // ResHeader rheader = ResHeaderUtil.getResHeader(reportService, true);
        int reportState = response.getData().get(0).getIsGenerated();
        System.out.println("reportState: " + reportState);
        if (reportState == 3)
            return true;
        return false;
    }

    // get report url
    public void getReportFileUrl() throws Exception {
        GetReportFileUrlRequest request = new GetReportFileUrlRequest();
        request.setReportId(fileId);
        GetReportFileUrlResponse response = reportService.getReportFileUrl(request);
        // List<GetReportFileUrlData> datas=response.getData();
        // System.out.println(datas);
        // ResHeader rheader = ResHeaderUtil.getResHeader(reportService, true);
        fileURL = response.getData().get(0).getReportFilePath();
        System.out.println("fileURL: " + fileURL);
    }

    // download report
    public boolean downloadReportFile(int deviceType, String file) {
        String downloadFileDir = null;
        if (deviceType == 1)
            downloadFileDir = file + "pc/";
        else if (deviceType == 2)
            downloadFileDir = file + "mobile/";
        fileName = userName + "FROM" + startDate + "TO" + endDate + "AT" + sf.format(new Date()) + fileSuffix;
        System.out.println("fileName: " + fileName);
        String downloadPosition = downloadFileDir + fileName;
        if (HttpClientUtil.httpGetRequest(fileURL, downloadPosition)) {
            System.out.println(
                    "Successfully Downloaded in " + downloadPosition + " at " + simpleDateFormat.format(new Date()));
            System.out.println(
                    "##########################################################################################");
            return true;
        }
        return false;
    }

    // get report
    public void getReport(int type, int level, int reportType) {
        int reTry = 10; // retry 10
        try {
            getProfessionalReportId(type, level, reportType);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.out.println("Error to get Report ID");
            return;
        }

        while (true) {
            try {
                System.out.println("Try " + reTry);

                if (getReportState()) {
                    getReportFileUrl();
                    if (level == 11 && reportType == 14 && downloadReportFile(type, fileDir))
                        return;
                    else if (level == 2 && reportType == 3 && downloadReportFile(type, regionDir))
                        return;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if ((--reTry) == 0) {
                System.out.println("Failed to get the Report type " + type + " , please run later");
                return;
            }
            try {
                TimeUnit.SECONDS.sleep(interval);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BaiduReport gtjaR = new BaiduReport();
        gtjaR.readProperties();
        if (args.length != 0) {
            if (args.length == 6) {
                userName = args[0];
                startDate = args[1];
                endDate = args[2];
                deviceType = Integer.valueOf(args[3]);
                fileDir = args[4];
                regionDir = args[5];
                System.out.println("running in customize mode");
            } else {
                System.out.println("Parameter is wrong");
                System.out.println(
                        "For example: java -jar GTJABaiduReport.jar baidu-HQ-01-2160247 2016-03-29 2016-03-29 1 D:/Downloads/");
                System.out.println("Quit");
                return;
            }
        } else
            System.out.println("running in default mode");

        gtjaR.printParameter();
        setUp();
        if (deviceType == 0) {
            //关键词报告
            gtjaR.getReport(1, 11, 14);
            gtjaR.getReport(2, 11, 14);
            //地域报告
            gtjaR.getReport(1, 2, 3);
            gtjaR.getReport(2, 2, 3);
        } else {
            gtjaR.getReport(deviceType, 11, 14);
            gtjaR.getReport(deviceType, 2, 3);
        }
    }

}
