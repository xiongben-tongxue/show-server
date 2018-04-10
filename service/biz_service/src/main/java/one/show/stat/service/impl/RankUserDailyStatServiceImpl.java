
package one.show.stat.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.stat.dao.RankUserDailyMapper;
import one.show.stat.domain.MonitorStat;
import one.show.stat.domain.RankUserDaily;
import one.show.stat.domain.ReturnList;
import one.show.stat.service.RankUserDailyStatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年5月30日 下午10:15:43
 *
 * 
 */
@Component
public class RankUserDailyStatServiceImpl implements RankUserDailyStatService {

	@Autowired
	private RankUserDailyMapper rankUserDailyMapper;
	
	@Override
	public RankUserDaily findRankUserDaily(Long uid, Integer type, Integer date)
			throws ServiceException {

		try {
			return rankUserDailyMapper.find(uid, type, date);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void save(RankUserDaily rankUserDaily) throws ServiceException {

		try {
			 rankUserDailyMapper.save(rankUserDaily);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(Long id, Double number) throws ServiceException {

		try {
			 rankUserDailyMapper.update(id, number);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ReturnList<RankUserDaily> findRankUserList(int beginTime,
			int endTime, int type, int start, int count)
			throws ServiceException {

		ReturnList<RankUserDaily> rankUserList = new ReturnList<RankUserDaily>();
        try {
        	 List<RankUserDaily> list = rankUserDailyMapper.findList(beginTime, endTime, type, start, count);
        	
        	 rankUserList.count = rankUserDailyMapper.findListCount(beginTime, endTime, type, start, count);
        	 rankUserList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return rankUserList;
	
	}

}


