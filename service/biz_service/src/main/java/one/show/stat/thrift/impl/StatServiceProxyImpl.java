/**
 * 
 */
package one.show.stat.thrift.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.stat.domain.ActionLog;
import one.show.stat.domain.ActiveXbStat;
import one.show.stat.domain.Log;
import one.show.stat.domain.MonitorStat;
import one.show.stat.domain.RankUserDaily;
import one.show.stat.domain.ReturnList;
import one.show.stat.domain.RobotStatDaily;
import one.show.stat.domain.SummaryStat;
import one.show.stat.domain.UserStat;
import one.show.stat.domain.VideoStat;
import one.show.stat.service.ActionLogService;
import one.show.stat.service.ActiveXbService;
import one.show.stat.service.LogService;
import one.show.stat.service.MonitorService;
import one.show.stat.service.RankUserDailyStatService;
import one.show.stat.service.RobotStatDailyService;
import one.show.stat.service.SummaryStatService;
import one.show.stat.service.UserStatService;
import one.show.stat.service.VideoStatService;
import one.show.stat.thrift.iface.StatServiceProxy.Iface;
import one.show.stat.thrift.view.ActionLogView;
import one.show.stat.thrift.view.ActiveXbStatView;
import one.show.stat.thrift.view.LogView;
import one.show.stat.thrift.view.MonitorStatListView;
import one.show.stat.thrift.view.MonitorStatView;
import one.show.stat.thrift.view.RankUserDailyView;
import one.show.stat.thrift.view.RankUserListView;
import one.show.stat.thrift.view.RobotStatDailyView;
import one.show.stat.thrift.view.SummaryStatView;
import one.show.stat.thrift.view.UserStatView;
import one.show.stat.thrift.view.VideoStatView;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus 2018年1月6日 下午10:06:12
 *
 */

@Component("statServiceProxyImpl")
public class StatServiceProxyImpl implements Iface {

	@Autowired
	private UserStatService userStatService;
	
	@Autowired
	private VideoStatService videoStatService;
	@Autowired
	private MonitorService monitorService;
	
	@Autowired
	private SummaryStatService summaryStatService;
	
	@Autowired
	private RankUserDailyStatService rankUserDailyStatService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private ActionLogService actionLogService;
	
	@Autowired
	private RobotStatDailyService robotStatDailyService;
	
	@Autowired
	private ActiveXbService activeXbService;
	

	@Override
	public UserStatView findUserStatByUid(long uid) throws TException {
		UserStat userStat = userStatService.findByUid(uid);
		
		UserStatView userStatView = new UserStatView();
		
		if (userStat != null){
			BeanUtils.copyProperties(userStat, userStatView);
			return userStatView;
		}else {
			return null;
		}
	}

	@Override
	public List<UserStatView> findUserStatByUids(List<Long> uids) throws TException {
		List<UserStat> userStats = userStatService.findByUids(uids);
		List<UserStatView> userStatViews = new ArrayList<>();
		for (UserStat userStat : userStats) {
			UserStatView userStatView = new UserStatView();
			BeanUtils.copyProperties(userStat, userStatView);
			userStatViews.add(userStatView);
		}
		return userStatViews;
	}

	

	/* (non-Javadoc)
	 * @see one.show.stat.thrift.iface.StatServiceProxy.Iface#saveUserStat(one.show.stat.thrift.view.UserStatView)
	 */
	@Override
	public void saveUserStat(UserStatView userStatView) throws TException {

		UserStat userStat = new UserStat();
		BeanUtils.copyProperties(userStatView, userStat);
		userStatService.save(userStat);
	}

	@Override
	public VideoStatView findVideoStatByVid(long vid) throws TException {
		VideoStat videoStat = videoStatService.findByVid(vid);
		
		VideoStatView videoStatView = new VideoStatView();
		
		if (videoStat != null){
			BeanUtils.copyProperties(videoStat, videoStatView);
			return videoStatView;
		}else {
			return null;
		}
	}

	@Override
	public List<VideoStatView> findVideoStatByVids(List<Long> vids) throws TException {
		List<VideoStat> videoStats = videoStatService.findByVids(vids);
		return (List<VideoStatView>) CollectionUtils.collect(videoStats, new Transformer<VideoStat, VideoStatView>() {
			@Override
			public VideoStatView transform(VideoStat videoStat) {
				VideoStatView videoStatView = new VideoStatView();
				BeanUtils.copyProperties(videoStat, videoStatView);
				return videoStatView;
			}
		});
	}



	@Override
	public void saveVideoStat(VideoStatView videoStatView) throws TException {
		VideoStat videoStat = new VideoStat();
		BeanUtils.copyProperties(videoStatView, videoStat);
		videoStatService.save(videoStat);
		
	}


	/* (non-Javadoc)
	 * @see one.show.stat.thrift.iface.StatServiceProxy.Iface#findMonitorStatList(int, int, int, int, int)
	 */
	@Override
	public MonitorStatListView findMonitorStatList(int beginTime, int endTime,
			int type, int start, int count) throws TException {

		
		try {
			ReturnList<MonitorStat> returnList = monitorService.findMonitorStatList(beginTime, endTime, type, start, count);
			MonitorStatListView monitorStatListView = new MonitorStatListView();
			monitorStatListView.monitorStatList = new ArrayList<MonitorStatView>();
			
			if (returnList != null && returnList.objects != null){
				for(MonitorStat monitorStat : returnList.objects){
					MonitorStatView monitorStatView = new MonitorStatView();
					BeanUtils.copyProperties(monitorStat, monitorStatView);
					if(monitorStatView.getFailNum() == 0){
						monitorStatView.setFailRatio(0.0);
					}else{
						double   f  = ((double)monitorStatView.getFailNum()/monitorStatView.getTotalNum())*100;
						BigDecimal   b   =   new   BigDecimal(f);
						double   failRatio   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
						monitorStatView.setFailRatio(failRatio);
					}
					
					monitorStatListView.monitorStatList.add(monitorStatView);
				}
			}
			
			monitorStatListView.count = returnList.count;
			return monitorStatListView;
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}
	
	
	public static void main(String[] args) {
		double f = 1.3646090110304447E7;
		java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");

		System.out.println(df.format(f));
	}

	/* (non-Javadoc)
	 * @see one.show.stat.thrift.iface.StatServiceProxy.Iface#getSummaryStatList(int)
	 */
	@Override
	public List<SummaryStatView> getSummaryStatList(int time) throws TException {
		List<SummaryStatView> resultList = new ArrayList<SummaryStatView>();
		
		List<SummaryStat> list = null;
		try {
			list = summaryStatService.findSummaryList(time);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (list != null){
			for(SummaryStat summaryStat : list){
				SummaryStatView summaryStatView = new SummaryStatView();
				BeanUtils.copyProperties(summaryStat, summaryStatView);
				summaryStatView.setReg(summaryStat.getRegister());
				resultList.add(summaryStatView);
			}
		}
		return resultList;
	}

	@Override
	public SummaryStatView getSummaryStat(int time) throws TException {
		SummaryStatView summaryStatView = new SummaryStatView();
		
		
		SummaryStat summaryStat = null;
		try {
			summaryStat = summaryStatService.findSummary(time);
			
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (summaryStat == null){
			return null;
		}else{
			BeanUtils.copyProperties(summaryStat, summaryStatView);
			summaryStatView.setReg(summaryStat.getRegister());
			return summaryStatView;
		}
		
		
	}

	@Override
	public void saveSummaryStat(SummaryStatView summaryStatView)
			throws TException {

		SummaryStat summaryStat = new SummaryStat();
		try {
			BeanUtils.copyProperties(summaryStatView, summaryStat);
			summaryStat.setRegister(summaryStatView.getReg());
			summaryStatService.save(summaryStat);
			
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateSummaryStat(SummaryStatView summaryStatView)
			throws TException {

		SummaryStat summaryStat = new SummaryStat();
		try {
			BeanUtils.copyProperties(summaryStatView, summaryStat);
			summaryStat.setRegister(summaryStatView.getReg());
			summaryStatService.update(summaryStat);
			
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void updateMonitorStatById(long id, String statement)
			throws TException {

		try {
			monitorService.updateMonitorStatById(id, statement);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void saveMonitorStat(MonitorStatView monitorStatView)
			throws TException {
		MonitorStat monitorStat = new MonitorStat();
			
		try {
			BeanUtils.copyProperties(monitorStatView, monitorStat);
			monitorService.save(monitorStat);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}

	@Override
	public MonitorStatView findMonitorStat(String name, int type, int time)
			throws TException {
		MonitorStatView monitorStatView = new MonitorStatView();
		
		MonitorStat monitorStat = null;
		try {
			monitorStat = monitorService.findMonitorStat(name, type, time);
		} catch (Exception e) {
			throw new TException(e);
		}
		
		if (monitorStat == null){
			return null;
		}else{
			BeanUtils.copyProperties(monitorStat, monitorStatView);
			return monitorStatView;
		}
		
	}

	@Override
	public void updateUserStat(UserStatView userStatView) throws TException {
		
		UserStat userStat = new UserStat();
		try {
			BeanUtils.copyProperties(userStatView, userStat);

			userStatService.update(userStat);
			
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateVideoStat(VideoStatView videoStatView) throws TException {

		
		VideoStat videoStat = new VideoStat();
		try {
			BeanUtils.copyProperties(videoStatView, videoStat);

			videoStatService.update(videoStat);
			
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public RankUserDailyView getRankUserDaily(long uid, int type, int date)
			throws TException {
		RankUserDailyView rankUserDailyView = new RankUserDailyView();
		
		
		RankUserDaily rankUserDaily = null;
		try {
			rankUserDaily = rankUserDailyStatService.findRankUserDaily(uid, type, date);
			
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (rankUserDaily == null){
			return null;
		}else{
			BeanUtils.copyProperties(rankUserDaily, rankUserDailyView);
			return rankUserDailyView;
		}
		
	}

	@Override
	public void savetRankUserDaily(RankUserDailyView rankUserDailyView)
			throws TException {
		RankUserDaily rankUserDaily = new RankUserDaily();
		try {
			BeanUtils.copyProperties(rankUserDailyView, rankUserDaily);
			rankUserDailyStatService.save(rankUserDaily);
			
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void updateRankUserDaily(long id, double number) throws TException {

		try {
			rankUserDailyStatService.update(id, number);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateSummaryStatLiveMax(int num, int time) throws TException {

		try {
			summaryStatService.updateLiveMax(num, time);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public RankUserListView findRankUserList(int beginTime, int endTime,
			int type, int start, int count) throws TException {
		
		try {
			ReturnList<RankUserDaily> returnList = rankUserDailyStatService.findRankUserList(beginTime, endTime, type, start, count);
			RankUserListView rankUserListView = new RankUserListView();
			rankUserListView.rankUserList = new ArrayList<RankUserDailyView>();
			
			if (returnList != null && returnList.objects != null){
				for(RankUserDaily rankUserDaily : returnList.objects){
					RankUserDailyView rankUserDailyView = new RankUserDailyView();
					BeanUtils.copyProperties(rankUserDaily, rankUserDailyView);
					
					rankUserListView.rankUserList.add(rankUserDailyView);
				}
			}
			
			rankUserListView.count = returnList.count;
			return rankUserListView;
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void saveLog(LogView logView) throws TException {
		Log log = new Log();
		try {
			BeanUtils.copyProperties(logView, log);
			logService.save(log);
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public RobotStatDailyView findRobotStatDailyByDate(int date)
			throws TException {
		RobotStatDaily robotStatDaily = null;
		try {
			robotStatDaily = robotStatDailyService.findByDate(date);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (robotStatDaily != null){
			RobotStatDailyView robotStatDailyView = new RobotStatDailyView();
			BeanUtils.copyProperties(robotStatDaily, robotStatDailyView);
			return robotStatDailyView;
		}
		return null;
	}

	@Override
	public void saveRobotStatDaily(RobotStatDailyView robotStatDailyView)
			throws TException {

		if (robotStatDailyView != null){
			RobotStatDaily robotStatDaily = new RobotStatDaily();
			BeanUtils.copyProperties(robotStatDailyView, robotStatDaily);
			try {
				robotStatDailyService.save(robotStatDaily);
			} catch (ServiceException e) {
				throw new TException(e);
			}
		}
	}

	@Override
	public void updateRobotStatDailyByDate(int date,
			Map<String, String> updateContent) throws TException {

		try {
			robotStatDailyService.updateByDate(date, updateContent);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void saveActionLog(ActionLogView actionLogView) throws TException {

		ActionLog log = new ActionLog();
		try {
			BeanUtils.copyProperties(actionLogView, log);
			actionLogService.save(log);
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public ActiveXbStatView findActiveXbStatByUidAndTime(long uid, int time)
			throws TException {
		ActiveXbStat activeXbStat = null;
		try {
			activeXbStat = activeXbService.findByUidAndTime(uid, time);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		ActiveXbStatView activeXbStatView = new ActiveXbStatView();
		if (activeXbStat != null){
			BeanUtils.copyProperties(activeXbStat, activeXbStatView);
			return activeXbStatView;
		}else{
			return null;
		}
		
	}

	@Override
	public void saveActiveXbStat(ActiveXbStatView ActiveXbStatView)
			throws TException {

		ActiveXbStat activeXbStat = new ActiveXbStat();
		BeanUtils.copyProperties(ActiveXbStatView, activeXbStat);
		try {
			activeXbService.insert(activeXbStat);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		
	}

	@Override
	public void updateActiveXbStatById(long id, Map<String, String> updateContent)
			throws TException {

		try {
			activeXbService.updateById(id, updateContent);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void deleteUserStat(long uid) throws TException {

		try {
			userStatService.delete(uid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


	
}


