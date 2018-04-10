package one.show.id.thrift.impl;

import one.show.id.ID;
import one.show.id.PopularID;

import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import one.show.id.thrift.iface.IDServiceProxy.Iface;
import one.show.id.thrift.view.IDView;


@Component("idServiceProxyImpl")
public class IDServiceProxyImpl implements Iface{
	

	@Override
	public long nextId() throws TException {
		// TODO Auto-generated method stub
		return ID.nextId();
	}

	/* (non-Javadoc)
	 * @see com.weipai.id.thrift.iface.IDServiceProxy.Iface#nextPid()
	 */
	@Override
	public long nextPid() throws TException {
		// TODO Auto-generated method stub
		return PopularID.getInstance().nextPid();
	}

	/* (non-Javadoc)
	 * @see com.weipai.id.thrift.iface.IDServiceProxy.Iface#nextIdAndPid()
	 */
	@Override
	public IDView nextIdAndPid() throws TException {
		IDView idView = new IDView();
		idView.setId(ID.nextId());
		idView.setPid(PopularID.getInstance().nextPid());
		
		return idView;
	}


}