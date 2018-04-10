/**
 * 
 */
package one.show.manage.service.impl;

import java.util.List;

import one.show.common.cache.LocalCache;
import one.show.common.exception.ServiceException;
import one.show.manage.dao.SensitiveMapper;
import one.show.manage.domain.App;
import one.show.manage.domain.ReturnList;
import one.show.manage.domain.Word;
import one.show.manage.service.SensitiveWordService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus 2018年1月14日 下午12:41:40
 *
 */

@Component
public class SensitiveWordServiceImpl implements SensitiveWordService{
	
	
	private static final Logger log = LoggerFactory.getLogger(SensitiveWordServiceImpl.class);
	  

	@Autowired
	private SensitiveMapper sensitiveMapper;

	/* (non-Javadoc)
	 * @see one.show.sensitive.service.SensitiveWordService#filter(java.lang.String)
	 */
	@Override
	public String filter(String word, final Integer appId) throws ServiceException {
		
		try {
			SensitiveWordFilter sensitiveWordFilter = new LocalCache<SensitiveWordFilter>() {

                @Override
                public SensitiveWordFilter getAliveObject() throws Exception {
                	List<String> list = sensitiveMapper.findWordListByAppId(appId);
            		SensitiveWordFilter sensitiveWordFilter = new SensitiveWordFilter();
            		sensitiveWordFilter.addFilterWords(list);
                    return sensitiveWordFilter;
                }
            }.put(60 * 10, "sensitive_word_list_"+appId);
            
            return sensitiveWordFilter.doFilter(word, "**");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	    
	}

	/* (non-Javadoc)
	 * @see one.show.sensitive.service.SensitiveWordService#findListByAppId(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ReturnList<Word> findListByAppId(Integer appId, String word, Integer start,
			Integer count) throws ServiceException {

		ReturnList<Word> wordList = new ReturnList<Word>();
        try {
        	 List<Word> list = sensitiveMapper.findListByAppId(appId, word, start, count);
        	
            wordList.count = sensitiveMapper.findCountByAppId(appId, word);
            wordList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return wordList;
	}

	/* (non-Javadoc)
	 * @see one.show.sensitive.service.SensitiveWordService#save(one.show.sensitive.domain.Word)
	 */
	@Override
	public void save(Word word) throws ServiceException {
		try {
			sensitiveMapper.save(word);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.sensitive.service.SensitiveWordService#delete(java.lang.Integer)
	 */
	@Override
	public void delete(Integer id) throws ServiceException {

		try {
			sensitiveMapper.delete(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.sensitive.service.SensitiveWordService#update(java.lang.Integer, java.lang.String)
	 */
	@Override
	public void update(Integer id, String word) throws ServiceException {

		try {
			sensitiveMapper.update(id, word);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.sensitive.service.SensitiveWordService#findAppList()
	 */
	@Override
	public List<App> findAppList() throws ServiceException {

		try {
			return sensitiveMapper.findAppList();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


