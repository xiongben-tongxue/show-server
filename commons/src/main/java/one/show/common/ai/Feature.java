package one.show.common.ai;

import java.util.ArrayList;
import java.util.List;

public class Feature {
	
	private int number;
	private List<Integer> ages = new ArrayList<Integer>();
	private List<Integer> rect = new ArrayList<Integer>();
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<Integer> getAges() {
		return ages;
	}
	public void setAges(List<Integer> ages) {
		this.ages = ages;
	}
	public List<Integer> getRect() {
		return rect;
	}
	public void setRect(List<Integer> rect) {
		this.rect = rect;
	}
	
	

}
