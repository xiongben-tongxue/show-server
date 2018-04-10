package one.show.user.service;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.user.domain.WhiteList;

/**
 * Created by Haliaeetus leucocephalus on 18/1/19.
 */
public interface WhiteListService {
    void save(long uid, int time) throws ServiceException;

    void remove(long uid) throws ServiceException;

    List<WhiteList> findWhiteList(int cursor, int count) throws ServiceException;

    WhiteList findWhiteListByUid(long uid) throws ServiceException;
    
   

}
