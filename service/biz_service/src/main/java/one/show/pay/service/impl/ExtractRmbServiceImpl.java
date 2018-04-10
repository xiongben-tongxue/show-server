package one.show.pay.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.TimeUtil;
import one.show.common.TypeUtil;
import one.show.common.exception.ServiceException;
import one.show.pay.dao.ExtractRmbMapper;
import one.show.pay.domain.ExtractRmb;
import one.show.pay.domain.ReturnList;
import one.show.pay.service.ExtractRmbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.pay.thrift.view.ExtractRmbView;

@Component
public class ExtractRmbServiceImpl implements ExtractRmbService {

	private static final Logger log = LoggerFactory.getLogger(ExtractRmbServiceImpl.class);
	
	@Autowired
	ExtractRmbMapper extractRmbMapper;

	@Override
	public ReturnList<ExtractRmb> getExtractRmbListByUid(
			Map<String, String> paramMap, Integer start, Integer count)
			throws ServiceException {

		ReturnList<ExtractRmb> extractRmbList = new ReturnList<ExtractRmb>();
		try {
			List<ExtractRmb> list = extractRmbMapper.getExtractRmbListByUid(paramMap, start, count);

			extractRmbList.count = extractRmbMapper.getExtractRmbCountByUid(paramMap);
			extractRmbList.objects = list;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return extractRmbList;
	}

	@Override
	public Double getExtractRmbTotalByUid(long uid, String key,
			Map<String, String> paramMap) throws ServiceException {
		try {
			return extractRmbMapper.getExtractRmbTotalByUid(uid, key, paramMap);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateExtractRmbById(long id, Map<String, String> paramMap)
			throws ServiceException {
		try {
			paramMap.put("update_time", TimeUtil.getCurrentTimestamp()+"");
			extractRmbMapper.updateExtractRmbById(id, paramMap);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveExtractRmb(ExtractRmb extractRmb) throws ServiceException {
		try {
			extractRmbMapper.saveExtractRmb(extractRmb);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public Long getMaxBatchNo() {
		return extractRmbMapper.getMaxBatchNo();
	}

	@Override
	public List<ExtractRmb> getExtractRmbListByBatchNo(long batchNo)
			throws ServiceException {
		return extractRmbMapper.getExtractRmbListByBatchNo(batchNo);
	}

	@Override
	public List<ExtractRmb> getExtractRmbListByStatus(int status, Integer count){
		return extractRmbMapper.getExtractRmbListByStatus(status, count);
	}

	@Override
	public ExtractRmbView getExtractRmbViewById(long id) {
		return extractRmbMapper.getExtractRmbViewById(id);
	}

	@Override
	public Double getExtractRmbTotalCount() {
		return extractRmbMapper.getExtractRmbTotalCount();
	}

}
