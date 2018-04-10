package one.show.manage.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.HeaderPortraitMapper;
import one.show.manage.domain.HeaderPortrait;
import one.show.manage.service.HeaderPortraitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("headerPortraitService")
public class HeaderPortraitServiceImpl implements HeaderPortraitService {
	@Autowired
	HeaderPortraitMapper  headerPortraitMapper;
	@Override
	public List<HeaderPortrait> findHeaderPortraitList(int pageSize,
			int pageCount) throws ServiceException {
		List<HeaderPortrait> list = null;
		try {
			list = headerPortraitMapper.findHeaderPortraitList(pageSize, pageCount);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return list;
	}

	@Override
	public void deleteHeaderPortrait(long id) throws ServiceException {
		try {
			headerPortraitMapper.deleteHeaderPortrait(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteHeaderPortraitList(List<Long> ids)
			throws ServiceException {
		try {
			headerPortraitMapper.deleteHeaderPortraitList(ids);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveHeaderPortraitList(HeaderPortrait headerPortrait)
			throws ServiceException {
		try {
			headerPortraitMapper.saveHeaderPortraitList(headerPortrait);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public Integer findHeaderPortraitCount() throws ServiceException{
		try {
			return headerPortraitMapper.findHeaderPortraitCount();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteHeaderPortraitByUid(String uid) throws ServiceException {
		try {
			headerPortraitMapper.deleteHeaderPortraitByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public HeaderPortrait findHeaderPortrait(String uid)
			throws ServiceException {
		HeaderPortrait headerPortrait= null;
		try {
			headerPortrait=headerPortraitMapper.findHeaderPortrait(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return headerPortrait;
	}

	@Override
	public void updateHeaderPortraitByUid(String uid, Map<String, String> params)
			throws ServiceException {
		try {
			headerPortraitMapper.updateHeaderPortraitByUid(uid, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

}
