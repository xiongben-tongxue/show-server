package one.show.manage.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.SendExchangeMapper;
import one.show.manage.domain.SendExchange;
import one.show.manage.service.SendExchangeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("sendExchangeService")
public class SendExchangeServiceImpl implements SendExchangeService {
	@Autowired
	SendExchangeMapper sendExchangeMapper;
	@Override
	public void saveSendExchange(SendExchange sendExchange)
			throws ServiceException {
		try {
			sendExchangeMapper.saveSendExchange(sendExchange);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<SendExchange> findSendExchangeByUid(Long uid, int sendType,int start,
			int count) throws ServiceException {
		List<SendExchange> list = null;
		try {
			list=sendExchangeMapper.findSendExchangeByUid(uid, sendType,start, count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return list;
	}

	@Override
	public Integer findSendExchangeCountByUid(Long uid,int sendType)
			throws ServiceException {
		int count =0;
		try {
			count = sendExchangeMapper.findSendExchangeCountByUid(uid,sendType);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return count;
	}

}
