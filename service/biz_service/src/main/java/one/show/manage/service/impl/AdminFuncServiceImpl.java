
package one.show.manage.service.impl;

import java.util.List;

import one.show.common.Constant.ADMIN_STATUS;
import one.show.common.exception.ServiceException;
import one.show.manage.dao.AdminFuncMapper;
import one.show.manage.domain.AdminFunc;
import one.show.manage.service.AdminFuncService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Haliaeetus leucocephalus
 *
 */
@Component
public class AdminFuncServiceImpl  implements AdminFuncService{

	@Autowired
	private AdminFuncMapper adminFuncMapper;
	
    @Override
    public String add(AdminFunc af) throws ServiceException {
        String funcId = null;
        try {
            funcId = new FuncIDBuilder() {
                @Override
                public String getLastChildFuncId(String fatherFuncId) throws ServiceException {
                    List<AdminFunc> childList = getChildList(fatherFuncId, false);
                    if (childList == null || childList.size() == 0) {
                        return null;
                    } else {
                        return childList.get(childList.size() - 1).getFuncId();
                    }

                }
            }.generateId(af.getFatherFuncId());
            
            af.setFuncId(funcId);
        } catch (Exception e1) {
            throw new ServiceException(e1);
        }
        
        try {
        	adminFuncMapper.save(af);
        } catch (Exception e) {
           throw new ServiceException(e);
        }
        
        return funcId ;
        
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ServiceException.class })
    public void delete(String funcId) throws ServiceException {
        try {
            
            adminFuncMapper.delete(funcId);
            
            //删除所有子节点
            List<AdminFunc> list = getChildList(funcId,true);
            if (list != null){
                for (AdminFunc func : list){
                	adminFuncMapper.delete(func.getFuncId());
                }
            }
           
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        
    }

    @Override
    public AdminFunc getById(String funcId) throws ServiceException {
        AdminFunc af = null;
        try {
            af = adminFuncMapper.findFuncById(funcId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        
        return af;
    }

    @Override
    public List<AdminFunc> getChildList(String funcId, boolean recursion) throws ServiceException{
        
        try {
        	List<AdminFunc> list = adminFuncMapper.findChildList(funcId, recursion);
        	return list;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AdminFunc> getFuncList(ADMIN_STATUS status) throws ServiceException {
       
        try {
        	 List<AdminFunc> list = adminFuncMapper.findFuncList(status==null?null:status.ordinal());
        	 return list;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ServiceException.class })
    public void update(AdminFunc af) throws ServiceException {
        try {
        	adminFuncMapper.update(af);
        	adminFuncMapper.updateChildStatusByFuncId(af.getFuncId(), af.getStatus());
            
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        
    }

}


