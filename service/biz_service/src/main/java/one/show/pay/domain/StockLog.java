package one.show.pay.domain;

/**
 * 库存日志类
 * @author Haliaeetus leucocephalus 2015/11/4
 *
 */
public class StockLog{
	private int id;
	private long uid;
	
	private int itemId;
	private String itemName;
	private int itemType;
	private double itemNumber;

	
	private int operate;
	private int actionTime;
	private int actionType;
	private String actionDesc;
	private double beforeChange;
	private double afterChange;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public double getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(double itemNumber) {
		this.itemNumber = itemNumber;
	}
	public int getOperate() {
		return operate;
	}
	public void setOperate(int operate) {
		this.operate = operate;
	}
	public int getActionTime() {
		return actionTime;
	}
	public void setActionTime(int actionTime) {
		this.actionTime = actionTime;
	}
	public String getActionDesc() {
		return actionDesc;
	}
	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}
	public double getBeforeChange() {
		return beforeChange;
	}
	public void setBeforeChange(double beforeChange) {
		this.beforeChange = beforeChange;
	}
	public double getAfterChange() {
		return afterChange;
	}
	public void setAfterChange(double afterChange) {
		this.afterChange = afterChange;
	}
	
	
	
	
	
	
}
