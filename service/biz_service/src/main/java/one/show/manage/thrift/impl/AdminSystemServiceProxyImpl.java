/**
 * 
 */
package one.show.manage.thrift.impl;

import java.util.ArrayList;
import java.util.List;

import one.show.common.Constant.ADMIN_STATUS;
import one.show.common.exception.ServiceException;
import one.show.manage.domain.AdminFunc;
import one.show.manage.domain.AdminRole;
import one.show.manage.domain.AdminRoleFunc;
import one.show.manage.domain.AdminUser;
import one.show.manage.domain.AdminUserRole;
import one.show.manage.domain.OperateLog;
import one.show.manage.domain.ReturnList;
import one.show.manage.service.AdminFuncService;
import one.show.manage.service.AdminRoleService;
import one.show.manage.service.AdminUserService;
import one.show.manage.service.OperateLogService;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface;
import one.show.manage.thrift.view.AdminFuncView;
import one.show.manage.thrift.view.AdminRoleFuncView;
import one.show.manage.thrift.view.AdminRoleListView;
import one.show.manage.thrift.view.AdminRoleView;
import one.show.manage.thrift.view.AdminUserListView;
import one.show.manage.thrift.view.AdminUserRoleView;
import one.show.manage.thrift.view.AdminUserView;
import one.show.manage.thrift.view.OperateLogView;


/**
 * @author Haliaeetus leucocephalus 2018年1月22日 下午5:57:46
 *
 */

@Component("adminSystemServiceProxyImpl")
public class AdminSystemServiceProxyImpl implements Iface{
	private static Logger logger = LoggerFactory.getLogger(AdminSystemServiceProxyImpl.class);
	 
	@Autowired
	private AdminFuncService adminFuncService;
	
	 
	@Autowired
	private AdminUserService adminUserService;
	
	 
	@Autowired
	private AdminRoleService adminRoleService;
	
	@Autowired
	private OperateLogService operateLogService;

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#findFuncList(int)
	 */
	@Override
	public List<AdminFuncView> findFuncList(int status) throws TException {
		
		ADMIN_STATUS adminStatus = null;
		
		try {
			adminStatus = ADMIN_STATUS.values()[status];
		} catch (Exception e) {
		}
		
		List<AdminFuncView> viewList = new ArrayList<AdminFuncView>();
		List<AdminFunc> list = null;
		try {
			list = adminFuncService.getFuncList(adminStatus);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		if (list != null){
			for(AdminFunc adminFunc : list){
				AdminFuncView adminFuncView = new AdminFuncView();
				BeanUtils.copyProperties(adminFunc, adminFuncView);
				viewList.add(adminFuncView);
			}
		}
		return viewList;
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#saveFunc(one.show.manage.thrift.view.AdminFuncView)
	 */
	@Override
	public String saveFunc(AdminFuncView adminFuncView) throws TException {
		AdminFunc adminFunc = new AdminFunc();
		BeanUtils.copyProperties(adminFuncView, adminFunc);
		
		try {
			return adminFuncService.add(adminFunc);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#updateFunc(one.show.manage.thrift.view.AdminFuncView)
	 */
	@Override
	public void updateFunc(AdminFuncView adminFuncView) throws TException {
		AdminFunc adminFunc = new AdminFunc();
		BeanUtils.copyProperties(adminFuncView, adminFunc);
		
		try {
			adminFuncService.update(adminFunc);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#deleteFunc(java.lang.String)
	 */
	@Override
	public void deleteFunc(String funcId) throws TException {

		try {
			adminFuncService.delete(funcId);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#findFuncById(java.lang.String)
	 */
	@Override
	public AdminFuncView findFuncById(String funcId) throws TException {
		AdminFuncView adminFuncView = new AdminFuncView();
		AdminFunc adminFunc = null;
		try {
			adminFunc = adminFuncService.getById(funcId);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (adminFunc != null){
			BeanUtils.copyProperties(adminFunc, adminFuncView);
			return adminFuncView;
		}else{
			return null;
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#findUser(java.lang.String)
	 */
	@Override
	public AdminUserView findUser(String userName) throws TException {
		
		AdminUserView adminUserView = new AdminUserView();
		AdminUser adminUser = null;
		try {
			adminUser = adminUserService.getUser(userName);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (adminUser != null){
			BeanUtils.copyProperties(adminUser, adminUserView);
			return adminUserView;
		}else{
			return null;
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#findUserRole(java.lang.String)
	 */
	@Override
	public List<AdminUserRoleView> findUserRole(String userName)
			throws TException {
		
		List<AdminUserRoleView> viewList = new ArrayList<AdminUserRoleView>();
		List<AdminUserRole> list = null;
		try {
			list = adminUserService.getUserRole(userName);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		if (list != null){
			for(AdminUserRole adminUserRole : list){
				AdminUserRoleView adminUserRoleView = new AdminUserRoleView();
				BeanUtils.copyProperties(adminUserRole, adminUserRoleView);
				viewList.add(adminUserRoleView);
			}
		}
		return viewList;
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#findRoleById(int)
	 */
	@Override
	public AdminRoleView findRoleById(int roleId) throws TException {
		AdminRoleView adminRoleView = new AdminRoleView();
		AdminRole adminRole = null;
		try {
			adminRole = adminRoleService.getRole(roleId);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (adminRole != null){
			BeanUtils.copyProperties(adminRole, adminRoleView);
			return adminRoleView;
		}else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#findFuncListByRoleId(int)
	 */
	@Override
	public List<AdminRoleFuncView> findFuncListByRoleId(int roleId)
			throws TException {
		
		List<AdminRoleFuncView> viewList = new ArrayList<AdminRoleFuncView>();
		List<AdminRoleFunc> list = null;
		try {
			list = adminRoleService.getRoleFunc(roleId);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		if (list != null){
			for(AdminRoleFunc adminRoleFunc : list){
				AdminRoleFuncView adminRoleFuncView = new AdminRoleFuncView();
				BeanUtils.copyProperties(adminRoleFunc, adminRoleFuncView);
				viewList.add(adminRoleFuncView);
			}
		}
		return viewList;
	}

	


	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser(String userName) throws TException {

		try {
			adminUserService.delelteUser(userName);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#saveUser(one.show.manage.thrift.view.AdminUserView, java.util.List)
	 */
	@Override
	public void saveUser(AdminUserView adminUserView)
			throws TException {
		
		AdminUser adminUser = new AdminUser();
		BeanUtils.copyProperties(adminUserView, adminUser);
		
		try {
			adminUserService.addUser(adminUser, adminUserView.roles);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#updateUser(one.show.manage.thrift.view.AdminUserView, java.util.List)
	 */
	@Override
	public void updateUser(AdminUserView adminUserView)
			throws TException {
		
		AdminUser adminUser = new AdminUser();
		BeanUtils.copyProperties(adminUserView, adminUser);
		
		try {
			adminUserService.updateUser(adminUser, adminUserView.roles);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#findUserList(int, int, int)
	 */
	@Override
	public AdminUserListView findUserList(int status, int start, int count)
			throws TException {

		ADMIN_STATUS s = null;
		try {
			s = ADMIN_STATUS.values()[status];
		} catch (Exception e) {
		}
		
		try {
			ReturnList<AdminUser> returnList = adminUserService.getUserList(s, start, count);
			AdminUserListView adminUserListView = new AdminUserListView();
			adminUserListView.userList = new ArrayList<AdminUserView>();
			
			if (returnList != null && returnList.objects != null){
				for(AdminUser adminUser : returnList.objects){
					AdminUserView adminUserView = new AdminUserView();
					BeanUtils.copyProperties(adminUser, adminUserView);
					adminUserListView.userList.add(adminUserView);
				}
			}
			
			adminUserListView.count = returnList.count;
			return adminUserListView;
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		
		
	}


	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#findRoleList(int, int, int)
	 */
	@Override
	public AdminRoleListView findRoleList(int status, int start, int count)
			throws TException {
		
		ADMIN_STATUS s = null;
		try {
			s = ADMIN_STATUS.values()[status];
		} catch (Exception e) {
		}
		
		try {
			ReturnList<AdminRole> returnList = adminRoleService.getRoleList(s, start, count);
			AdminRoleListView adminRoleListView = new AdminRoleListView();
			adminRoleListView.roleList = new ArrayList<AdminRoleView>();
			
			if (returnList != null && returnList.objects != null){
				for(AdminRole adminRole : returnList.objects){
					AdminRoleView adminRoleView = new AdminRoleView();
					BeanUtils.copyProperties(adminRole, adminRoleView);
					adminRoleListView.roleList.add(adminRoleView);
				}
			}
			
			adminRoleListView.count = returnList.count;
			return adminRoleListView;
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#saveRole(one.show.manage.thrift.view.AdminRoleView)
	 */
	@Override
	public void saveRole(AdminRoleView adminRoleView) throws TException {
		AdminRole adminRole = new AdminRole();
		BeanUtils.copyProperties(adminRoleView, adminRole);
		
		try {
			adminRoleService.addRole(adminRole, adminRoleView.funcs);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#updateRole(one.show.manage.thrift.view.AdminRoleView)
	 */
	@Override
	public void updateRole(AdminRoleView adminRoleView) throws TException {

		AdminRole adminRole = new AdminRole();
		BeanUtils.copyProperties(adminRoleView, adminRole);
		
		try {
			adminRoleService.updateRole(adminRole, adminRoleView.funcs);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#deleteRole(int)
	 */
	@Override
	public void deleteRole(int roleId) throws TException {

		try {
			adminRoleService.deleteRole(roleId);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#updatePwd(java.lang.String, java.lang.String)
	 */
	@Override
	public void updatePwd(String userName, String password) throws TException {
		try {
			adminUserService.updatePwd(userName, password);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.thrift.iface.AdminSystemServiceProxy.Iface#saveOperateLog(one.show.manage.thrift.view.OperateLogView)
	 */
	@Override
	public void saveOperateLog(OperateLogView operateLogView) throws TException {

		OperateLog operateLog = new OperateLog();
		BeanUtils.copyProperties(operateLogView, operateLog);
		
		try {
			operateLogService.save(operateLog);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		
	}


}


