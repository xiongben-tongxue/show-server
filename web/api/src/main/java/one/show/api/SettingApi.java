package one.show.api;

import java.util.Map;

import one.show.service.DeviceService;
import one.show.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingApi extends BaseApi {

	@Autowired
	private UserService userService;

	@Autowired 
	private DeviceService deviceService;
	
	
	@RequestMapping(value = "/forbidden_device")
	public Map forbiddenDevice() {
		
		try {
			boolean a = deviceService.deviceIsForbidden("1");
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return success();
	}
}