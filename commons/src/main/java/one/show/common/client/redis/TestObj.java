/**
 * 
 */
package one.show.common.client.redis;

import java.io.Serializable;

/**
 * @author Haliaeetus leucocephalus 2018年1月7日 下午5:23:51
 * 
 */
public class TestObj implements Serializable{
	private int id;
	private String name;
	
	public TestObj(int id, String name){
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this.id == ((TestObj)obj).id){
			return true;
		}else{
			return false;
		}
	}

}
