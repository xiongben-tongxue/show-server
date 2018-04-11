/**
 * 
 */
package one.show.stat.service.impl;

import one.show.common.ParallelHandler;
import one.show.stat.dao.VideoStatMapper;
import one.show.stat.domain.VideoStat;
import one.show.stat.service.VideoStatService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Haliaeetus leucocephalus 2018年1月6日 下午9:50:47
 *
 */

@Component
public class VideoStatServiceImpl implements VideoStatService {

	private static final Logger log = LoggerFactory.getLogger(VideoStatServiceImpl.class);

	@Autowired
	private VideoStatMapper videoStatMapper;

	static int poolSize = 100;
	static ExecutorService executor = Executors.newFixedThreadPool(poolSize);

	/* (non-Javadoc)
	 * @see one.show.stat.service.VideoStatService#save(one.show.stat.domain.VideoStat)
	 */
	@Override
	public void save(VideoStat videoStat) {
		videoStatMapper.save(videoStat);

	}

	/* (non-Javadoc)
	 * @see one.show.stat.service.VideoStatService#updateById(java.lang.String, java.lang.String)
	 */
	@Override
	public void update(VideoStat videoStat) {
		videoStatMapper.update(videoStat);

	}


	@Override
	public List<VideoStat> findByVids(List<Long> vids) {
		return new ParallelHandler<Long, VideoStat>() {

			@Override
			public VideoStat handle(Long item) {
				VideoStat videoStat = videoStatMapper.findByVid(item);
				if (videoStat == null)  {
					videoStat = new VideoStat();
					videoStat.setVid(item);
				}
				return videoStat;
			}
		}.execute(vids);
	}

	@Override
	public VideoStat findByVid(Long vid) {
		return videoStatMapper.findByVid(vid);
	}

}