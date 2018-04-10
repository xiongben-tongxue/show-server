package one.show.manage.service.impl;

import one.show.common.exception.ServiceException;

import org.springframework.util.StringUtils;

/**
 * 
 * @author Haliaeetus leucocephalus
 */
public abstract class FuncIDBuilder {
	
	/**
	 * 返回fatherFuncId下最后一个节点id
	 * @param fatherFuncId
	 * @return
	 */
	public abstract String getLastChildFuncId(String fatherFuncId) throws ServiceException;
	
	public String generateId(String fatherFuncId) throws Exception{
		String funcId = "";
		String lastChildFuncId = getLastChildFuncId(fatherFuncId);
		
		String prefix = "";
		if (fatherFuncId!=null && !fatherFuncId.equalsIgnoreCase("-1")){
			prefix = fatherFuncId;
		}
		
		if (StringUtils.isEmpty(lastChildFuncId)){
			funcId = prefix+"01";
		}else{

			try{
				String suffix = lastChildFuncId.substring(prefix.length(),lastChildFuncId.length());
				
				int id = Integer.parseInt(suffix);
				id ++;
				String newSuffix = String.valueOf(id);
				if (id<10){
					newSuffix = "0"+newSuffix;
				}
				funcId = prefix+newSuffix;
				
			}catch(Exception e){
				throw new Exception("ID生成异常:"+e.getMessage());
			}
			
		}
		
		return funcId;
	}
}
