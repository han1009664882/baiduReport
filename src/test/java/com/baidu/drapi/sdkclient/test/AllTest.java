package com.baidu.drapi.sdkclient.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baidu.drapi.autosdk.core.CommonService;
import com.baidu.drapi.autosdk.core.ResHeader;
import com.baidu.drapi.autosdk.core.ResHeaderUtil;
import com.baidu.drapi.autosdk.core.ServiceFactory;
import com.baidu.drapi.autosdk.exception.ApiException;
import com.baidu.drapi.autosdk.sms.service.AccountInfo;
import com.baidu.drapi.autosdk.sms.service.AccountService;
import com.baidu.drapi.autosdk.sms.service.AddAdgroupRequest;
import com.baidu.drapi.autosdk.sms.service.AddAdgroupResponse;
import com.baidu.drapi.autosdk.sms.service.AddCampaignRequest;
import com.baidu.drapi.autosdk.sms.service.AddCampaignResponse;
import com.baidu.drapi.autosdk.sms.service.AddCreativeRequest;
import com.baidu.drapi.autosdk.sms.service.AddCreativeResponse;
import com.baidu.drapi.autosdk.sms.service.AddDynCreativeRequest;
import com.baidu.drapi.autosdk.sms.service.AddDynCreativeResponse;
import com.baidu.drapi.autosdk.sms.service.AddPhoneRequest;
import com.baidu.drapi.autosdk.sms.service.AddPhoneResponse;
import com.baidu.drapi.autosdk.sms.service.AddSublinkRequest;
import com.baidu.drapi.autosdk.sms.service.AddSublinkResponse;
import com.baidu.drapi.autosdk.sms.service.AddWordRequest;
import com.baidu.drapi.autosdk.sms.service.AddWordResponse;
import com.baidu.drapi.autosdk.sms.service.AdgroupService;
import com.baidu.drapi.autosdk.sms.service.AdgroupType;
import com.baidu.drapi.autosdk.sms.service.CampaignService;
import com.baidu.drapi.autosdk.sms.service.CampaignType;
import com.baidu.drapi.autosdk.sms.service.CreativeService;
import com.baidu.drapi.autosdk.sms.service.CreativeType;
import com.baidu.drapi.autosdk.sms.service.DynCreativeType;
import com.baidu.drapi.autosdk.sms.service.DynamicCreativeService;
import com.baidu.drapi.autosdk.sms.service.GetAccountInfoRequest;
import com.baidu.drapi.autosdk.sms.service.GetAccountInfoResponse;
import com.baidu.drapi.autosdk.sms.service.GetAdgroupRequest;
import com.baidu.drapi.autosdk.sms.service.GetAdgroupResponse;
import com.baidu.drapi.autosdk.sms.service.GetCampaignRequest;
import com.baidu.drapi.autosdk.sms.service.GetCampaignResponse;
import com.baidu.drapi.autosdk.sms.service.GetCreativeRequest;
import com.baidu.drapi.autosdk.sms.service.GetCreativeResponse;
import com.baidu.drapi.autosdk.sms.service.GetDynCreativeRequest;
import com.baidu.drapi.autosdk.sms.service.GetDynCreativeResponse;
import com.baidu.drapi.autosdk.sms.service.GetPhoneRequest;
import com.baidu.drapi.autosdk.sms.service.GetPhoneResponse;
import com.baidu.drapi.autosdk.sms.service.GetSublinkBySublinkIdRequest;
import com.baidu.drapi.autosdk.sms.service.GetSublinkBySublinkIdResponse;
import com.baidu.drapi.autosdk.sms.service.GetWordRequest;
import com.baidu.drapi.autosdk.sms.service.GetWordResponse;
import com.baidu.drapi.autosdk.sms.service.KeywordService;
import com.baidu.drapi.autosdk.sms.service.KeywordType;
import com.baidu.drapi.autosdk.sms.service.NewCreativeService;
import com.baidu.drapi.autosdk.sms.service.PhoneType;
import com.baidu.drapi.autosdk.sms.service.SublinkInfo;
import com.baidu.drapi.autosdk.sms.service.SublinkType;
import com.baidu.drapi.autosdk.sms.service.UpdateAccountInfoRequest;
import com.baidu.drapi.autosdk.sms.service.UpdateAccountInfoResponse;
import com.baidu.drapi.autosdk.sms.service.UpdateAdgroupRequest;
import com.baidu.drapi.autosdk.sms.service.UpdateAdgroupResponse;
import com.baidu.drapi.autosdk.sms.service.UpdateCampaignRequest;
import com.baidu.drapi.autosdk.sms.service.UpdateCampaignResponse;
import com.baidu.drapi.autosdk.sms.service.UpdateCreativeRequest;
import com.baidu.drapi.autosdk.sms.service.UpdateCreativeResponse;
import com.baidu.drapi.autosdk.sms.service.UpdateDynCreativeRequest;
import com.baidu.drapi.autosdk.sms.service.UpdateDynCreativeResponse;
import com.baidu.drapi.autosdk.sms.service.UpdatePhoneRequest;
import com.baidu.drapi.autosdk.sms.service.UpdatePhoneResponse;
import com.baidu.drapi.autosdk.sms.service.UpdateSublinkRequest;
import com.baidu.drapi.autosdk.sms.service.UpdateSublinkResponse;
import com.baidu.drapi.autosdk.sms.service.UpdateWordRequest;
import com.baidu.drapi.autosdk.sms.service.UpdateWordResponse;
/**
 * 该测试主要包涵账户、计划、单元、关键词、创意、动态创意、附件创意的CRUD操作。
 * 对于整账户、报告、KR、Toolkit、Search直接运行相关test即可
 * */
public class AllTest {

	private static CommonService factory;

	private static AccountService accountService;
	private static CampaignService campaignService;
	private static AdgroupService adgroupService;
	private static KeywordService keywordService;
	private static CreativeService creativeService;
	private static DynamicCreativeService dynamicCreativeService;
	private static NewCreativeService newCreativeService;

	private static String[] accountFields = { "userId" };
	private static String[] campaignFields = { "campaignName", "campaignId" };
	private static String[] adgroupFields = { "adgroupId" };
	private static String[] wordFields = { "keywordId", "keyword" };
	private static String[] creativeFields = { "title", "creativeId",
			"description1", "pcDestinationUrl" };
	private static String[] newCreativeFields = {"adgroupId", "sublinkInfos", "sublinkId"};
	private static String[] phoneFields = {"phoneId", "phoneNum", "adgroupId"};

	private static String SUCCESS = "success";

	private static final Double BALANCE = 110D;
	private static final Double MAXPRICE = 30D;

	private static final int CAMPAIGN_COUNT = 1;
	private static final int ADGROUP_COUNT = 1;
	private static final int WORD_COUNT = 1;
	private static final int CREATIVE_COUNT = 1;
	private static final int SUBLINK_COUNT = 1;
	private static final int SUBLINKINFO_COUNT = 3;

	private static final int IDTYPE = 11;
	private static final int CREATIVE_IDTYPE= 7;
	private static final int PHONE_ID_TYPE=9;

	private static final int ISTMP = 0;

	private static final String DESC1 = "add_creative_by_zxt";
	private static final String URL = "https://www.baidu.com";
	private static final String MODIFY_DESC1 = "modify_creative_by_zxt";

	@Before
	public void setUp() {
		try {
			factory = ServiceFactory.getInstance();
			accountService = factory.getService(AccountService.class);
			campaignService = factory.getService(CampaignService.class);
			adgroupService = factory.getService(AdgroupService.class);
			keywordService = factory.getService(KeywordService.class);
			creativeService = factory.getService(CreativeService.class);
			dynamicCreativeService = factory
					.getService(DynamicCreativeService.class);
			newCreativeService = factory.getService(NewCreativeService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private AccountInfo getAccountInfo() throws ApiException {
		GetAccountInfoRequest request = new GetAccountInfoRequest();
		request.setAccountFields(Arrays.asList(accountFields));
		GetAccountInfoResponse response = accountService
				.getAccountInfo(request);
		ResHeader resHeader = ResHeaderUtil.getResHeader(accountService, true);
		Assert.assertTrue(SUCCESS.equals(resHeader.getDesc())
				&& resHeader.getStatus() == 0);
		List<AccountInfo> data = response.getData();
		return data.get(0);
	}

	private void updAccountInfo() throws ApiException {
		AccountInfo accountInfo = getAccountInfo();
		accountInfo.setBudget(BALANCE);
		UpdateAccountInfoRequest request = new UpdateAccountInfoRequest();
		request.setAccountInfo(accountInfo);
		UpdateAccountInfoResponse response = accountService
				.updateAccountInfo(request);
		List<AccountInfo> data = response.getData();
		ResHeader resHeader = ResHeaderUtil.getResHeader(accountService, true);
		Assert.assertTrue(SUCCESS.equals(resHeader.getDesc())
				&& resHeader.getStatus() == 0);
		System.out.println(data);
	}

	private List<CampaignType> AddCampaign() throws ApiException {
		AddCampaignRequest request = new AddCampaignRequest();
		List<CampaignType> campaignTypes = new ArrayList<CampaignType>();
		for (int i = 0; i < CAMPAIGN_COUNT; i++) {
			CampaignType campaignType = new CampaignType();
			campaignType.setCampaignName(UUID.randomUUID().toString()
					.substring(0, 29));
			campaignTypes.add(campaignType);
		}
		request.setCampaignTypes(campaignTypes);
		AddCampaignResponse response = campaignService.addCampaign(request);
		ResHeader resHeader = ResHeaderUtil.getResHeader(campaignService, true);
		Assert.assertTrue(SUCCESS.equals(resHeader.getDesc())
				&& resHeader.getStatus() == 0);
		List<CampaignType> data = response.getData();
		return data;
	}

	private List<CampaignType> GetCampaign(List<CampaignType> campaignTypes)
			throws ApiException {
		GetCampaignRequest request = new GetCampaignRequest();
		List<Long> ids = new ArrayList<Long>();
		for (CampaignType campaignType : campaignTypes) {
			ids.add(campaignType.getCampaignId());
		}
		request.setCampaignFields(Arrays.asList(campaignFields));
		request.setCampaignIds(ids);
		GetCampaignResponse response = campaignService.getCampaign(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(campaignService, true);
		List<CampaignType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<CampaignType> UpdCampaign(List<CampaignType> campaignTypes)
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

	private List<AdgroupType> addAdgroup(Long campaignId) throws ApiException {
		AddAdgroupRequest request = new AddAdgroupRequest();
		List<AdgroupType> list = new ArrayList<AdgroupType>();
		for (int i = 0; i < ADGROUP_COUNT; i++) {
			AdgroupType adgroupType = new AdgroupType();
			adgroupType.setAdgroupName(UUID.randomUUID().toString()
					.substring(0, 29));
			adgroupType.setCampaignId(campaignId);
			adgroupType.setMaxPrice(MAXPRICE);
			list.add(adgroupType);
		}
		request.setAdgroupTypes(list);
		AddAdgroupResponse response = adgroupService.addAdgroup(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(adgroupService, true);
		List<AdgroupType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<AdgroupType> updAdgroup(List<AdgroupType> adgroupTypes)
			throws ApiException {
		UpdateAdgroupRequest request = new UpdateAdgroupRequest();
		for (AdgroupType adgroupType : adgroupTypes) {
			adgroupType.setAdgroupName(UUID.randomUUID().toString()
					.substring(0, 29));
		}
		request.setAdgroupTypes(adgroupTypes);
		UpdateAdgroupResponse response = adgroupService.updateAdgroup(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(adgroupService, true);
		List<AdgroupType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<AdgroupType> getAdgroup(List<AdgroupType> adgroupTypes)
			throws ApiException {
		GetAdgroupRequest request = new GetAdgroupRequest();
		List<Long> ids = new ArrayList<Long>();
		for (AdgroupType adgroupType : adgroupTypes) {
			ids.add(adgroupType.getAdgroupId());
		}
		request.setAdgroupFields(Arrays.asList(adgroupFields));
		request.setIds(ids);
		request.setIdType(5);
		GetAdgroupResponse response = adgroupService.getAdgroup(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(adgroupService, true);
		List<AdgroupType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<KeywordType> addWord(Long adgroupId) throws ApiException {
		AddWordRequest request = new AddWordRequest();
		List<KeywordType> list = new ArrayList<KeywordType>();
		for (int i = 0; i < WORD_COUNT; i++) {
			KeywordType keywordType = new KeywordType();
			keywordType.setKeyword(UUID.randomUUID().toString()
					.substring(0, 29));
			keywordType.setAdgroupId(adgroupId);
			list.add(keywordType);
		}
		request.setKeywordTypes(list);
		AddWordResponse response = keywordService.addWord(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(keywordService, true);
		List<KeywordType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<KeywordType> updWord(List<KeywordType> keywordTypes)
			throws ApiException {
		UpdateWordRequest request = new UpdateWordRequest();
		for (KeywordType keywordType : keywordTypes) {
			keywordType.setKeyword(UUID.randomUUID().toString()
					.substring(0, 29));
		}
		request.setKeywordTypes(keywordTypes);
		UpdateWordResponse response = keywordService.updateWord(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(keywordService, true);
		List<KeywordType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<KeywordType> getWord(List<KeywordType> keywordTypes)
			throws ApiException {
		GetWordRequest request = new GetWordRequest();
		List<Long> ids = new ArrayList<Long>();
		for (KeywordType keywordType : keywordTypes) {
			ids.add(keywordType.getKeywordId());
		}
		request.setWordFields(Arrays.asList(wordFields));
		request.setIds(ids);
		request.setIdType(IDTYPE);
		request.setGetTemp(ISTMP);
		GetWordResponse response = keywordService.getWord(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(keywordService, true);
		List<KeywordType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<CreativeType> getCreative(List<CreativeType> creativeTypes)
			throws ApiException {
		GetCreativeRequest request = new GetCreativeRequest();
		List<Long> ids = new ArrayList<Long>();
		for (CreativeType creativeType : creativeTypes) {
			ids.add(creativeType.getCreativeId());
		}
		request.setCreativeFields(Arrays.asList(creativeFields));
		request.setIds(ids);
		request.setIdType(CREATIVE_IDTYPE);
		request.setGetTemp(ISTMP);
		GetCreativeResponse response = creativeService.getCreative(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(creativeService, true);
		List<CreativeType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<CreativeType> addCreative(Long adgroupId) throws ApiException {
		AddCreativeRequest request = new AddCreativeRequest();
		List<CreativeType> list = new ArrayList<CreativeType>();
		for (int i = 0; i < CREATIVE_COUNT; i++) {
			CreativeType creativeType = new CreativeType();
			creativeType
					.setTitle(UUID.randomUUID().toString().substring(0, 29));
			creativeType.setAdgroupId(adgroupId);
			creativeType.setDescription1(DESC1);
			creativeType.setPcDestinationUrl(URL);
			list.add(creativeType);
		}
		request.setCreativeTypes(list);
		AddCreativeResponse response = creativeService.addCreative(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(creativeService, true);
		List<CreativeType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	private List<CreativeType> updCreative(List<CreativeType> creativeTypes)
			throws ApiException {
		UpdateCreativeRequest request = new UpdateCreativeRequest();
		for (CreativeType creativeType : creativeTypes) {
			creativeType
					.setTitle(UUID.randomUUID().toString().substring(0, 29));
			creativeType.setDescription1(MODIFY_DESC1);
		}
		request.setCreativeTypes(creativeTypes);
		UpdateCreativeResponse response = creativeService
				.updateCreative(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(creativeService, true);
		List<CreativeType> datas = response.getData();
		System.out.println(datas);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return datas;
	}

	public List<DynCreativeType> addDynCreative(Long campaignId, Long adgourpId)
			throws ApiException {
		AddDynCreativeRequest request = new AddDynCreativeRequest();

		DynCreativeType dynCreative = new DynCreativeType();
		dynCreative.setCampaignId(campaignId);
		dynCreative.setAdgroupId(adgourpId);
		dynCreative.setBindingType(3);
		dynCreative.setTitle("sdktest_1");
		dynCreative.setMurl("http://www.baidu.com");
		dynCreative.setUrl("http://www.baidu.com");
		List<DynCreativeType> dynCreativeFragments = new ArrayList<DynCreativeType>();
		dynCreativeFragments.add(dynCreative);
		request.setDynCreativeTypes(dynCreativeFragments);
		AddDynCreativeResponse response = dynamicCreativeService
				.addDynCreative(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(dynamicCreativeService,
				true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return response.getData();
	}

	public List<DynCreativeType> updateDynCreatives(List<DynCreativeType> params)
			throws ApiException {
		UpdateDynCreativeRequest request = new UpdateDynCreativeRequest();
		for (DynCreativeType t : params) {
			t.setTitle("modify-" + t.getTitle());
		}
		request.setDynCreativeTypes(params);
		UpdateDynCreativeResponse response = dynamicCreativeService
				.updateDynCreative(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(dynamicCreativeService,
				true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return response.getData();
	}

	private List<DynCreativeType> getDynCreative(List<DynCreativeType> params)
			throws ApiException {
		GetDynCreativeRequest request = new GetDynCreativeRequest();
		List<Long> ids = new ArrayList<Long>();
		for (DynCreativeType t : params) {
			ids.add(t.getDynCreativeId());
		}
		List<String> fields = new ArrayList<String>();
		fields.add("dynCreativeId");
		fields.add("campaignId");
		fields.add("adgroupId");
		fields.add("bindingType");
		fields.add("url");
		fields.add("murl");
		request.setDynCreativeFields(fields);
		request.setIds(ids);
		request.setIdType(13);
		GetDynCreativeResponse response = dynamicCreativeService
				.getDynCreative(request);
		ResHeader rheader = ResHeaderUtil.getResHeader(dynamicCreativeService,
				true);
		Assert.assertTrue(SUCCESS.equals(rheader.getDesc())
				&& rheader.getStatus() == 0);
		return response.getData();
	}

	private List<SublinkType> getSublink(List<SublinkType> sublinkTypes)
			throws ApiException {
		GetSublinkBySublinkIdRequest request = new GetSublinkBySublinkIdRequest();
		List<Long> ids = new ArrayList<Long>();
		for (SublinkType sublinkType : sublinkTypes) {
			ids.add(sublinkType.getSublinkId());
		}
		request.setSublinkFields(Arrays.asList(newCreativeFields));
		request.setIds(ids);
		request.setIdType(12);
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

	private List<SublinkType> addSublink(Long adgroupId) throws ApiException {
		AddSublinkRequest request = new AddSublinkRequest();
		List<SublinkType> list = new ArrayList<SublinkType>();
		for (int i = 0; i < SUBLINK_COUNT; i++) {
			List<SublinkInfo> sublinkInfos = new ArrayList<SublinkInfo>();
			for (int j = 0; j < SUBLINKINFO_COUNT; j++) {
				SublinkInfo info = new SublinkInfo();
				info.setDescription(UUID.randomUUID().toString()
						.substring(0, 15));
				info.setDestinationUrl(URL);
				sublinkInfos.add(info);
			}
			SublinkType sublinkType = new SublinkType();
			sublinkType.setAdgroupId(adgroupId);
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

	private List<SublinkType> updSublink(List<SublinkType> sublinkTypes)
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
	
	private List<PhoneType> addPhone(Long adgroupId) throws ApiException {
        AddPhoneRequest request=new AddPhoneRequest();
        List<PhoneType> phoneTypes=new ArrayList<PhoneType>();
        PhoneType phoneType=new PhoneType();
        phoneType.setAdgroupId(adgroupId);
        phoneType.setPhoneNum("010-58846789");
        phoneTypes.add(phoneType);
        request.setPhoneTypes(phoneTypes);
        AddPhoneResponse response=newCreativeService.addPhone(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(newCreativeService, true);
        List<PhoneType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }
    private List<PhoneType> getPhone(List<PhoneType> phoneTypes) throws ApiException{
        GetPhoneRequest request=new GetPhoneRequest();
        List<Long> ids=new ArrayList<Long>();
        for(PhoneType type:phoneTypes){
            ids.add(type.getPhoneId());
        }
        request.setIds(ids);
        request.setIdType(PHONE_ID_TYPE);
        request.setPhoneFields(Arrays.asList(phoneFields));
        GetPhoneResponse response=newCreativeService.getPhone(request);
        ResHeader rheader = ResHeaderUtil.getResHeader(newCreativeService, true);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return response.getData();
    }
    private List<PhoneType> updatePhone(List<PhoneType> phoneTypes)  throws ApiException {
        UpdatePhoneRequest request=new UpdatePhoneRequest();
        phoneTypes.get(0).setPhoneNum("010-58846888");
        phoneTypes.get(0).setPause(true);
        request.setPhoneTypes(phoneTypes);
        UpdatePhoneResponse response=newCreativeService.updatePhone(request);
        request.setPhoneTypes(phoneTypes);
        ResHeader rheader = ResHeaderUtil.getResHeader(newCreativeService, true);
        List<PhoneType> datas = response.getData();
        System.out.println(datas);
        Assert.assertTrue(SUCCESS.equals(rheader.getDesc()) && rheader.getStatus() == 0);
        return datas;
    }

	@Test
	public void testCommonService() throws ApiException {
		this.updAccountInfo();
		List<CampaignType> campaignTypes = this.AddCampaign();

		campaignTypes = this.UpdCampaign(campaignTypes);

		campaignTypes = this.GetCampaign(campaignTypes);

		Long campaignId = campaignTypes.get(0).getCampaignId();

		List<AdgroupType> adgroupTypes = this.addAdgroup(campaignId);

		adgroupTypes = this.updAdgroup(adgroupTypes);

		adgroupTypes = this.getAdgroup(adgroupTypes);

		Long adgroupId = adgroupTypes.get(0).getAdgroupId();

		List<KeywordType> keywordTypes = this.addWord(adgroupId);

		keywordTypes = this.updWord(keywordTypes);

		keywordTypes = this.getWord(keywordTypes);

		List<CreativeType> creativeTypes = this.addCreative(adgroupId);

		creativeTypes = this.updCreative(creativeTypes);

		creativeTypes = this.getCreative(creativeTypes);

		List<DynCreativeType> dynCreativeTypes = this.addDynCreative(
				campaignId, adgroupId);

		dynCreativeTypes = this.updateDynCreatives(dynCreativeTypes);

		dynCreativeTypes = this.getDynCreative(dynCreativeTypes);
		
		List<SublinkType> sublinkTypes = this.addSublink(adgroupId);
		
		sublinkTypes = updSublink(sublinkTypes);
		
		sublinkTypes = getSublink(sublinkTypes);
		
		List<PhoneType> phoneTypes = this.addPhone(adgroupId);
		
		phoneTypes = this.updatePhone(phoneTypes);
		
		phoneTypes = this.getPhone(phoneTypes);
	}
}
