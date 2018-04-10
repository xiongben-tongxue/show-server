package one.show.manage.thrift.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import one.show.manage.dao.ActivityConfigMapper;
import one.show.manage.domain.ActivityConfig;
import one.show.manage.domain.ActivityShare;
import one.show.manage.domain.ActivityShareReward;
import one.show.manage.service.ActivityService;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.manage.thrift.iface.ActivityServiceProxy.Iface;
import one.show.manage.thrift.view.ActivityConfigView;
import one.show.manage.thrift.view.ActivityShareRewardView;
import one.show.manage.thrift.view.ActivityShareView;

@Component("activityServiceProxyImpl")
public class ActivityServiceProxyImpl implements Iface{
	private static Logger logger = LoggerFactory.getLogger(ActivityServiceProxyImpl.class);
	
	@Autowired
	ActivityService activityService;

	@Override
	public List<ActivityConfigView> findAllActivityConfigs() throws TException {
		List<ActivityConfig> list = activityService.findAllActivityConfigs();
		List<ActivityConfigView> activityConfigViews = new ArrayList<ActivityConfigView>();
		if(list!=null){
			for(ActivityConfig activityConfig :list){
				ActivityConfigView activityConfigView = new ActivityConfigView();
				BeanUtils.copyProperties(activityConfig, activityConfigView);
				activityConfigViews.add(activityConfigView);
			}
		}
		return activityConfigViews;
	}

	@Override
	public ActivityShareView findActivityShareByDid(String did) throws TException {
		ActivityShareView activityShareView = null;
		ActivityShare activityShare = activityService.findActivityShareByDid(did);
		if(activityShare!=null){
			activityShareView = new ActivityShareView();
			BeanUtils.copyProperties(activityShare, activityShareView);
		}
		return activityShareView;
	}

	@Override
	public void updateActivityShare(String did, Map<String, String> updateContent)
			throws TException {
		activityService.updateActivityShare(did, updateContent);
	}

	@Override
	public void saveActivityShare(ActivityShareView activityShareView)
			throws TException {
		if(activityShareView==null){
			return;
		}
		ActivityShare activityShare = new ActivityShare();
		BeanUtils.copyProperties(activityShareView, activityShare);
		activityService.saveActivityShare(activityShare);
	}

	@Override
	public ActivityShareRewardView findActivityShareRewardByDate(int date)
			throws TException {
		ActivityShareReward activityShareReward = activityService.findActivityShareRewardByDate(date);
		ActivityShareRewardView activityShareRewardView = null;
		if(activityShareReward!=null){
			activityShareRewardView = new ActivityShareRewardView();
			BeanUtils.copyProperties(activityShareReward, activityShareRewardView);
		}
		return activityShareRewardView;
	}

	@Override
	public void updateActivityShareReward(int id,
			Map<String, String> updateContent) throws TException {
		activityService.updateActivityShareReward(id, updateContent);
	}

	@Override
	public void saveActivityShareReward(
			ActivityShareRewardView activityShareRewardView) throws TException {
		if(activityShareRewardView==null){
			return;
		}
		ActivityShareReward activityShareReward = new ActivityShareReward();
		BeanUtils.copyProperties(activityShareRewardView, activityShareReward);
		activityService.saveActivityShareReward(activityShareReward);
	}

	@Override
	public void addToUserShare(String did, String shareReward) throws TException {
		activityService.addToUserShare(did,shareReward);
	}

	@Override
	public void addToShareReward(int date,String name, double showCoin) throws TException {
		activityService.addToShareReward(date,name,showCoin);
	}

	@Override
	public void updateActivityConfig(int id, Map<String, String> updateContent)
			throws TException {
		activityService.updateActivityConfig(id, updateContent);
	}
}
