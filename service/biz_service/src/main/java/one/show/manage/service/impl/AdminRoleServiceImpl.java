/**
 * 
 */
package one.show.manage.service.impl;

import java.util.List;

import one.show.common.Constant.ADMIN_STATUS;
import one.show.common.exception.ServiceException;
import one.show.manage.dao.AdminRoleFuncMapper;
import one.show.manage.dao.AdminRoleMapper;
import one.show.manage.domain.AdminRole;
import one.show.manage.domain.AdminRoleFunc;
import one.show.manage.domain.ReturnList;
import one.show.manage.service.AdminRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Haliaeetus leucocephalus 2018年1月24日 下午9:52:41
 *
 */
@Component
public class AdminRoleServiceImpl implements AdminRoleService {

	@Autowired
	private AdminRoleMapper adminRoleMapper;
	
	@Autowired
	private AdminRoleFuncMapper adminRoleFuncMapper;
	
	
	 /**
     * 更新角色对应的功能
     * @param roleId
     * @param funcIds
     * @throws DaoException
     */
    private void updateRoleFunc(Integer roleId, List<String> funcIds) {
        
        adminRoleFuncMapper.deleteRoleFunc(roleId);
        
        if (funcIds != null){
            for(String funcId : funcIds){
                AdminRoleFunc arf = new AdminRoleFunc();
                arf.setFuncId(funcId);
                arf.setRoleId(roleId);

                adminRoleFuncMapper.save(arf);
            }
        }
    }

	
	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminRoleService#getRole(java.lang.Integer)
	 */
	@Override
	public AdminRole getRole(Integer roleId) throws ServiceException {
		try {
			return adminRoleMapper.findRoleById(roleId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminRoleService#getRoleList(one.show.common.Constant.ADMIN_STATUS)
	 */
	@Override
	public ReturnList<AdminRole> getRoleList(ADMIN_STATUS status, Integer start, Integer count)
			throws ServiceException {
		
		ReturnList<AdminRole> userList = new ReturnList<AdminRole>();
        try {
        	 List<AdminRole> list = adminRoleMapper.findRoleList(status == null ? null :status.ordinal(), start, count);
        	
        	 userList.count = adminRoleMapper.findRoleListCount(status == null ? null :status.ordinal());
        	 userList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return userList;
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminRoleService#addRole(one.show.manage.domain.AdminRole, java.lang.String[])
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ServiceException.class })
	public void addRole(AdminRole ar, List<String> funcIds) throws ServiceException {
		try {
		 	adminRoleMapper.save(ar);
            
            updateRoleFunc(ar.getRoleId(), funcIds);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminRoleService#getRoleFunc(java.lang.Integer)
	 */
	@Override
	public List<AdminRoleFunc> getRoleFunc(Integer roleId)
			throws ServiceException {
		try {
			return adminRoleFuncMapper.findFuncListByRoleId(roleId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminRoleService#updateRole(one.show.manage.domain.AdminRole, java.lang.String[])
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ServiceException.class })
	public void updateRole(AdminRole ar, List<String> funcIds)
			throws ServiceException {
		try {
		 	adminRoleMapper.update(ar);
            
            updateRoleFunc(ar.getRoleId(), funcIds);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminRoleService#deleteRole(java.lang.Integer)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ServiceException.class })
	public void deleteRole(Integer roleId) throws ServiceException {

	 	try {
		 	adminRoleMapper.delete(roleId);
            
            updateRoleFunc(roleId, null);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        

		 
	}

}


