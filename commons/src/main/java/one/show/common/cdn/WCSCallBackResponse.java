
package one.show.common.cdn;

import java.io.Serializable;
import java.util.List;

import one.show.common.JacksonUtil;
import one.show.common.TypeUtil;

/**
 * @author Haliaeetus leucocephalus  2017年1月17日 下午8:24:28
 *
 * 
 */

public class WCSCallBackResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 322807714275673959L;

	private String id;
	
	private int code;
	
	private String inputkey;
	
	private String channelname;
	
	private String inputbucket;
	
	private int inputfsize;
	
	private String desc;
	
	private int separate;
	
	private List<ResponseItem> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInputkey() {
		return inputkey;
	}

	public void setInputkey(String inputkey) {
		this.inputkey = inputkey;
	}

	public List<ResponseItem> getItems() {
		return items;
	}

	public void setItems(List<ResponseItem> items) {
		this.items = items;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getChannelname() {
		return channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	public String getInputbucket() {
		return inputbucket;
	}

	public void setInputbucket(String inputbucket) {
		this.inputbucket = inputbucket;
	}

	public int getInputfsize() {
		return inputfsize;
	}

	public void setInputfsize(int inputfsize) {
		this.inputfsize = inputfsize;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getSeparate() {
		return separate;
	}

	public void setSeparate(int separate) {
		this.separate = separate;
	}

	@Override
	public String toString() {
		return "WCSCallBackResponse [id=" + id + ", code=" + code
				+ ", inputkey=" + inputkey + ", channelname=" + channelname
				+ ", inputbucket=" + inputbucket + ", inputfsize=" + inputfsize
				+ ", desc=" + desc + ", separate=" + separate + ", items="
				+ items + "]";
	}

	public static void main(String[] args) {
		String str="{\"id\":\"10051e41d2225f6e47eea6f086fa6482336f\",\"code\":3,\"inputkey\":\"591c162573c8acfd6aa576e4.mp4\",\"channelname\":null,\"inputbucket\":\"wuli-video\",\"inputfsize\":601908,\"desc\":\"operate [avthumb/m4a/wmImage2/d3VsaS12aWRlbzo1OTFiY2QzMTZmNDFkY2MyNDMwNjU1MWEucG5n/wmGravity/TOP_RIGHT|saveas/d3VsaS12aWRlbzo1OTFjMTYyNjczYzhhY2ZkNmRhNTc2ZTQubXA0] is finish operate [vframe/jpg/offset/2/mode/2/h/200|saveas/d3VsaS12aWRlbzo1OTFjMTYyNjczYzhhY2ZkNmNhNTc2ZTQuanBn] is finish operate [vframe/jpg/offset/2|saveas/d3VsaS12aWRlbzo1OTFjMTYyNjczYzhhY2ZkNmJhNTc2ZTQuanBn] is finish \",\"separate\":0,\"items\":[{\"cmd\":\"avthumb/m4a/wmImage2/d3VsaS12aWRlbzo1OTFiY2QzMTZmNDFkY2MyNDMwNjU1MWEucG5n/wmGravity/TOP_RIGHT|saveas/d3VsaS12aWRlbzo1OTFjMTYyNjczYzhhY2ZkNmRhNTc2ZTQubXA0\",\"code\":\"3\",\"costTime\":0,\"desc\":\"fileOperateSucceed\",\"error\":null,\"fsize\":368516,\"hash\":\"FnUVErnYdQtbxsRB2y3EbXJj7Oib\",\"key\":\"wuli-video:591c162673c8acfd6da576e4.mp4\",\"url\":\"http://play.supe.tv/591c162673c8acfd6da576e4.mp4\",\"duration\":6.102,\"bit_rate\":\"483141\",\"resolution\":\"320X568\",\"detail\":[{\"fsize\":368516,\"hash\":\"FnUVErnYdQtbxsRB2y3EbXJj7Oib\",\"key\":\"wuli-video:591c162673c8acfd6da576e4.mp4\",\"url\":\"http://play.supe.tv/591c162673c8acfd6da576e4.mp4\",\"duration\":6.102,\"bit_rate\":\"483141\",\"resolution\":\"320X568\"}]},{\"cmd\":\"vframe/jpg/offset/2/mode/2/h/200|saveas/d3VsaS12aWRlbzo1OTFjMTYyNjczYzhhY2ZkNmNhNTc2ZTQuanBn\",\"code\":\"3\",\"costTime\":0,\"desc\":\"fileOperateSucceed\",\"error\":null,\"fsize\":5071,\"hash\":\"FqbHLVe4TDN7cVLxxwvm7QjH9O9i\",\"key\":\"wuli-video:591c162673c8acfd6ca576e4.jpg\",\"url\":\"http://play.supe.tv/591c162673c8acfd6ca576e4.jpg\",\"duration\":0.0,\"bit_rate\":\"0\",\"resolution\":\"113X200\",\"detail\":[{\"fsize\":5071,\"hash\":\"FqbHLVe4TDN7cVLxxwvm7QjH9O9i\",\"key\":\"wuli-video:591c162673c8acfd6ca576e4.jpg\",\"url\":\"http://play.supe.tv/591c162673c8acfd6ca576e4.jpg\",\"duration\":0.0,\"bit_rate\":\"0\",\"resolution\":\"113X200\"}]},{\"cmd\":\"vframe/jpg/offset/2|saveas/d3VsaS12aWRlbzo1OTFjMTYyNjczYzhhY2ZkNmJhNTc2ZTQuanBn\",\"code\":\"3\",\"costTime\":0,\"desc\":\"fileOperateSucceed\",\"error\":null,\"fsize\":14344,\"hash\":\"Fl3IZpMMdVH_k33kL9ChD_yYN-Pi\",\"key\":\"wuli-video:591c162673c8acfd6ba576e4.jpg\",\"url\":\"http://play.supe.tv/591c162673c8acfd6ba576e4.jpg\",\"duration\":0.0,\"bit_rate\":\"0\",\"resolution\":\"320X568\",\"detail\":[{\"fsize\":14344,\"hash\":\"Fl3IZpMMdVH_k33kL9ChD_yYN-Pi\",\"key\":\"wuli-video:591c162673c8acfd6ba576e4.jpg\",\"url\":\"http://play.supe.tv/591c162673c8acfd6ba576e4.jpg\",\"duration\":0.0,\"bit_rate\":\"0\",\"resolution\":\"320X568\"}]}]}";
		System.out.println(TypeUtil.typeToString("",JacksonUtil.readJsonToObject(WCSCallBackResponse.class, str)));
	}
}


