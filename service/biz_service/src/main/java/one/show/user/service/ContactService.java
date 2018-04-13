/**
 * 
 */
package one.show.user.service;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.user.domain.Contact;

/**
 * @author zhangwei 2015年8月4日 下午5:48:42
 *
 */
public interface ContactService {
	
	public List<Contact> findListByUid(String uid);
	
	public void batchSave(List<Contact> contactList) throws ServiceException;
}


