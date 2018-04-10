package one.show.user.service;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.domain.ThirdBind;

public interface ThirdBindService {
	
	
	public List<ThirdBind> findThirdBindByUid(long uid) throws ServiceException;
	
	public void saveThirdBind(ThirdBind thirdBind) throws ServiceException;
	
	public void updateThirdBind(long uid,String type,Map<String,String> map) throws ServiceException;
	
	public void deleteThirdBind(long uid,String type) throws ServiceException;
	
	public ThirdBind findThirdBindByUidAndType(long uid,String type) throws ServiceException;
}
