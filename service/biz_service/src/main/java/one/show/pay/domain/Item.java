/**
 * 
 */
package one.show.pay.domain;

/**
 * @author Haliaeetus leucocephalus 2015年11月11日 下午12:05:46
 *
 */
public class Item {
	
	private int id;
	private int type;
	private double price;
	private double number;
	private String name;
	
	
	public double getNumber() {
		return number;
	}
	public void setNumber(double number) {
		this.number = number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}


