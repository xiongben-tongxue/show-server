package one.show.user.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.ParallelHandler;
import one.show.common.exception.ServiceException;
import one.show.user.dao.UserMapper;
import one.show.user.domain.User;
import one.show.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.id.thrift.iface.IDServiceProxy;
import one.show.id.thrift.view.IDView;

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
	public List<User> findAllUsers0(Integer tbid)throws ServiceException {
		try {
			return userMapper.findAllUsers0(tbid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<User> findAllUsers1(Integer tbid)throws ServiceException {
		try {
			return userMapper.findAllUsers1(tbid);
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

}
