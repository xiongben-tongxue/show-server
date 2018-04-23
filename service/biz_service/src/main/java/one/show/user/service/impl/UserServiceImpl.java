package one.show.user.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.Adapter;
import one.show.common.Constant.POPULAR_NO_STATUS;
import one.show.common.Loader;
import one.show.common.ParallelHandler;
import one.show.common.RandomUtils;
import one.show.common.TimeUtil;
import one.show.common.exception.ServiceException;
import one.show.common.id.PopularID;
import one.show.common.im.IMUtils;
import one.show.common.local.HeaderParams;
import one.show.common.local.XThreadLocal;
import one.show.id.ID;
import one.show.manage.service.SensitiveWordService;
import one.show.search.service.SearchService;
import one.show.search.thrift.view.UserSearchView;
import one.show.user.dao.UserMapper;
import one.show.user.domain.Device;
import one.show.user.domain.ThirdData;
import one.show.user.domain.User;
import one.show.user.domain.UserDevice;
import one.show.user.domain.UserPopular;
import one.show.user.service.DeviceService;
import one.show.user.service.ThirdDataService;
import one.show.user.service.UserPopularService;
import one.show.user.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("userService")
public class UserServiceImpl implements UserService{
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ThirdDataService thirdDataService;
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Autowired
	private UserPopularService userPopularService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private SearchService searchService;
	
	@Override
	public void updateUserById(long uid, Map<String, String> updateContent) throws ServiceException{
		try {
			 userMapper.updateUserById(uid, updateContent);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<User> findUserListByIds(List<Long> ids) {
		return new ParallelHandler<Long, User>() {

			@Override
			public User handle(Long item) {
				return userMapper.findById(item);
			}
		}.execute(ids);
	}


	@Override
	public User findUserById(long id){
		
		return userMapper.findById(id);
	}


	@Override
	public void instertUser(User user) throws ServiceException{
		try {
			user.setNotifyConfig(63);
			userMapper.insertUser(user);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<User> findAllUsersList(Integer start, Integer count)throws ServiceException {
		try {
			return userMapper.findAllUsersList(start, count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	@Override
	public void deletetUser(Long id) throws ServiceException {
		try {
			userMapper.deleteUser(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public boolean isAllow(String nickName) {
		User user = findUserByNickName(nickName);
		return user==null;
	}

	@Override
	public User findUserByNickName(String nickName) {
		return userMapper.findUserByNickName(nickName);
	}

	@Override
	public User findUserByPopularNo(long pid) {
		return userMapper.findUserByPopularNo(pid);
	}


	
	private void getUniqNickName(User user) throws ServiceException {
		boolean flag = isAllow(user.getNickname());
		if (!flag) {
			user.setNickname(user.getNickname()
					+ RandomUtils.generateNumber(4));
		}
	}
	
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class)
	public void registerUser(User user, ThirdData thirdData) throws ServiceException{
		long uid = ID.nextId();
		//保存登陆表数据
		thirdData.setUid(uid);
		thirdDataService.save(thirdData);
		
		user.setId(uid);
		user.setPopularNo(PopularID.getInstance().nextPid());
	
		
		//昵称为空或者存在敏感词
		String filterWord = null;
		try{
			filterWord = sensitiveWordService.filter(user.getNickname(), 1);
		}catch(ServiceException e){
			log.error(e.getMessage(), e);
		}
		
		if (StringUtils.isBlank(user.getNickname()) || (filterWord != null && !user.getNickname().equals(filterWord))) {
			user.setNickname("ShowCoin新人" + RandomUtils.generateNumber(8));
		}else{
			//生成唯一昵称
			getUniqNickName(user);
		}
		if (StringUtils.isBlank(user.getProfileImg())) {
			user.setProfileImg(Loader.getInstance().getProps("default.profileimg"));
		}
		
		String ryToken = IMUtils.getInstants().getToken(uid+"", user.getNickname(), Adapter.getAvatar(user.getProfileImg()));
		user.setRyToken(ryToken);
		
		insertUser(user);
		
		
		if (user.getDeviceUuid() != null && !"".equals(user.getDeviceUuid())) {
			Device dv = deviceService.findDeviceByDid(user.getDeviceUuid());
			if(dv==null){
				//保存设备数据
				dv = new Device();
				dv.setDeviceUuid(user.getDeviceUuid());
				deviceService.save(dv);
			}
			
			//保存用户和设备对应关系
			UserDevice udv = new UserDevice();
			udv.setUid(user.getId());
			udv.setDeviceUuid(user.getDeviceUuid());
			udv.setCreateTime(TimeUtil.getCurrentTimestamp());
			deviceService.insertUserDevice(udv);
		}
		
		try {
			UserSearchView userSearch = new UserSearchView();
			userSearch.setUid(user.getId());
			userSearch.setNickName(user.getNickname());
			userSearch.setPopularNo(user.getPopularNo());
			searchService.insertUser(userSearch);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class)
	private void insertUser(User user) throws ServiceException {
		if(user!=null){
			int now = TimeUtil.getCurrentTimestamp();
			try {
				//用户所持有靓号列表
				UserPopular userPopular = new UserPopular();
				userPopular.setUid(user.getId());
				userPopular.setPopularNo(user.getPopularNo());
				userPopular.setStatus(POPULAR_NO_STATUS.USEING.ordinal());
				userPopular.setCreateTime(now);
				userPopularService.save(userPopular);
				
				instertUser(user);
				
				
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				throw new ServiceException(e.getMessage());
			}
			
		}
		
	}

}
