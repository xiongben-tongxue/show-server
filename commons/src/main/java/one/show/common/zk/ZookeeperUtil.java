package one.show.common.zk;

import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.WatchedEvent;

public class ZookeeperUtil extends BaseZookeeper{
	
	public static ZookeeperUtil zk = new ZookeeperUtil();

	@Override
	public void nodeChildrenChanged(WatchedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connected(WatchedEvent event) {
		// TODO Auto-generated method stub
		
	}

}
