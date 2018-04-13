/**
 * 
 */
package one.show.user.service.impl;

import one.show.common.exception.ServiceException;
import one.show.user.dao.ContactMapper;
import one.show.user.domain.Contact;
import one.show.user.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangwei 2015年8月4日 下午5:51:06
 *
 */

@Component("contactService")
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactMapper phoneMapper;
	
	@Override
	public List<Contact> findListByUid(String uid) {

		return phoneMapper.findListByUid(uid);
	}

	@Override
	public void batchSave(List<Contact> contactList) throws ServiceException{
		try {
			phoneMapper.batchSave(contactList.get(0).getUid() , contactList);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


