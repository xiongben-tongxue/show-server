
package one.show.pay.service.impl;

import one.show.common.exception.ServiceException;
import one.show.pay.dao.ExtractBindMapper;
import one.show.pay.domain.ExtractBind;
import one.show.pay.domain.ExtractRmb;
import one.show.pay.service.ExtractBindService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年7月25日 下午3:16:02
 *
 * 
 */
@Component
public class ExtractBindServiceImpl implements ExtractBindService {
	
	@Autowired
	ExtractBindMapper extractBindMapper;

	@Override
	public void saveExtractBind(ExtractBind extractBind) throws ServiceException {
		try {
			extractBindMapper.save(extractBind);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public ExtractBind getExtractBindByUid(long uid)
			throws ServiceException {
		try {
			return extractBindMapper.getByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateExtractBindByUid(long uid, String alipayAccount, String alipayName)
			throws ServiceException {

		try {
			extractBindMapper.updateAlipayByUid(uid, alipayAccount, alipayName);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


