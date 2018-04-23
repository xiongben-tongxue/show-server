package one.show.user.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.dao.DeviceMapper;
import one.show.user.dao.UserDeviceMapper;
import one.show.user.domain.Device;
import one.show.user.domain.UserDevice;
import one.show.user.service.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component("deviceService")
public class DeviceServiceImpl implements DeviceService{
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private UserDeviceMapper userDeviceMapper;
	
	@Override
	public Device findDeviceByDid(String deviceUuid) throws ServiceException {
		Device device = null;
		try {
			device = deviceMapper.findDeviceByDid(deviceUuid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return device;
	}
	@Override
	public void updateDevice(String deviceUuid, Map<String, String> paramsMap)
			throws ServiceException {
		try {
			deviceMapper.updateDevice(deviceUuid, paramsMap);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class)
	public int save(Device device) throws ServiceException {
		try {
			return deviceMapper.insert(device);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public UserDevice findUserDevice(long uid, String deviceUuid) throws ServiceException {
		try {
			return userDeviceMapper.findById(uid, deviceUuid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public List<UserDevice> findListByUid(long uid) throws ServiceException {
		try {
			return userDeviceMapper.findListByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class)
	public void insertUserDevice(UserDevice ud) throws ServiceException {
		try {
			userDeviceMapper.insertUserDevice(ud);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public void delete(String did) throws ServiceException {

		try {
			deviceMapper.delete(did);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	@Override
	public void deleteUserDeviceByUid(long uid) throws ServiceException {

		try {
			userDeviceMapper.deleteUserDeviceByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
