/**
 * 
 */
package one.show.user.thrift.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import one.show.user.domain.Gps;
import one.show.user.service.GpsService;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.user.thrift.iface.GpsServiceProxy.Iface;
import one.show.user.thrift.view.GpsView;


/**
 * @author zhangwei 2015年8月5日 下午9:48:19
 *
 */

@Component("gpsServiceProxyImpl")
public class GpsServiceProxyImpl implements Iface{
	
	private static final Logger log = LoggerFactory.getLogger(GpsServiceProxyImpl.class);
	@Autowired
	GpsService gpsService;
	@Override
	public GpsView findGpsByUid(long uid) throws TException {
		GpsView gpsView = null;
		try {
			Gps gps =gpsService.findGpsByUid(uid);
			if(gps!=null){
				gpsView = new GpsView();
				BeanUtils.copyProperties(gps, gpsView);
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return gpsView;
	}
	@Override
	public void saveGps(GpsView gpsView) throws TException {
		try {
			if(gpsView!=null){
				Gps gps = new Gps();
				BeanUtils.copyProperties(gpsView, gps);
				gpsService.saveGps(gps);
			}
			
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}
	@Override
	public void updateGps(long uid, Map<String, String> params)
			throws TException {
		try {
			gpsService.updateGps(uid, params);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}
	@Override
	public List<GpsView> findNearGpsList(Map<String, String> params,int start,int count)
			throws TException {
		List<GpsView> gpsViewList = null;
		try {
			List<Gps> gpsList = gpsService.findNearGpsList(params,start,count);
			if(gpsList!=null){
				gpsViewList = new ArrayList<GpsView>();
				for(Gps gps:gpsList){
					GpsView gpsView = new GpsView();
					BeanUtils.copyProperties(gps, gpsView);
					gpsViewList.add(gpsView);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return gpsViewList;
	}
	


}


