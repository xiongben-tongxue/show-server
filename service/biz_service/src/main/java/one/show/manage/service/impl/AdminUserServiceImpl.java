/**
 * 
 */
package one.show.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import one.show.common.Constant.ADMIN_STATUS;
import one.show.common.exception.ServiceException;
import one.show.manage.dao.AdminUserMapper;
import one.show.manage.dao.AdminUserRoleMapper;
import one.show.manage.domain.AdminUser;
import one.show.manage.domain.AdminUserRole;
import one.show.manage.domain.ReturnList;
import one.show.manage.service.AdminUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Haliaeetus leucocephalus 2018年1月24日 下午7:34:35
 *
 */
@Component
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserMapper adminUserMapper;
	
	@Autowired
	private AdminUserRoleMapper adminUserRoleMapper;
	
	  /**
     * 更新用户角色
     * @param userName
     * @param roleIds
     * @throws DaoException
     */
    private void updateUserRole(String userName, List<Integer> roleIds)  {
    	
        adminUserRoleMapper.deleteUserRole(userName);
      
        if (roleIds != null){
            for(Integer roleId : roleIds){
                AdminUserRole aur = new AdminUserRole();
                aur.setUserName(userName);
                aur.setRoleId(roleId);
                adminUserRoleMapper.save(aur);
            }
        }
        
    }


	
	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminUserService#addUser(one.show.manage.domain.AdminUser, java.lang.Long[])
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ServiceException.class })
	public void addUser(AdminUser au, List<Integer> roleIds) throws ServiceException {
	 	try {
            adminUserMapper.save(au);
            
            updateUserRole(au.getUserName(), roleIds);
            
        } catch (Exception e) {
            throw new ServiceException(e);
        }
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminUserService#getUser(java.lang.String)
	 */
	@Override
	public AdminUser getUser(String userName) throws ServiceException {
		AdminUser au = null;
        try {
            au = adminUserMapper.findUserByUserName(userName);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return au;

	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminUserService#getUserList(one.show.common.Constant.ADMIN_STATUS, int, int)
	 */
	@Override
	public ReturnList<AdminUser> getUserList(ADMIN_STATUS status, int start,
			int count) throws ServiceException {
		
		ReturnList<AdminUser> userList = new ReturnList<AdminUser>();
        try {
        	 List<AdminUser> list = adminUserMapper.findUserList(status == null ? null :status.ordinal(), start, count);
        	
            userList.count = adminUserMapper.findUserListCount(status == null ? null :status.ordinal());
            userList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return userList;

	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminUserService#updateUser(one.show.manage.domain.AdminUser, java.lang.Long[])
	 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ServiceException.class })
	@Override
	public void updateUser(AdminUser au, List<Integer> roleIds)
			throws ServiceException {
		 try {
	          //修改用户数据
			 adminUserMapper.update(au);
	            
	         updateUserRole(au.getUserName(), roleIds);
	          
	     } catch (Exception e) {
	            throw new ServiceException(e);
	     }

		
	}


	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminUserService#delelteUser(java.lang.String)
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ServiceException.class })
	public void delelteUser(String userName) throws ServiceException {

		 try {
	            adminUserMapper.delete(userName);
	            
	            updateUserRole(userName, null);
	        } catch (Exception e) {
	        	throw new ServiceException(e);
	        }

	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminUserService#getUserRole(java.lang.String)
	 */
	@Override
	public List<AdminUserRole> getUserRole(String userName)
			throws ServiceException {
		
	        List<AdminUserRole> list = new ArrayList<AdminUserRole>();
	        try {
	           list = adminUserRoleMapper.findUserRoleList(userName);
	        } catch (Exception e) {
	            throw new ServiceException(e);
	        }
	        return list;

	}



	/* (non-Javadoc)
	 * @see one.show.manage.service.AdminUserService#updatePwd(java.lang.String, java.lang.String)
	 */
	@Override
	public void updatePwd(String userName, String password)
			throws ServiceException {
		try {
			adminUserMapper.updatePwd(userName, password);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

}


