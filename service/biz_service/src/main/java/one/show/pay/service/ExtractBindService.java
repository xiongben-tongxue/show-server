/**
 * 
 */
package one.show.pay.service;

import one.show.common.exception.ServiceException;
import one.show.pay.domain.ExtractBind;

/**
 * @author Haliaeetus leucocephalus 2015年11月17日 下午5:00:35
 *
 */
public interface ExtractBindService {

	public void saveExtractBind(ExtractBind extractBind) throws ServiceException;
	
	public ExtractBind getExtractBindByUid(long uid) throws ServiceException;


	public void updateExtractBindByUid(long uid, String alipayAccount, String alipayName) throws ServiceException;
}


