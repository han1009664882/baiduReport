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
public class NewCreativeServiceTest {
	private static CommonService factory;
	private NewCreativeService newCreativeService;
	private static final int BEAN_COUNT = 1;
	private static final int SUBLINKINFO_COUNT = 3;
	private static String[] fields = { "adgroupId", "sublinkInfos", "sublinkId" };
	private static String SUCCESS = "success";
	private static final Long ADGROUPID = 2088608210L;
	private static final int IDTYPE = 12;
	private static final int ISTMP = 0;
	private static final int PHONE_ID_TYPE = 9;
	private static String[] phoneFields = { "phoneId", "phoneNum", "adgroupId" };
	private static final int BRIDGE_ID_TYPE = 5;
	private static String[] bridgeFields = { "bridgeId", "adgroupId", "pause" };
	private static final int ECALL_ID_TYPE = 23;
	private static String[] ecallFields = { "ecallId", "adgroupId",
			"ecallGroupName" };
	private static final String URL = "https://www.baidu.com";
	private static final long ECALLGROUPID = 1395;

	@Before
	public void setUp() {
		try {
			factory = ServiceFactory.getInstance();
			newCreativeService = factory.getService(NewCreativeService.class);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	private List<SublinkType> innerGet(List<SublinkType> sublinkTypes)
			throws ApiException {
		GetSublinkBySublinkIdRequest request = new GetSublinkBySublinkIdRequest();
		List<Long> ids = new ArrayList<Long>();
		for (SublinkType sublinkType : sublinkTypes) {
			ids.add(sublinkType.getSublinkId());
		}
		request.setSublinkFields(Arrays.asList(fields));
		request.setIds(ids);
		request.setIdType(IDTYPE);
		request.setGetTemp(ISTMP);
		request.setDevice(0);
		GetSublinkBySublinkIdResponse response = newCreativeService
				.getSublink(request);
		ResHeader rheader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		List<SublinkType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<SublinkType> innerAdd() throws ApiException {
		AddSublinkRequest request = new AddSublinkRequest();
		List<SublinkType> list = new ArrayList<SublinkType>();
		for (int i = 0; i < BEAN_COUNT; i++) {
			List<SublinkInfo> sublinkInfos = new ArrayList<SublinkInfo>();
			for (int j = 0; j < SUBLINKINFO_COUNT; j++) {
				SublinkInfo info = new SublinkInfo();
				info.setDescription(UUID.randomUUID().toString()
						.substring(0, 15));
				info.setDestinationUrl(URL);
				sublinkInfos.add(info);
			}
			SublinkType sublinkType = new SublinkType();
			sublinkType.setAdgroupId(ADGROUPID);
			sublinkType.setSublinkInfos(sublinkInfos);
			sublinkType.setDevice(0);
			list.add(sublinkType);
		}
		request.setSublinkTypes(list);
		AddSublinkResponse response = newCreativeService.addSublink(request);
		ResHeader rheader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		List<SublinkType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<SublinkType> innerUpdate(List<SublinkType> sublinkTypes)
			throws ApiException {
		UpdateSublinkRequest request = new UpdateSublinkRequest();
		for (SublinkType sublinkType : sublinkTypes) {
			List<SublinkInfo> sublinkInfos = sublinkType.getSublinkInfos();
			for (SublinkInfo info : sublinkInfos) {
				info.setDescription(UUID.randomUUID().toString()
						.substring(0, 15));
			}
		}
		request.setSublinkTypes(sublinkTypes);
		UpdateSublinkResponse response = newCreativeService
				.updateSublink(request);
		ResHeader rheader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		List<SublinkType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private void innerDelete(List<SublinkType> sublinkTypes)
			throws ApiException {
		DeleteSublinkRequest request = new DeleteSublinkRequest();
		List<Long> ids = new ArrayList<Long>();
		for (SublinkType sublinkType : sublinkTypes) {
			ids.add(sublinkType.getSublinkId());
		}
		request.setSublinkIds(ids);
		DeleteSublinkResponse response = newCreativeService
				.deleteSublink(request);
		ResHeader rheader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		List<SublinkType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
	}

	private List<PhoneType> addPhone() throws ApiException {
		AddPhoneRequest request = new AddPhoneRequest();
		List<PhoneType> phoneTypes = new ArrayList<PhoneType>();
		PhoneType phoneType = new PhoneType();
		phoneType.setAdgroupId(ADGROUPID);
		phoneType.setPhoneNum("010-58846789");
		phoneTypes.add(phoneType);
		request.setPhoneTypes(phoneTypes);
		AddPhoneResponse response = newCreativeService.addPhone(request);
		ResHeader rheader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		List<PhoneType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<PhoneType> getPhone(List<PhoneType> phoneTypes)
			throws ApiException {
		GetPhoneRequest request = new GetPhoneRequest();
		List<Long> ids = new ArrayList<Long>();
		for (PhoneType type : phoneTypes) {
			ids.add(type.getPhoneId());
		}
		request.setIds(ids);
		request.setIdType(PHONE_ID_TYPE);
		request.setPhoneFields(Arrays.asList(phoneFields));
		GetPhoneResponse response = newCreativeService.getPhone(request);
		ResHeader rheader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		return response.getData();
	}

	private void updatePhone(List<PhoneType> phoneTypes) throws ApiException {
		UpdatePhoneRequest request = new UpdatePhoneRequest();
		phoneTypes.get(0).setPhoneNum("010-58846788");
		request.setPhoneTypes(phoneTypes);
		UpdatePhoneResponse response = newCreativeService.updatePhone(request);

		ResHeader rheader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		List<PhoneType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
	}

	private List<BridgeType> addBridge() throws ApiException {
		AddBridgeRequest request = new AddBridgeRequest();
		List<BridgeType> bridgeTypes = new ArrayList<BridgeType>();
		BridgeType bridgeType = new BridgeType();
		bridgeType.setAdgroupId(ADGROUPID);
		bridgeTypes.add(bridgeType);
		request.setBridgeTypes(bridgeTypes);
		AddBridgeResponse response = newCreativeService.addBridge(request);
		ResHeader rheader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		return response.getData();
	}

	private List<BridgeType> getBridge(List<BridgeType> bridgeTypes)
			throws ApiException {
		GetBridgeRequest request = new GetBridgeRequest();
		List<Long> ids = new ArrayList<Long>();
		for (BridgeType bridgeType : bridgeTypes) {
			ids.add(bridgeType.getBridgeId());
		}
		request.setIds(ids);
		request.setIdType(BRIDGE_ID_TYPE);
		request.setBridgeFields(Arrays.asList(bridgeFields));
		GetBridgeResponse response = newCreativeService.getBridge(request);
		ResHeader rheader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		return response.getData();
	}

	private void updateBridge(List<BridgeType> bridgeTypes) throws ApiException {
		UpdateBridgeRequest request = new UpdateBridgeRequest();
		for (BridgeType bridgeType : bridgeTypes) {
			bridgeType.setPause(true);
		}
		request.setBridgeTypes(bridgeTypes);
		UpdateBridgeResponse response = newCreativeService
				.updateBridge(request);
		ResHeader rheader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
	}

	private List<EcallType> addEcall() throws ApiException {
		AddEcallRequest request = new AddEcallRequest();
		List<EcallType> ecallTypes = new ArrayList<EcallType>();
		EcallType ecallType = new EcallType();
		ecallType.setAdgroupId(ADGROUPID);
		ecallType.setEcallGroupId(ECALLGROUPID);
		ecallTypes.add(ecallType);
		request.setEcallTypes(ecallTypes);
		AddEcallResponse response = newCreativeService.addEcall(request);
		ResHeader rHeader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		List<EcallType> datas = response.getData();
		Assert.assertTrue(SUCCESS.equals(rHeader.getDesc())
				&& rHeader.getStatus() == 0);
		return datas;
	}

	private List<EcallType> getEcall(List<EcallType> ecallTypes)
			throws ApiException {
		GetEcallRequest request = new GetEcallRequest();
		List<Long> ids = new ArrayList<Long>();
		for (EcallType ecallType : ecallTypes) {
			ids.add(ecallType.getEcallId());
		}
		request.setIds(ids);
		request.setIdType(ECALL_ID_TYPE);
		request.setEcallFields(Arrays.asList(ecallFields));
		GetEcallResponse response = newCreativeService.getEcall(request);
		return response.getData();
	}

	private void UpdateEcall(List<EcallType> ecallTypes) throws ApiException {
		UpdateEcallRequest request = new UpdateEcallRequest();
		ecallTypes.get(0).setPause(true);
		request.setEcallTypes(ecallTypes);
		UpdateEcallResponse response = newCreativeService.updateEcall(request);
		ResHeader resHeader = ResHeaderUtil.getResHeader(newCreativeService,
				true);
		List<EcallType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(resHeader.getDesc())
				&& resHeader.getStatus() == 0);
	}

	private void getEcallGroup() throws ApiException {
		GetEcallGroupRequest request = new GetEcallGroupRequest();
		GetEcallGroupResponse response = newCreativeService
				.getEcallGroup(request);
		ResHeader rHeader = ResHeaderUtil
				.getResHeader(newCreativeService, true);
		List<EcallGroupType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rHeader.getDesc())
				&& rHeader.getStatus() == 0);
	}

	@Test
	public void testCommonService() throws ApiException {
		List<SublinkType> datas = this.innerAdd();
		datas = this.innerGet(datas);
		datas = this.innerUpdate(datas);
		this.innerDelete(datas);
		List<PhoneType> phoneTypes = this.addPhone();
		phoneTypes = this.getPhone(phoneTypes);
		this.updatePhone(phoneTypes);
		List<BridgeType> bridgeTypes = this.addBridge();
		bridgeTypes = this.getBridge(bridgeTypes);
		this.updateBridge(bridgeTypes);
		List<EcallType> ecallTypes = this.addEcall();
		ecallTypes = this.getEcall(ecallTypes);
		this.UpdateEcall(ecallTypes);
		this.getEcallGroup();
	}
}
