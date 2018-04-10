package one.show.search.thrift.impl;

import java.util.List;

import one.show.search.service.SearchService;
import one.show.search.thrift.iface.SearchServiceProxy.Iface;
import one.show.search.thrift.view.UserSearchView;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus 2015年10月18日
 *
 */
@Component
public class SearchServiceProxyImpl implements Iface{

	private static final Logger log = LoggerFactory.getLogger(SearchServiceProxyImpl.class);
	
	@Autowired
	SearchService searchService;
	
	@Override
	public List<Long> searchUser(String keyword, int cursor, int count) throws TException {
		List<Long> userList = null;
		try {
			userList = searchService.searchUser(keyword, cursor, count);
		} catch (Exception e) {
			throw new TException(e);
		}
		
		return userList;
	}


	@Override
	public void insertUser(UserSearchView userSearchView) throws TException {
		try {
			if(userSearchView!=null){
				searchService.insertUser(userSearchView);
			}
			
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void updateUser(UserSearchView userSearchView) throws TException {
		try {
			if(userSearchView!=null){
				searchService.updateUser(userSearchView);
			}
			
		} catch (Exception e) {
			throw new TException(e);
		}
	}




	@Override
	public void deleteUser(long uid) throws TException {
		try {
			searchService.deleteUser(uid);
			
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}


			
}
