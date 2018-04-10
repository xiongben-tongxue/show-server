package one.show.common.cdn;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import one.show.common.JacksonUtil;

/**
 *  {"items":[{"persistentId":"1001dae62d9211d74a2aa98cf77d2268ae06","streamname":"live-743317204174512128_1466055606.flv","ops":"wsrecord/flv/vcodec/copy/acodec/copy|saveas/YmVrZS0yMDE2MDYxNDokKHN0cmVhbW5hbWUpLSQodGltZXN0YW1wKQ==","bucket":"showone-20160614","code":1,"desc":"fileOperateActive","error":null,"keys":["showone-20160614:live-743317204174512128_1466055606--20160616143102.flv"],"urls":["http://video1.xiubi.com/live-743317204174512128_1466055606--20160616143102.flv"],"detail":[{"key":"showone-20160614:live-743317204174512128_1466055606--20160616143102.flv","url":"http://video1.xiubi.com/live-743317204174512128_1466055606--20160616143102.flv","duration":2231.74,"hash":"lqPHGrxtrri36e5yl_S4JpOcVYGY","fsize":73252513}]}],"batch_notify_id":null}
 * @author hank
 *
 */
public class RecordFinish implements Serializable{

	private List<RecordItem> items;
	
	private String batch_notify_id;
	
	public List<RecordItem> getItems() {
		return items;
	}

	public void setItems(List<RecordItem> items) {
		this.items = items;
	}

	public String getBatch_notify_id() {
		return batch_notify_id;
	}

	public void setBatch_notify_id(String batch_notify_id) {
		this.batch_notify_id = batch_notify_id;
	}

	public static void main(String[] args) {
		String str = " {\"items\":[{\"persistentId\":\"1001dae62d9211d74a2aa98cf77d2268ae06\",\"streamname\":\"live-743317204174512128_1466055606.flv\",\"ops\":\"wsrecord/flv/vcodec/copy/acodec/copy|saveas/YmVrZS0yMDE2MDYxNDokKHN0cmVhbW5hbWUpLSQodGltZXN0YW1wKQ==\",\"bucket\":\"showone-20160614\",\"code\":1,\"desc\":\"fileOperateActive\",\"error\":null,\"keys\":[\"showone-20160614:live-743317204174512128_1466055606--20160616143102.flv\"],\"urls\":[\"http://video1.xiubi.com/live-743317204174512128_1466055606--20160616143102.flv\"],\"detail\":[{\"key\":\"showone-20160614:live-743317204174512128_1466055606--20160616143102.flv\",\"url\":\"http://video1.xiubi.com/live-743317204174512128_1466055606--20160616143102.flv\",\"duration\":2231.74,\"hash\":\"lqPHGrxtrri36e5yl_S4JpOcVYGY\",\"fsize\":73252513}]}],\"batch_notify_id\":null}";
		Map map = JacksonUtil.readJsonToObject(Map.class, str);
		System.out.println(map.get("items").toString());
		List<Map> items = (List<Map>)map.get("items");
		for(Map m:items){
			System.out.println(m.get("streamname").toString().split("-")[1].split("_")[0]);
			System.out.println((List)(m.get("urls")));
		}
	}
}
