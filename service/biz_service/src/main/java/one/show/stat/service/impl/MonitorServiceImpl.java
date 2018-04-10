/**
 * 
 */
package one.show.stat.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.stat.dao.MonitorStatMapper;
import one.show.stat.domain.MonitorStat;
import one.show.stat.domain.ReturnList;
import one.show.stat.service.MonitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus 2018年3月12日 下午10:39:27
 *
 */
@Component
public class MonitorServiceImpl implements MonitorService {

	@Autowired
	private MonitorStatMapper monitorStatMapper;
	/* (non-Javadoc)
	 * @see one.show.stat.service.MonitorService#findMonitorStatList(int, int, int, int, int)
	 */
	@Override
	public ReturnList<MonitorStat> findMonitorStatList(int beginTime,
			int endTime, int type, int start, int count)
			throws ServiceException {

		ReturnList<MonitorStat> monitorStatList = new ReturnList<MonitorStat>();
        try {
        	 List<MonitorStat> list = monitorStatMapper.findList(beginTime, endTime, type, start, count);
        	
            monitorStatList.count = monitorStatMapper.findListCount(beginTime, endTime, type, start, count);
            monitorStatList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return monitorStatList;
        
	}
	@Override
	public void updateMonitorStatById(Long id, String statement)
			throws ServiceException {
		try {
			monitorStatMapper.updateById(id, statement);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	@Override
	public void save(MonitorStat monitorStat) throws ServiceException {

		try {
			monitorStatMapper.save(monitorStat);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	@Override
	public MonitorStat findMonitorStat(String name, int type, int time)
			throws ServiceException {

		try {
			return monitorStatMapper.find(name, type, time);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

}


