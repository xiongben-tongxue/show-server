package one.show.user.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.ParallelHandler;
import one.show.common.exception.ServiceException;
import one.show.id.thrift.iface.IDServiceProxy;
import one.show.user.dao.UserMapper;
import one.show.user.domain.User;
import one.show.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private IDServiceProxy.Iface idServiceClientProxy;

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

}
