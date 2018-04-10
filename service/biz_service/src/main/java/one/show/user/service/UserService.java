package one.show.user.service;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.domain.User;

public interface UserService{
	
	public User findUserById(long id);
	
	
	public void instertUser(User user) throws ServiceException;
	
	public void deletetUser(Long id) throws ServiceException;
	
	/**
	 * 更新用户字段
	 * @param uid
	 * @param updateContent
	 * 用例 :
	 * userService.updateUserById(11, new KV(ImmutableMap.of(
		"city", "beijing",
		"birth","19910103"
		)));
	 */
	void  updateUserById(long uid, Map<String, String> updateContent) throws ServiceException;

	public List<User> findUserListByIds(List<Long> ids);
	
	public List<User> findAllUsers0(Integer tbid) throws ServiceException;
	
	public List<User> findAllUsers1(Integer tbid) throws ServiceException;
}
