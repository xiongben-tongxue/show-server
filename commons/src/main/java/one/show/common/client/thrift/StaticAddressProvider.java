package one.show.common.client.thrift;

import java.net.InetSocketAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
	@author Haliaeetus leucocephalus 18-2-24
	基于zookeeper-watcher机制,获取最新地址
 */
public class StaticAddressProvider implements ThriftServerAddressProvider, InitializingBean {


	private static final Logger logger = LoggerFactory.getLogger(StaticAddressProvider.class);
    private String configPath;
    private String host;
    private int port;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public List<InetSocketAddress> getAll() {
        return null;
    }

    @Override
    public InetSocketAddress selector() {
        return new InetSocketAddress(this.host,this.port);
    }

    @Override
    public void close() {

    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
