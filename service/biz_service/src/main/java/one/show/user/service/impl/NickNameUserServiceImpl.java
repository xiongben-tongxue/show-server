package one.show.user.service.impl;

import one.show.common.exception.ServiceException;
import one.show.user.dao.NickNameMapper;
import one.show.user.domain.NickNameUser;
import one.show.user.service.NickNameUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("nickNameUserService")
public class NickNameUserServiceImpl implements NickNameUserService {
	@Autowired
	NickNameMapper nickNameUserMapper;
	@Override
	public boolean isAllow(String nickName) throws ServiceException {
		boolean flag = false;
		try {
			NickNameUser nickNameUser = nickNameUserMapper.findNickNameUserByNickName(nickName);
			if(nickNameUser==null){
				flag = true;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return flag;
	}

	@Override
	public void saveNickNameUser(NickNameUser nickNameUser)
			throws ServiceException {
		try {
			nickNameUserMapper.saveNickNameUser(nickNameUser);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public long findUidByNickName(String nickName) throws ServiceException {
		long uid=0L;
		try {
			 NickNameUser nickNameUser = nickNameUserMapper.findNickNameUserByNickName(nickName);
			 if(nickNameUser!=null){
				 uid = nickNameUser.getUid();
			 }
		} catch (Exception e) {
			throw new ServiceException(e);
		}
				
		return uid;
	}

	@Override
	public void deleteNickName(String nickName) throws ServiceException {
		try {
			nickNameUserMapper.deleteNickName(nickName);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

}
