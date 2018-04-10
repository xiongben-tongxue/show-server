package one.show.manage.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.AdvertisementMapper;
import one.show.manage.domain.Advertisement;
import one.show.manage.service.AdvertisementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("advertisementService")
public class AdvertisementServiceImpl implements AdvertisementService{
	@Autowired
	AdvertisementMapper advertismentMapper;
	@Override
	public List<Advertisement> findAdvertisement()
			throws ServiceException {
		List<Advertisement> list = null;
		try {
			list=advertismentMapper.findAdvertisement();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return list;
	}

}
