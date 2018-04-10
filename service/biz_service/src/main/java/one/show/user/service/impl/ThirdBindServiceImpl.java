package one.show.user.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.dao.ThirdBindMapper;
import one.show.user.domain.ThirdBind;
import one.show.user.service.ThirdBindService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.id.thrift.iface.IDServiceProxy;
import one.show.id.thrift.view.IDView;

@Component("thirdBindService")
public class ThirdBindServiceImpl implements ThirdBindService {
	@Autowired
	private ThirdBindMapper thirdBindMapper;
	
	@Autowired
	private IDServiceProxy.Iface idServiceClientProxy;

	@Override
	public List<ThirdBind> findThirdBindByUid(long uid) throws ServiceException{
		List<ThirdBind> list=null;
		try {
			list= thirdBindMapper.findThirdBindByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return list;
	}

	@Override
	public void saveThirdBind(ThirdBind thirdBind) throws ServiceException{
		try {
			thirdBindMapper.save(thirdBind);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void updateThirdBind(long uid,String type,Map<String,String> map) throws ServiceException{
		try {
			thirdBindMapper.updateThirdBindByUidAndType(uid, type, map);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void deleteThirdBind(long uid,String type) throws ServiceException{

		try {
			thirdBindMapper.deleteThirdBind(uid,type);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public ThirdBind findThirdBindByUidAndType(long uid, String type) throws ServiceException{
		ThirdBind thirdBind = null;
		try {
			List<ThirdBind> thirdBinds = thirdBindMapper.findThirdBindByUidAndType(uid, type);
			if(thirdBinds!=null && thirdBinds.size()>0){
				thirdBind = thirdBinds.get(0);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return thirdBind;
		
	}
	
}
