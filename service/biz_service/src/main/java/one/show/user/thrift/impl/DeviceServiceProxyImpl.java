/**
 * 
 */
package one.show.user.thrift.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.dao.DeviceForbiddenMapper;
import one.show.user.dao.DeviceMapper;
import one.show.user.domain.Device;
import one.show.user.domain.DeviceForbidden;
import one.show.user.domain.UserDevice;
import one.show.user.service.DeviceForbiddenService;
import one.show.user.service.DeviceService;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.common.exception.ServerException;
import one.show.user.thrift.iface.DeviceServiceProxy.Iface;
import one.show.user.thrift.view.DeviceForbiddenView;
import one.show.user.thrift.view.DeviceView;
import one.show.user.thrift.view.UserDeviceView;


/**
 * @author zhangwei 2015年8月5日 下午9:48:19
 *
 */

@Component("deviceServiceProxyImpl")
public class DeviceServiceProxyImpl implements Iface{
	
	private static final Logger log = LoggerFactory.getLogger(DeviceServiceProxyImpl.class);
	@Autowired
	DeviceMapper deviceMapper;
	@Autowired
	DeviceForbiddenMapper deviceForbiddenMapper;
	@Autowired
	DeviceForbiddenService deviceForbiddenService;
	@Autowired
	DeviceService deviceService;
	@Override
	public DeviceView findDeviceByDid(String did) throws TException {
		DeviceView deviceView = null;
		try {
			Device device = deviceMapper.findDeviceByDid(did);
			if(device!=null){
				deviceView = new DeviceView();
				BeanUtils.copyProperties(device, deviceView);
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return deviceView;
	}

	@Override
	public boolean findForbiddenByDid(String did) throws TException {
		List<DeviceForbidden> dfv = null;
		try {
			dfv = deviceForbiddenMapper.findForbiddenByDid(did);
			if (dfv == null || dfv.size() == 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateDevice(String did, Map<String, String> paramsMap)
			throws TException {
		try {
			deviceMapper.updateDevice(did, paramsMap);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public List<DeviceForbiddenView> findDeviceForbiddenListByDid(String did)
			throws TException {
		List<DeviceForbiddenView> viewList = null;
		try {
			List<DeviceForbidden> list = deviceForbiddenMapper.findDeviceForbiddenListByDid(did);
			if (list!=null&&list.size()>0) {
				viewList = new ArrayList<DeviceForbiddenView>();
				for (DeviceForbidden deviceForbidden : list) {
					try {
						DeviceForbiddenView deviceForbiddenView = new DeviceForbiddenView();
						BeanUtils.copyProperties(deviceForbidden, deviceForbiddenView);
						viewList.add(deviceForbiddenView);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
			}
			
			return viewList;
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void saveForbidden(DeviceForbiddenView deviceForbiddenView) throws ServerException, TException {
		try {
			if(deviceForbiddenView!=null){
				DeviceForbidden deviceForbidden = new DeviceForbidden();
				BeanUtils.copyProperties(deviceForbiddenView, deviceForbidden);
				deviceForbiddenService.saveForbidden(deviceForbidden);
			}
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}

	@Override
	public void deleteForbidden(String did) throws TException {
		try {
			deviceForbiddenService.deleteForbidden(did);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void saveDevice(DeviceView deviceView) throws ServerException, TException {
		Device device = new Device();
		BeanUtils.copyProperties(deviceView, device);
		try {
			deviceService.save(device);
		} catch (ServiceException e) {
			log.error(e.getMessage(),e);
			throw new ServerException(e.getMessage());
		}
	}

	@Override
	public UserDeviceView findUserDevice(long uid,String deviceUuid) throws TException {

		 try {
			UserDeviceView udv = new UserDeviceView();
			 UserDevice ud = deviceService.findUserDevice(uid, deviceUuid);
			 if(ud!=null){
				 BeanUtils.copyProperties(ud, udv);
				 return udv;
			 }else{
				 return null;
			 }
			 
		} catch (Exception e) {
			log.error("findUserDevice error",e);
			throw new ServerException(e.getMessage());
		}
	}

	@Override
	public List<UserDeviceView> findListByUid(
			long uid) throws TException {
		List<UserDeviceView> views = new ArrayList<UserDeviceView>();
		try {
			List<UserDevice> list = deviceService.findListByUid(uid);
			if(list!=null){
				for (UserDevice ud:list) {
					UserDeviceView udv = new UserDeviceView();
					BeanUtils.copyProperties(ud, udv);
					views.add(udv);
				}
			}
		}catch (Exception e) {
			log.error("findListByUid error",e);
			throw new ServerException(e.getMessage());
		} 
		return views;
	}

	@Override
	public void saveUserDevice(UserDeviceView device) throws TException {
		try {
			if(device!=null){
				UserDevice ud = new UserDevice();
				BeanUtils.copyProperties(device, ud);
				deviceService.insertUserDevice(ud);
			}
		}catch (Exception e) {
			log.error("saveUserDevice error",e);
			throw new ServerException(e.getMessage());
		} 
	}

	@Override
	public void deleteDevice(String did) throws TException {

		try {
			deviceService.delete(did);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void deleteUserDeviceByUid(long uid) throws TException {

		try {
			deviceService.deleteUserDeviceByUid(uid);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}

}