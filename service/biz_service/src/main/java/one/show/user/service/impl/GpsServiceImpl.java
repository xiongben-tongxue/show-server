package one.show.user.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.dao.GpsMapper;
import one.show.user.domain.Gps;
import one.show.user.service.GpsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("gpsService")
public class GpsServiceImpl implements GpsService{
	
	@Autowired
	GpsMapper gpsMapper;
	@Override
	public Gps findGpsByUid(long uid) throws ServiceException {
		Gps gps=null;
		try {
			gps = gpsMapper.findGpsByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return gps;
	}
	@Override
	public void saveGps(Gps gps) throws ServiceException {
		try {
			gpsMapper.saveGps(gps);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	@Override
	public void updateGps(long uid, Map<String, String> params)
			throws ServiceException {
		try {
			gpsMapper.updateGps(uid, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	@Override
	public List<Gps> findNearGpsList(Map<String, String> params,int start,int count)
			throws ServiceException {
		List<Gps> uidList = null;
		try {
			uidList = gpsMapper.findNearGpsList(params,start,count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return uidList;
	}
}
