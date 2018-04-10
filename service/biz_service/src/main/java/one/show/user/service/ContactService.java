/**
 * 
 */
package one.show.user.service;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.user.domain.Contact;

/**
 * @author Haliaeetus leucocephalus 2018年1月4日 下午5:48:42
 *
 */
public interface ContactService {
	
	public List<Contact> findListByUid(String uid);
	
	public void batchSave(List<Contact> contactList) throws ServiceException;
}


