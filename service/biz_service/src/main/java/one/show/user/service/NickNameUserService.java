package one.show.user.service;

import one.show.common.exception.ServiceException;
import one.show.user.domain.NickNameUser;

public interface NickNameUserService {
	public boolean isAllow(String nickName)throws ServiceException;
	
	public void saveNickNameUser(NickNameUser nickNameUser) throws ServiceException;
	
	public long findUidByNickName(String nickName) throws ServiceException;
	
	public void deleteNickName(String nickName) throws ServiceException;
}
