
package one.show.manage.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.NoticeMapper;
import one.show.manage.domain.Notice;
import one.show.manage.domain.ReturnList;
import one.show.manage.service.NoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年6月2日 下午9:19:12
 *
 * 
 */

@Component
public class NoticeServiceImpl implements NoticeService {

	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Override
	public Notice findById(Integer id) throws ServiceException {
		try {
			return noticeMapper.findById(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void insert(Notice notice) throws ServiceException {

		try {
			noticeMapper.insert(notice);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(Integer id) throws ServiceException {

		try {
			 noticeMapper.delete(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(Map<String, String> updateContent, Integer id)
			throws ServiceException {
		try {
			 noticeMapper.update(updateContent, id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<Notice> findEffecNoticeList()
			throws ServiceException {
		try {
			return noticeMapper.findEffecNoticeList();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ReturnList<Notice> findNoticeList(Integer start, Integer count) throws ServiceException {
		ReturnList<Notice> list = new ReturnList<Notice>();
		try {
			List<Notice> noticeList = noticeMapper.findNoticeList(start, count);
			list.objects = noticeList;
			list.count = noticeMapper.findNoticeListCount();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		return list;
	}

	@Override
	public Integer findNoticeListCount() throws ServiceException {
		try {
			return noticeMapper.findNoticeListCount();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


