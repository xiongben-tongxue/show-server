package one.show.manage.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.RegisterMapper;
import one.show.manage.domain.Register;
import one.show.manage.domain.ReturnList;
import one.show.manage.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterServiceImpl implements RegisterService{
	
	@Autowired
	RegisterMapper registerMapper;
	@Override
	public void saveRegister(Register register) throws ServiceException {
		
		try {
			registerMapper.saveRegister(register);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	@Override
	public Register getRegisterByUid(String uid) throws ServiceException {
		
		try {
			return registerMapper.getRegisterByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public void updateRegisterByUid(String uid, Map<String, String> paramMap)
			throws ServiceException {
		try {
			registerMapper.updateRegisterByUid(uid, paramMap);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	@Override
	public ReturnList<Register> getRegisterList(Map<String, String> paramMap,
			Integer start, Integer count) throws ServiceException {

		ReturnList<Register> registerList = new ReturnList<Register>();
		try {
			List<Register> list = registerMapper.getRegisterList(paramMap, start, count);

			registerList.count = registerMapper.getRegisterCount(paramMap);
			registerList.objects = list;

		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return registerList;
	}
	@Override
	public void updateRegister(Register register) throws ServiceException {
		try {
			registerMapper.updateRegister(register);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
}