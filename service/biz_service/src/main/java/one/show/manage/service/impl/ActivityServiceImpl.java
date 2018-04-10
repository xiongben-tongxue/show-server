package one.show.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.show.common.TimeUtil;
import one.show.manage.dao.ActivityConfigMapper;
import one.show.manage.dao.ActivityShareMapper;
import one.show.manage.dao.ActivityShareRewardMapper;
import one.show.manage.domain.ActivityConfig;
import one.show.manage.domain.ActivityShare;
import one.show.manage.domain.ActivityShareReward;
import one.show.manage.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityConfigMapper activityConfigMapper;
	
	@Autowired
	private ActivityShareMapper activityShareMapper;
	
	@Autowired
	private ActivityShareRewardMapper activityShareRewardMapper;

	@Override
	public List<ActivityConfig> findAllActivityConfigs() {
		return activityConfigMapper.findAll();
	}

	@Override
	public ActivityShare findActivityShareByDid(String did) {
		return activityShareMapper.findById(did);
	}

	@Override
	public void updateActivityShare(String did, Map<String, String> updateContent) {
		activityShareMapper.updateById(did, updateContent);
	}

	@Override
	public void saveActivityShare(ActivityShare activityShare) {
		activityShareMapper.insertActivityShare(activityShare);
	}

	@Override
	public ActivityShareReward findActivityShareRewardByDate(int date) {
		return activityShareRewardMapper.findByDate(date);
	}

	@Override
	public void updateActivityShareReward(int id,
			Map<String, String> updateContent) {
		activityShareRewardMapper.updateActivityShareRewardById(id, updateContent);
	}

	@Override
	public void saveActivityShareReward(ActivityShareReward activityShareReward) {
		activityShareRewardMapper.insertActivityShareReward(activityShareReward);
	}

	@Override
	public void addToUserShare(String did, String shareReward) {
		int updateTime = TimeUtil.getCurrentTimestamp();
		activityShareMapper.addToUserShare(did,shareReward,updateTime);
	}

	@Override
	public void addToShareReward(int date,String name, double showCoin) {
		int updateTime = TimeUtil.getCurrentTimestamp();
		Map<String, String> updateContent = new HashMap<String, String>();
		updateContent.put(name, showCoin+"");
		updateContent.put("update_time", updateTime+"");
		activityShareRewardMapper.addToShareReward(date,updateContent);
	}

	@Override
	public void updateActivityConfig(int id, Map<String, String> updateContent) {
		int updateTime = TimeUtil.getCurrentTimestamp();
		updateContent.put("update_time", updateTime+"");
		activityConfigMapper.updateActivityConfigById(id, updateContent);
	}
}
