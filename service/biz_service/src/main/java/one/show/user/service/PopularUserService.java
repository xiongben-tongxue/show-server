package one.show.user.service;

import one.show.common.exception.ServiceException;
import one.show.user.domain.PopularUser;

public interface PopularUserService {
	
	public PopularUser findPopularUserByPopularNo(long popularNo)throws ServiceException;
	public void  savePopularUser(PopularUser popularUser)throws ServiceException;
	
	public void  deletePopularUser(long popularNo)throws ServiceException;
}
