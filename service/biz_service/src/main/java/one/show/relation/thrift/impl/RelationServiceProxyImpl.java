/**
 * 
 */
package one.show.relation.thrift.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.show.common.JacksonUtil;
import one.show.common.Constant.RELATION_ACTION;
import one.show.common.exception.ServiceException;
import one.show.common.mq.Publisher;
import one.show.common.mq.Queue;
import one.show.relation.domain.Fans;
import one.show.relation.domain.Feed;
import one.show.relation.domain.Follow;
import one.show.relation.service.FansService;
import one.show.relation.service.FeedService;
import one.show.relation.service.FollowService;
import one.show.relation.thrift.iface.RelationServiceProxy.Iface;
import one.show.relation.thrift.view.FansView;
import one.show.relation.thrift.view.FeedView;
import one.show.relation.thrift.view.FollowView;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangwei 2015年8月5日 下午9:48:19
 * 
 */

@Component("relationServiceProxyImpl")
public class RelationServiceProxyImpl implements Iface {

	private static final Logger log = LoggerFactory
			.getLogger(RelationServiceProxyImpl.class);

	@Autowired
	private FansService fansService;

	@Autowired
	private FollowService followService;
	
	@Autowired
	private FeedService feedService;



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * one.show.relation.thrift.iface.RelationServiceProxy.Iface#findFansListByUid
	 * (long, int, int)
	 */
	@Override
	public List<FansView> findFansListByUid(long uid, int start, int count)
			throws TException {
		List<FansView> viewList = new ArrayList<FansView>();
		List<Fans> list = null;
		try {
			list = fansService.findListByUid(uid, start, count);
		} catch (ServiceException e) {
			throw new TException(e);
		}

		if (list != null) {
			for (Fans fans : list) {
				FansView fansView = new FansView();
				BeanUtils.copyProperties(fans, fansView);
				viewList.add(fansView);
			}
		}
		return viewList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see one.show.relation.thrift.iface.RelationServiceProxy.Iface#
	 * findFollowListByUid(long, int, int)
	 */
	@Override
	public List<FollowView> findFollowListByUid(long uid, int start, int count)
			throws TException {

		List<FollowView> viewList = null;
		List<Follow> list = null;
		try {
			list = followService.findListByUid(uid, start, count);
		} catch (ServiceException e) {
			throw new TException(e);
		}

		if (list != null && list.size() > 0) {
			viewList = new ArrayList<FollowView>();
			for (Follow follow : list) {
				FollowView followView = new FollowView();
				BeanUtils.copyProperties(follow, followView);
				viewList.add(followView);
			}
		}
		return viewList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * one.show.relation.thrift.iface.RelationServiceProxy.Iface#follow(long,
	 * long)
	 */
	@Override
	public void follow(long uid, long fid) throws TException {
		Follow follow = new Follow(uid, fid);
		Fans fans = new Fans(fid, uid);

		try {


			followService.save(follow);
			fansService.save(fans);
			
			try {
				
				Map map = new HashMap();
				map.put("uid", uid);
				map.put("fid", fid);
				map.put("action", RELATION_ACTION.FOLLOW.toString());
				map.put("time", (int) (System.currentTimeMillis() / 1000));
				
				
				Publisher.getInstance().sendMessage(JacksonUtil.writeToJsonString(map), Queue.RELATION);
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			throw new TException(e);
		}
		
		
		

	}

	
	/*
         * (non-Javadoc)
         *
         * @see
         * one.show.relation.thrift.iface.RelationServiceProxy.Iface#unfollow(
         * long, long)
         */
	@Override
	public void unfollow(long uid, long fid) throws TException {

		try {
			followService.delete(uid, fid);
			fansService.delete(fid, uid);

			try {

				Map map = new HashMap();
				map.put("uid", uid);
				map.put("fid", fid);
				map.put("action", RELATION_ACTION.UNFOLLOW.toString());
				map.put("time", (int) (System.currentTimeMillis() / 1000));

				Publisher.getInstance().sendMessage(JacksonUtil.writeToJsonString(map), Queue.RELATION);

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			throw new TException(e);
		}
		
		

	}


	@Override
	public List<Boolean> isFollowed(long currentUser, List<Long> uids)
			throws TException {
		try {
			return followService.isFollowed(currentUser, uids);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}

	@Override
	public List<FeedView> findFeedListByUid(long uid, Map<String, String> condition, int start, int count)
			throws TException {

		List<FeedView> viewList = new ArrayList<FeedView>();
		List<Feed> list = null;
		try {
			list = feedService.findListByUid(uid, condition, start, count);
		} catch (ServiceException e) {
			throw new TException(e);
		}

		if (list != null) {
			for (Feed feed : list) {
				FeedView feedView = new FeedView();
				BeanUtils.copyProperties(feed, feedView);
				viewList.add(feedView);
			}
		}
		return viewList;
	}

	
	@Override
	public void saveFeed(FeedView feedView) throws TException {
		Feed feed = new Feed();
		BeanUtils.copyProperties(feedView, feed);
		try {
			feedService.save(feed);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateFeed(long uid, long fid, String point,
			Map<String, String> updateContent) throws TException {

		try {
			feedService.update(uid, fid, point, updateContent);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void deleteFeed(long uid, long fid, String point) throws TException {

		try {
			feedService.delete(uid, fid, point);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public int findFansCount(long uid) throws TException {
		try {
			return fansService.findFansCountByUid(uid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public int findFollowCount(long uid) throws TException {
		try {
			return followService.findFollowCountByUid(uid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<Boolean> isFans(long currentUser, List<Long> uids)
			throws TException {
		try {
			return followService.isFans(currentUser, uids);
		} catch (Exception e) {
			throw new TException(e);
		}
	
	}

}