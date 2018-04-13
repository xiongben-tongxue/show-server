/**
 * 
 */
package one.show.admin.view;

import java.util.List;

import one.show.manage.thrift.view.AdminFuncView;
import one.show.manage.thrift.view.AdminRoleView;
import one.show.manage.thrift.view.AdminUserView;

/**
 * @author zhangwei 2015年8月22日 下午9:49:37
 *
 */
public class User {
	private AdminUserView userView;
	
	private List<AdminFuncView> funcList;
	private AdminFuncView currentFunc;
	private List<AdminRoleView> roleList;
	
	

	public AdminUserView getUserView() {
		return userView;
	}

	public void setUserView(AdminUserView userView) {
		this.userView = userView;
	}

	public List<AdminFuncView> getFuncList() {
		return funcList;
	}

	public void setFuncList(List<AdminFuncView> funcList) {
		this.funcList = funcList;
	}

	public AdminFuncView getCurrentFunc() {
		return currentFunc;
	}

	public void setCurrentFunc(AdminFuncView currentFunc) {
		this.currentFunc = currentFunc;
	}

	public List<AdminRoleView> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<AdminRoleView> roleList) {
		this.roleList = roleList;
	}

	public Boolean authAccess(String funcUrl) {

		List<AdminFuncView> funcList = this.getFuncList();

		for (AdminFuncView adminFunc : funcList) {
			String[] urls = adminFunc.getUrl().split(",");
			for (String url : urls) {
				if (url.equalsIgnoreCase(funcUrl)||url.contains(funcUrl)) {
					this.setCurrentFunc(adminFunc);
					return true;
				}
			}

		}
		return false;
	}
}


