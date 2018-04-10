/**
 * 
 */
package one.show.manage.thrift.impl;

import java.util.ArrayList;
import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.manage.domain.App;
import one.show.manage.domain.ReturnList;
import one.show.manage.domain.Word;
import one.show.manage.service.SensitiveWordService;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.manage.thrift.iface.SensitiveServiceProxy.Iface;
import one.show.manage.thrift.view.AppView;
import one.show.manage.thrift.view.WordListView;
import one.show.manage.thrift.view.WordView;

/**
 * @author Haliaeetus leucocephalus 2018年1月14日 下午1:47:50
 *
 */

@Component("sensitiveServiceProxyImpl")
public class SensitiveServiceProxyImpl implements Iface{


	private static final Logger logger = LoggerFactory.getLogger(SensitiveServiceProxyImpl.class);
	
	@Autowired
	private SensitiveWordService sensitiveWordService;
	
	/* (non-Javadoc)
	 * @see one.show.sensitive.thrift.iface.SensitiveServiceProxy.Iface#filter(java.lang.String, int)
	 */
	@Override
	public String filter(String word, int appId) throws TException {

		try {
			return sensitiveWordService.filter(word, appId);
		} catch (ServiceException e) {
			throw new TException(e);		
		}
	}

	/* (non-Javadoc)
	 * @see one.show.sensitive.thrift.iface.SensitiveServiceProxy.Iface#findListByAppId(int, int, int)
	 */
	@Override
	public WordListView findListByAppId(int appId, String word, int start, int count)
			throws TException {

		try {
			ReturnList<Word> returnList = sensitiveWordService.findListByAppId(appId, word, start, count);
			WordListView wordListView = new WordListView();
			wordListView.wordList = new ArrayList<WordView>();
			
			if (returnList != null && returnList.objects != null){
				for(Word w : returnList.objects){
					WordView wordView = new WordView();
					BeanUtils.copyProperties(w, wordView);
					wordListView.wordList.add(wordView);
				}
			}
			wordListView.count = returnList.count;
			return wordListView;
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.sensitive.thrift.iface.SensitiveServiceProxy.Iface#save(one.show.sensitive.thrift.view.WordView)
	 */
	@Override
	public void save(WordView wordView) throws TException {

		Word word = new Word();
		BeanUtils.copyProperties(wordView, word);
		try {
			sensitiveWordService.save(word);
		} catch (ServiceException e) {
			throw new TException(e);		
		}
	}

	/* (non-Javadoc)
	 * @see one.show.sensitive.thrift.iface.SensitiveServiceProxy.Iface#deleteWord(int)
	 */
	@Override
	public void deleteWord(int id) throws TException {

		try {
			sensitiveWordService.delete(id);
		} catch (ServiceException e) {
			throw new TException(e);		
		}
	}

	/* (non-Javadoc)
	 * @see one.show.sensitive.thrift.iface.SensitiveServiceProxy.Iface#update(int, java.lang.String)
	 */
	@Override
	public void update(int id, String word) throws TException {

		try {
			sensitiveWordService.update(id, word);
		} catch (ServiceException e) {
			throw new TException(e);		
		}
	}

	/* (non-Javadoc)
	 * @see one.show.sensitive.thrift.iface.SensitiveServiceProxy.Iface#findAppList()
	 */
	@Override
	public List<AppView> findAppList() throws TException {
		List<AppView> viewList = new ArrayList<AppView>();
		List<App> list = null;
		try {
			list = sensitiveWordService.findAppList();
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		if (list != null){
			for(App app : list){
				AppView appView = new AppView();
				BeanUtils.copyProperties(app, appView);
				viewList.add(appView);
			}
		}
		return viewList;
	}

}


