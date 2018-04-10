/**
 * 
 */
package one.show.stat.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.stat.dao.SummaryStatMapper;
import one.show.stat.domain.SummaryStat;
import one.show.stat.service.SummaryStatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus 2015年10月26日 下午4:41:02
 *
 */
@Component
public class SummaryStatServiceImpl implements SummaryStatService {

	@Autowired
	private SummaryStatMapper summaryStatMapper;
	/* (non-Javadoc)
	 * @see one.show.stat.service.SummaryStatService#findSummaryList()
	 */
	@Override
	public List<SummaryStat> findSummaryList(Integer time) throws ServiceException{
		try {
			return summaryStatMapper.findSummaryList(time);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	@Override
	public SummaryStat findSummary(Integer time) throws ServiceException{
		try {
			return summaryStatMapper.find(time);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	@Override
	public void save(SummaryStat summaryStat) throws ServiceException {

		try {
			summaryStatMapper.save(summaryStat);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public void update(SummaryStat summaryStat) throws ServiceException {

		try {
			summaryStatMapper.update(summaryStat);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public void updateLiveMax(Integer num, Integer time) throws ServiceException {

		try {
			summaryStatMapper.updateLiveMax(num, time);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


