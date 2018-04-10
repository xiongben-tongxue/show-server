package one.show.manage.service.impl;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.ReportMapper;
import one.show.manage.domain.Report;
import one.show.manage.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	ReportMapper reportMapper;
	@Override
	public void saveReport(Report report) throws ServiceException {
		
		try {
			reportMapper.saveReport(report);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

}
