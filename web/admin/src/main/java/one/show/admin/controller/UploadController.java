/**
 * 
 */
package one.show.admin.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import one.show.admin.view.Result;
import one.show.common.Loader;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Andy on 18/4/13.
 *
 */

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(UploadController.class);
	
	
	@RequestMapping("/upload.do")
	public ModelAndView upload(){
        ModelAndView mv = new ModelAndView();  
	    mv.setViewName("upload/upload");
	    return mv;
	}
	
	@RequestMapping("/doUpload.do")
	@ResponseBody
	public Result doUpload(@RequestParam("fileData") MultipartFile fileData){
		Result result = new Result();
		if (fileData.isEmpty()) {
			result.setStatus(2);
			return result;
		}
		
		String ext = FilenameUtils.getExtension(
				fileData.getOriginalFilename());
		
		// 获取指定文件的输入流
		InputStream content = null;
		try {
			content = fileData.getInputStream();
		} catch (IOException e) {
			result.setStatus(2);
			return result;
		}
		
		result.setMsg(fileData.getOriginalFilename());
		
		String date = new SimpleDateFormat("YMM/dd/HH/").format(new Date());
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
		String remotePath = "/system/" + date  + "/" + timestamp + "."+ext;
		File tempFile = new File("/data/temp"+remotePath);
		
		try {
			fileData.transferTo(tempFile);
			
			String url = Loader.getInstance().getProps(
					"file.xiubi.url")+remotePath;
			
			result.setHref(url);
			result.setStatus(1);		
		} catch (Exception e) {
			result.setStatus(2);		
			log.error(e.getMessage(), e);
		}
		
		
        return result;
	}
	
	

}


