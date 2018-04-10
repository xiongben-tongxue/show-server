/**

 * 
 */
package one.show.common.datasource;

import one.show.common.Constant;
import one.show.common.DESUtil;
import one.show.common.JacksonUtil;
import one.show.common.zk.BaseZookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Haliaeetus leucocephalus 2018年1月13日 下午9:01:48
 *
 */
public class BKDataSource  extends BaseZookeeper implements FactoryBean,InitializingBean {
	
	private static final Logger log = LoggerFactory.getLogger(BKDataSource.class);
	  
	
	//数据库名字 
	private String dbName = null;
	
	//r/w
	private String rw = "r";
	
	
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getRw() {
		return rw;
	}

	public void setRw(String rw) {
		this.rw = rw;
	}

	ComboPooledDataSource comboPooledDataSource = null;
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			connect();
			
			if(!exists(Constant.ZK_DS_PATH_HOME, false)){
                // 创建根节点
            	createPersistentNode(Constant.ZK_DS_PATH_HOME, new byte[0]);
            }
			
			
			String confData = getData(Constant.ZK_DS_PATH_HOME+"/"+dbName + "_" + rw, false);  
			
			DSConf dsConf = JacksonUtil.readJsonToObject(DSConf.class, confData);
			
			log.info("BKDatasource init : "+dbName+"["+rw+"] ==> "+dsConf.toString());
			
			comboPooledDataSource = new ComboPooledDataSource();
			comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
			comboPooledDataSource.setJdbcUrl("jdbc:mysql://"+dsConf.getHost()+":"+dsConf.getPort()+"/"+dbName);
			comboPooledDataSource.setUser(dsConf.getUserName());
			comboPooledDataSource.setPassword(new DESUtil().decrypt(dsConf.getPassword()));
			comboPooledDataSource.setMaxPoolSize(dsConf.getMaxPoolSize());
			comboPooledDataSource.setMinPoolSize(dsConf.getMinPoolSize());
			comboPooledDataSource.setPreferredTestQuery("select 1");
			comboPooledDataSource.setIdleConnectionTestPeriod(600);
			comboPooledDataSource.setMaxIdleTime(300);
			comboPooledDataSource.setAcquireIncrement(100);
			comboPooledDataSource.setInitialPoolSize(dsConf.getMinPoolSize());

		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}finally{
			close();
			
		}
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public Object getObject() throws Exception {
		return comboPooledDataSource;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class getObjectType() {
		return ComboPooledDataSource.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

	/* (non-Javadoc)
	 * @see one.show.common.zk.BaseZookeeper#nodeChildrenChanged(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void nodeChildrenChanged(WatchedEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see one.show.common.zk.BaseZookeeper#connected(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void connected(WatchedEvent event) {
		// TODO Auto-generated method stub
		
	}

}


