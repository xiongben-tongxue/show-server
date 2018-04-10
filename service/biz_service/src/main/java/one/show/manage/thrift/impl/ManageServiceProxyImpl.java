package one.show.manage.thrift.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import one.show.common.Constant.ADMIN_STATUS;
import one.show.common.exception.ServiceException;
import one.show.manage.domain.Advertisement;
import one.show.manage.domain.FanLevel;
import one.show.manage.domain.Gift;
import one.show.manage.domain.GiftType;
import one.show.manage.domain.HeaderPortrait;
import one.show.manage.domain.Link;
import one.show.manage.domain.MasterLevel;
import one.show.manage.domain.Notice;
import one.show.manage.domain.Register;
import one.show.manage.domain.Report;
import one.show.manage.domain.ReturnList;
import one.show.manage.domain.RobotSendGiftRatio;
import one.show.manage.domain.SendExchange;
import one.show.manage.domain.SystemConfig;
import one.show.manage.domain.UserCDN;
import one.show.manage.domain.VersionControl;
import one.show.manage.service.AdvertisementService;
import one.show.manage.service.GiftService;
import one.show.manage.service.HeaderPortraitService;
import one.show.manage.service.LevelService;
import one.show.manage.service.LinkService;
import one.show.manage.service.NoticeService;
import one.show.manage.service.RegisterService;
import one.show.manage.service.ReportService;
import one.show.manage.service.SendExchangeService;
import one.show.manage.service.SystemConfigService;
import one.show.manage.service.UserCDNService;
import one.show.manage.service.VersionControlService;
import one.show.manage.thrift.iface.ManageServiceProxy.Iface;
import one.show.manage.thrift.view.AdminHeaderPortraitListView;
import one.show.manage.thrift.view.AdvertisementView;
import one.show.manage.thrift.view.FanLevelView;
import one.show.manage.thrift.view.GiftListView;
import one.show.manage.thrift.view.GiftTypeView;
import one.show.manage.thrift.view.GiftView;
import one.show.manage.thrift.view.HeaderPortraitView;
import one.show.manage.thrift.view.LinkView;
import one.show.manage.thrift.view.MasterLevelView;
import one.show.manage.thrift.view.NoticeListView;
import one.show.manage.thrift.view.NoticeView;
import one.show.manage.thrift.view.RegisterListView;
import one.show.manage.thrift.view.RegisterView;
import one.show.manage.thrift.view.ReportView;
import one.show.manage.thrift.view.RobotSendGiftRatioView;
import one.show.manage.thrift.view.SendExchangeView;
import one.show.manage.thrift.view.SystemConfigView;
import one.show.manage.thrift.view.UserCDNListView;
import one.show.manage.thrift.view.UserCDNView;
import one.show.manage.thrift.view.VersionControlView;
import one.show.user.service.RobotService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus 2018年1月14日 下午1:47:50
 * 
 */

@Component("manageServiceProxyImpl")
public class ManageServiceProxyImpl implements Iface {

	private static Logger logger = LoggerFactory
			.getLogger(ManageServiceProxyImpl.class);

	@Autowired
	VersionControlService versionControlService;
	@Autowired
	LinkService linkService;
	@Autowired
	ReportService reportService;
	@Autowired
    HeaderPortraitService headerPortraitService;
	@Autowired
	GiftService giftService;
	@Autowired
	LevelService levelService;
	@Autowired
	SendExchangeService sendExchangeService;
	@Autowired
	SystemConfigService systemConfigService;
	@Autowired
	AdvertisementService advertisementService;
	@Autowired
	RegisterService registerService;
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	RobotService robotService;
	
	@Autowired
	UserCDNService userCDNService;

	@Override
	public VersionControlView findVersionControlByAgentType(int agentType)
			throws TException {
		VersionControlView versionControlView = null;
		try {
			VersionControl versionControl = versionControlService
					.findVersionControl(agentType);
			if (versionControl != null) {
				versionControlView = new VersionControlView();
				BeanUtils.copyProperties(versionControl, versionControlView);
			}
		} catch (Exception e) {
			throw new TException(e);
		}

		return versionControlView;
	}

	
	@Override
	public List<LinkView> findPlazaLinks() throws TException {
		List<LinkView> linkViewList = null;
		try {
			List<Link> linkList = linkService.findLinkList();
			if (linkList != null) {
				linkViewList = new ArrayList<LinkView>();
				for (Link link : linkList) {
					if (link != null) {
						LinkView linkView = new LinkView();
						BeanUtils.copyProperties(link, linkView);
						linkViewList.add(linkView);
					}
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return linkViewList;
	}

	@Override
	public void saveReport(ReportView reportView) throws TException {
		try {
			if (reportView != null) {
				Report report = new Report();
				BeanUtils.copyProperties(reportView, report);
				reportService.saveReport(report);
			}
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * one.show.manage.thrift.iface.ManageServiceProxy.Iface#findGiftList(int,
	 * int, int, int)
	 */
	@Override
	public GiftListView findGiftList(int giftType, int status, int start,
			int count) throws TException {

		ADMIN_STATUS s = null;
		try {
			s = ADMIN_STATUS.values()[status];
		} catch (Exception e) {
		}

		try {
			ReturnList<Gift> returnList = giftService.getGiftList(
					giftType < 0 ? null : giftType, s, start, count);
			GiftListView giftListView = new GiftListView();
			giftListView.giftList = new ArrayList<GiftView>();

			if (returnList != null && returnList.objects != null) {
				for (Gift gift : returnList.objects) {
					GiftView giftView = new GiftView();
					BeanUtils.copyProperties(gift, giftView);
					giftListView.giftList.add(giftView);
				}
			}

			giftListView.count = returnList.count;
			return giftListView;

		} catch (ServiceException e) {
			throw new TException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * one.show.manage.thrift.iface.ManageServiceProxy.Iface#saveGift(com.
	 * weipai.manage.thrift.view.GiftView)
	 */
	@Override
	public void saveGift(GiftView giftView) throws TException {

		Gift gift = new Gift();
		BeanUtils.copyProperties(giftView, gift);

		try {
			giftService.save(gift);
		} catch (ServiceException e) {
			throw new TException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * one.show.manage.thrift.iface.ManageServiceProxy.Iface#deleteGift(int)
	 */
	@Override
	public void deleteGift(int giftId) throws TException {

		try {
			giftService.delete(giftId);
		} catch (ServiceException e) {
			throw new TException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * one.show.manage.thrift.iface.ManageServiceProxy.Iface#findGiftTypeList
	 * (int)
	 */
	@Override
	public List<GiftTypeView> findGiftTypeList(int status) throws TException {

		ADMIN_STATUS adminStatus = null;

		try {
			adminStatus = ADMIN_STATUS.values()[status];
		} catch (Exception e) {
		}

		List<GiftTypeView> viewList = new ArrayList<GiftTypeView>();
		List<GiftType> list = null;
		try {
			list = giftService.getGiftTypeList(adminStatus);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		if (list != null) {
			for (GiftType giftType : list) {
				GiftTypeView giftTypeView = new GiftTypeView();
				BeanUtils.copyProperties(giftType, giftTypeView);
				viewList.add(giftTypeView);
			}
		}
		return viewList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * one.show.manage.thrift.iface.ManageServiceProxy.Iface#findGiftById(int)
	 */
	@Override
	public GiftView findGiftById(int giftId) throws TException {
		GiftView giftView = new GiftView();
		Gift gift = null;
		try {
			gift = giftService.getGiftById(giftId);
		} catch (ServiceException e) {
			throw new TException(e);
		}

		if (gift != null) {
			BeanUtils.copyProperties(gift, giftView);
			return giftView;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * one.show.manage.thrift.iface.ManageServiceProxy.Iface#updateGift(int,
	 * java.util.Map)
	 */
	@Override
	public void updateGift(int giftId, Map<String, String> updateContent)
			throws TException {
		try {
			giftService.update(giftId, updateContent);
		} catch (ServiceException e) {
			throw new TException(e);
		}

	}




	@Override
	public void saveSendExchange(SendExchangeView sendExchangeView)
			throws TException {
		try {
			if (sendExchangeView != null) {
				SendExchange sendExchange = new SendExchange();
				BeanUtils.copyProperties(sendExchangeView, sendExchange);
				sendExchangeService.saveSendExchange(sendExchange);
			}

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	@Override
	public List<SendExchangeView> findSendExchangeByUid(long uid,
			int sendType, int start, int count) throws TException {
		List<SendExchangeView> list = null;
		try {
			List<SendExchange> sendExchangeList = sendExchangeService
					.findSendExchangeByUid(uid, sendType, start, count);
			if (sendExchangeList != null) {
				list = new ArrayList<SendExchangeView>();
				for (SendExchange sendExchange : sendExchangeList) {
					SendExchangeView sendExchangeView = new SendExchangeView();
					BeanUtils.copyProperties(sendExchange, sendExchangeView);
					list.add(sendExchangeView);
				}
			}

		} catch (Exception e) {
			throw new TException(e);
		}
		return list;
	}

	@Override
	public int findSendExchangeCountByUid(long uid, int sendType)
			throws TException {
		int count = 0;
		try {
			count = sendExchangeService.findSendExchangeCountByUid(uid,
					sendType);
		} catch (Exception e) {
			throw new TException(e);
		}
		return count;
	}

	@Override
	public SystemConfigView getSystemConfig(String configId) throws TException {
		try {
			SystemConfigView systemConfigView = null;
			SystemConfig systemConfig = systemConfigService
					.getSystemConfig(configId);
			if (systemConfig != null) {
				systemConfigView = new SystemConfigView();
				BeanUtils.copyProperties(systemConfig, systemConfigView);
			}
			return systemConfigView;
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateSystemConfig(SystemConfigView systemConfigView)
			throws TException {
		try {
			if (systemConfigView != null) {
				SystemConfig systemConfig = new SystemConfig();
				BeanUtils.copyProperties(systemConfigView, systemConfig);
				systemConfigService.updateSystemConfig(systemConfig);
			}
		} catch (Exception e) {
			throw new TException(e);
		}

	}


	@Override
	public List<AdvertisementView> findAdvertisementView()
			throws TException {
		List<AdvertisementView> list = null;
		try {
			List<Advertisement> adList = advertisementService
					.findAdvertisement();
			if (adList != null) {
				list = new ArrayList<AdvertisementView>();
				for (Advertisement ad : adList) {
					AdvertisementView adv = new AdvertisementView();
					BeanUtils.copyProperties(ad, adv);
					list.add(adv);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return list;
	}

	
	@Override
	public void addSystemConfig(SystemConfigView systemConfigView)
			throws TException {
		try {
			SystemConfig systemConfig = new SystemConfig();
			BeanUtils.copyProperties(systemConfigView, systemConfig);
			systemConfigService.addSystemConfig(systemConfig);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void deleteSystemConfig(String configId) throws TException {
		try {
			systemConfigService.deleteSystemConfig(configId);
		} catch (Exception e) {
			throw new TException(e);
		}

	}

	@Override
	public List<SystemConfigView> getSystemConfigList(
			Map<String, String> paramMap, int start, int count)
			throws TException {
		List<SystemConfigView> systemConfigViewList = null;
		try {
			List<SystemConfig> systemConfigList = systemConfigService
					.getSystemConfigList(paramMap, start, count);
			if (systemConfigList != null) {
				systemConfigViewList = new ArrayList<SystemConfigView>();
				for (SystemConfig systemConfig : systemConfigList) {
					SystemConfigView systemConfigView = new SystemConfigView();
					BeanUtils.copyProperties(systemConfig, systemConfigView);
					systemConfigViewList.add(systemConfigView);
				}
			}
			return systemConfigViewList;
		} catch (Exception e) {
			throw new TException(e);
		}
	}


	@Override
	public SystemConfigView getSystemConfigByVersion(String version)
			throws TException {
		SystemConfigView systemConfigView = null;
		try {
			SystemConfig systemConfig = systemConfigService
					.getSystemConfigByVersion(version);
			if (systemConfig != null) {
				systemConfigView = new SystemConfigView();
				BeanUtils.copyProperties(systemConfig, systemConfigView);
			}
			return systemConfigView;
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void saveRegister(RegisterView registerView) throws TException {
		Register register = new Register();
		try {
			BeanUtils.copyProperties(registerView, register);
			registerService.saveRegister(register);
		} catch (ServiceException e) {
			throw new TException(e);
		}

	}

	@Override
	public RegisterView getRegisterByUid(String uid) throws TException {
		RegisterView registerView = null;
		try {
			Register register = registerService.getRegisterByUid(uid);
			if (register != null) {
				registerView = new RegisterView();
				BeanUtils.copyProperties(register, registerView);
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return registerView;
	}

	@Override
	public void updateRegisterByUid(String uid, Map<String, String> paramMap)
			throws TException {
		try {
			registerService.updateRegisterByUid(uid, paramMap);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public RegisterListView getRegisterList(Map<String, String> paramMap,
			int start, int count) throws TException {
		try {
			ReturnList<Register> returnList = registerService.getRegisterList(
					paramMap, start, count);
			RegisterListView registerListView = new RegisterListView();
			registerListView.registerList = new ArrayList<RegisterView>();

			if (returnList != null && returnList.objects != null) {
				for (Register register : returnList.objects) {
					RegisterView registerView = new RegisterView();
					BeanUtils.copyProperties(register, registerView);
					registerListView.registerList.add(registerView);
				}
			}
			registerListView.count = returnList.count;
			return registerListView;
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateRegister(RegisterView registerView) throws TException {

		try {
			Register register = new Register();
			BeanUtils.copyProperties(registerView, register);
			registerService.updateRegister(register);
		} catch (Exception e) {
			throw new TException(e);
		}
	}


	@Override
	public List<MasterLevelView> findMasterLevelList() throws TException {
		List<MasterLevel> masterLevel = null;
		try {
			masterLevel = levelService.findMasterLevelList();
		} catch (ServiceException e) {
			throw new TException(e);
		}
		if (masterLevel != null){
			return (List<MasterLevelView>) CollectionUtils.collect(masterLevel, new Transformer<MasterLevel, MasterLevelView>() {
				@Override
				public MasterLevelView transform(MasterLevel masterLevel) {
		
					MasterLevelView masterLevelView = new MasterLevelView();
					BeanUtils.copyProperties(masterLevel, masterLevelView);
					return masterLevelView;
				}
			});
		}
		return null;
	}


	@Override
	public List<FanLevelView> findFanLevelList() throws TException {
		List<FanLevel> fanLevel = null;
		try {
			fanLevel = levelService.findFanLevelList();
		} catch (ServiceException e) {
			throw new TException(e);
		}
		if (fanLevel != null){
			return (List<FanLevelView>) CollectionUtils.collect(fanLevel, new Transformer<FanLevel, FanLevelView>() {
				@Override
				public FanLevelView transform(FanLevel fanLevel) {
					FanLevelView fanLevelView = new FanLevelView();
					BeanUtils.copyProperties(fanLevel, fanLevelView);
					return fanLevelView;
				}
			});
		}
		return null;
	}


	@Override
	public NoticeView findNoticeById(int id) throws TException {
		Notice notice = null;
		try {
			notice = noticeService.findById(id);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (notice != null) {
			NoticeView noticeView = new NoticeView();
			BeanUtils.copyProperties(notice, noticeView);
			return noticeView;
		}
		
		return null;
	}


	@Override
	public List<NoticeView> findEffecNoticeList() throws TException {
		
		List<Notice> noticeList = null;
		try {
			noticeList = noticeService.findEffecNoticeList();
		} catch (ServiceException e) {
			throw new TException(e);
		}
		if (noticeList != null){
			return (List<NoticeView>) CollectionUtils.collect(noticeList, new Transformer<Notice, NoticeView>() {
				@Override
				public NoticeView transform(Notice notice) {
					NoticeView noticeView = new NoticeView();
					BeanUtils.copyProperties(notice, noticeView);
					return noticeView;
				}
			});
		}
		return null;
	
	}


	@Override
	public NoticeListView findNoticeList(int start, int count)
			throws TException {

		try {
			ReturnList<Notice> returnList = noticeService.findNoticeList(start, count);
			NoticeListView noticeListView = new NoticeListView();
			noticeListView.noticeList = new ArrayList<NoticeView>();

			if (returnList != null && returnList.objects != null) {
				for (Notice notice : returnList.objects) {
					NoticeView noticeView = new NoticeView();
					BeanUtils.copyProperties(notice, noticeView);
					noticeListView.noticeList.add(noticeView);
				}
			}

			noticeListView.count = returnList.count;
			return noticeListView;

		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


	@Override
	public void updateNoticeById(int id, Map<String, String> paramMap)
			throws TException {

		try {
			noticeService.update(paramMap, id);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


	@Override
	public void deleteNoticeById(int id) throws TException {

		try {
			noticeService.delete(id);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


	@Override
	public void addNotice(NoticeView noticeView) throws TException {

		try {
			Notice notice = new Notice();
			BeanUtils.copyProperties(noticeView, notice);
			noticeService.insert(notice);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


	 @Override
     public List<HeaderPortraitView> findHeaderPortraitList(int pageSize,
                     int pageCount) throws TException {
             List<HeaderPortraitView> list = null;
             try {
                     List<HeaderPortrait> headerPortraitList = headerPortraitService
                                     .findHeaderPortraitList(pageSize, pageCount);
                     if (headerPortraitList != null) {
                             list = new ArrayList<HeaderPortraitView>();
                             for (HeaderPortrait headerPortrait : headerPortraitList) {
                                     HeaderPortraitView headerPortraitView = new HeaderPortraitView();
                                     BeanUtils
                                                     .copyProperties(headerPortrait, headerPortraitView);
                                     list.add(headerPortraitView);
                             }
                     }
             } catch (Exception e) {
                     throw new TException(e);
             }
             return list;
     }

     @Override
     public void deleteHeaderPortrait(long id) throws TException {
             try {
                     headerPortraitService.deleteHeaderPortrait(id);
             } catch (Exception e) {
                     throw new TException(e);
             }

     }

     @Override
     public void deleteHeaderPortraitList(List<Long> ids) throws TException {
             try {
                     headerPortraitService.deleteHeaderPortraitList(ids);
             } catch (Exception e) {
                     throw new TException(e);
             }

     }
     @Override
     public void saveHeaderPortraitList(HeaderPortraitView headerPortraitView)
                     throws TException {
             try {
                     HeaderPortrait headerPortrait = null;
                     if (headerPortraitView != null) {
                             headerPortrait = new HeaderPortrait();
                             BeanUtils.copyProperties(headerPortraitView, headerPortrait);
                     }
                     headerPortraitService.saveHeaderPortraitList(headerPortrait);
             } catch (Exception e) {
                     throw new TException(e);
             }

     }
     
     @Override
     public AdminHeaderPortraitListView findAdminHeaderPortraitListView(
                     int pageSize, int pageCount) throws TException {
             List<HeaderPortraitView> list = null;
             AdminHeaderPortraitListView adminHeaderPortraitListView = null;
             try {
                     List<HeaderPortrait> headerPortraitList = headerPortraitService
                                     .findHeaderPortraitList(pageSize, pageCount);
                     if (headerPortraitList != null) {
                             list = new ArrayList<HeaderPortraitView>();
                             for (HeaderPortrait headerPortrait : headerPortraitList) {
                                     HeaderPortraitView headerPortraitView = new HeaderPortraitView();
                                     BeanUtils
                                                     .copyProperties(headerPortrait, headerPortraitView);
                                     list.add(headerPortraitView);
                             }
                             adminHeaderPortraitListView = new AdminHeaderPortraitListView();
                             adminHeaderPortraitListView.count = headerPortraitService
                                             .findHeaderPortraitCount();
                             adminHeaderPortraitListView.headerPortraitViewList = list;
                     }
             } catch (Exception e) {
                     throw new TException(e);
             }
             return adminHeaderPortraitListView;
     }


	@Override
	public UserCDNListView findUserCDNList(int start, int count)
			throws TException {
		
		try {
			ReturnList<UserCDN> returnList = userCDNService.findUserCDNList(start, count);
			UserCDNListView cdnListView = new UserCDNListView();
			cdnListView.cdnList = new ArrayList<UserCDNView>();

			if (returnList != null && returnList.objects != null) {
				for (UserCDN userCDN : returnList.objects) {
					UserCDNView userCDNView = new UserCDNView();
					BeanUtils.copyProperties(userCDN, userCDNView);
					cdnListView.cdnList.add(userCDNView);
				}
			}

			cdnListView.count = returnList.count;
			return cdnListView;

		} catch (ServiceException e) {
			throw new TException(e);
		}
	
	}


	@Override
	public void updateUserCDNById(UserCDNView userCDNView) throws TException {

		try {
			UserCDN userCDN = new UserCDN();
			BeanUtils.copyProperties(userCDNView, userCDN);
			userCDNService.update(userCDN);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


	@Override
	public void deleteUserCDN(long uid) throws TException {

		try {
			userCDNService.delete(uid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


	@Override
	public void saveUserCDN(UserCDNView userCDNView) throws TException {
		try {
			UserCDN userCDN = new UserCDN();
			BeanUtils.copyProperties(userCDNView, userCDN);
			userCDNService.save(userCDN);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}


	@Override
	public UserCDNView findUserCDNById(long uid) throws TException {

		try {
			
			UserCDN userCDN = userCDNService.findUserCDNById(uid);
			
			if (userCDN != null){
				UserCDNView userCDNView = new UserCDNView();
				BeanUtils.copyProperties(userCDN, userCDNView);
				return userCDNView;
			}else{
				return null;
			}
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}


	@Override
	public List<RobotSendGiftRatioView> findRobotSendGiftRatioList()
			throws TException {
		
		List<RobotSendGiftRatioView> robotSendGiftRatioViewList = null;
		try {
			List<RobotSendGiftRatio> robotSendGiftRatioList = robotService.findSendGiftRatioList();
			if (robotSendGiftRatioList != null) {
				robotSendGiftRatioViewList = new ArrayList<RobotSendGiftRatioView>();
				for (RobotSendGiftRatio robotSendGiftRatio : robotSendGiftRatioList) {
					if (robotSendGiftRatio != null) {
						RobotSendGiftRatioView robotSendGiftRatioView = new RobotSendGiftRatioView();
						BeanUtils.copyProperties(robotSendGiftRatio, robotSendGiftRatioView);
						robotSendGiftRatioViewList.add(robotSendGiftRatioView);
					}
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return robotSendGiftRatioViewList;
	
	}


	@Override
	public void saveRobotSendGiftRatio(
			RobotSendGiftRatioView robotSendGiftRatioView) throws TException {

		try {
			if (robotSendGiftRatioView != null) {
				RobotSendGiftRatio robotSendGiftRatio = new RobotSendGiftRatio();
				BeanUtils.copyProperties(robotSendGiftRatioView, robotSendGiftRatio);
				robotService.saveSendGiftRatio(robotSendGiftRatio);
			}
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}



}