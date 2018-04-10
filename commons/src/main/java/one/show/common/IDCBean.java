package one.show.common;


import java.io.Serializable;
import java.util.List;

/**
 * 扩展IDC类型字段，以区分FMS和普通CDN.<br>
 * 创建时间：2010-06-07<br>
 * 修改记录：<br>
 * 
 * 20111212 调整idc类型:
 * 
 * update `b_idc` set ptype=2 id>500 and id not in(1001,1002,1003,1004) and
 * ptype=0;
 * 
 * select * from b_idc where id>500 and id not in(1001,1002,1003,1004) and
 * ptype=0;
 * 
 */
public class IDCBean implements Serializable, Cloneable {

	private static final long serialVersionUID = 9128984548265169900L;

	private int id;

	private int netType;// 网络类型

	private int type;// IDC类型

	private int baseAllCount;// 节点的最大响应数

	private int allowCount;// allowCount是根据实际响应中，调整后的最大响应数量

	private boolean isAdjust;// 是否需要进修微调

	private int weight;// 带宽权重

	private static final int TYPE_NOR_INNER = 0;
	private static final int TYPE_NOR_OUTER = 2;
	private static final int TYPE_FMS = 1;

	private int groupId;
	private List<String> ipList;

	public boolean isNorInner() {
		return type == TYPE_NOR_INNER;
	}

	public boolean isNorOuter() {
		return type == TYPE_NOR_OUTER;
	}

	public boolean isFMS() {
		return type == TYPE_FMS;
	}

	public int getNetType() {
		return netType;
	}

	public void setNetType(int netType) {
		this.netType = netType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isAdjust() {
		return isAdjust;
	}

	public void setAdjust(boolean isAdjust) {
		this.isAdjust = isAdjust;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAllowCount() {
		return allowCount;
	}

	public int getBaseAllCount() {
		return baseAllCount;
	}

	public void setBaseAllCount(int baseAllCount) {
		this.baseAllCount = baseAllCount;
	}

	public List<String> getIpList() {
		return ipList;
	}

	public void setIpList(List<String> ipList) {
		this.ipList = ipList;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * 计算AllCount;
	 */
	public void calculate(float tinyNum) {
		allowCount = Math.round(baseAllCount * tinyNum);
	}

	/**
	 * 原基础上再次计算
	 * 
	 * @param tinyNum
	 */
	public void Addcalculate(float tinyNum) {
		allowCount = Math.round(allowCount * tinyNum);
	}

	public IDCBean clone() {
		IDCBean bean = null;
		try {
			bean = (IDCBean) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			bean = new IDCBean();
			bean.setId(id);
			bean.setNetType(netType);
			bean.setType(type);
			bean.setIpList(ipList);
			bean.setBaseAllCount(baseAllCount);
			bean.calculate(1f);
			bean.setWeight(weight);
		}
		return bean;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}

