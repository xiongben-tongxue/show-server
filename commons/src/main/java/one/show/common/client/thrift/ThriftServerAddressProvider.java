package one.show.common.client.thrift;

import java.net.InetSocketAddress;
import java.util.List;

public interface ThriftServerAddressProvider {
	
	 public List<InetSocketAddress> getAll();
	
	 public InetSocketAddress selector();
	
	 public void close();

}
