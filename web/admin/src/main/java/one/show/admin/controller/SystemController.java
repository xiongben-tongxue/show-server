package one.show.admin.controller;

import java.util.List;

import one.show.admin.tag.PageController;
import one.show.admin.view.Result;
import one.show.common.Constant;
import one.show.common.exception.ServiceException;
import one.show.service.ManageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import one.show.manage.thrift.view.SystemConfigView;

@Controller
@RequestMapping("/system")
public class SystemController {

	private static final Logger log = LoggerFactory
			.getLogger("SystemController");
	@Autowired
	private ManageService manageService;

	@RequestMapping("/configList.do")
	public ModelAndView configList(
			@ModelAttribute(value = "pageController") PageController pageController) {

		ModelAndView mv = new ModelAndView();
		if (pageController == null) {
			pageController = new PageController();
		}
		List<SystemConfigView> systemConfigViewList = null;
		int totalRow = 0;
		try {
			systemConfigViewList = manageService.getSystemConfigList(null,
					pageController.getPageStartRow(),
					pageController.getPageSize());
			
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		if (systemConfigViewList!=null) {
			totalRow = systemConfigViewList.size();
		}
		pageController.setTotalRows(totalRow);
		pageController.update();

		mv.addObject("configList", systemConfigViewList);
		mv.setViewName("system/configlist");
		return mv;
	}
	
	
	@RequestMapping("/configAdd.do")
	public ModelAndView configAdd() {

		ModelAndView mv = new ModelAndView();
		try {
			SystemConfigView systemConfig = manageService.getSystemConfig(Constant.SYSTEM_CONFIG_ID);
			mv.addObject("config", systemConfig);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		mv.addObject("opt", "configDoAdd");
		mv.setViewName("system/configinfo");
		return mv;
	}
	
	@RequestMapping("/configEdit.do")
	public ModelAndView configEdit(String configId) {

		ModelAndView mv = new ModelAndView();
		try {
			SystemConfigView systemConfig = manageService.getSystemConfig(configId);
			mv.addObject("config", systemConfig);
			mv.addObject("configId", configId);
			mv.addObject("opt", "configDoEdit");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		mv.setViewName("system/configinfo");
		return mv;
	}
	
	@RequestMapping("/configDoAdd.do")
	public ModelAndView configDoAdd(@ModelAttribute(value = "pageController") PageController pageController,
			@ModelAttribute(value = "systemConfig") SystemConfigView systemConfigView) {

		try {
			systemConfigView.setCreateTime((int)(System.currentTimeMillis()/1000));
			manageService.addSystemConfig(systemConfigView);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return configList(pageController);
	}

	@RequestMapping("/configDoEdit.do")
	public ModelAndView configDoEdit(@ModelAttribute(value = "pageController") PageController pageController,
			@ModelAttribute(value = "systemConfig") SystemConfigView systemConfigView) {

		try {
			manageService.updateSystemConfig(systemConfigView);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return configList(pageController);
	}
	
	@RequestMapping("/configDelete.do")
	public ModelAndView configDelete(@ModelAttribute(value = "pageController") PageController pageController,String configId) {

		try {
			manageService.deleteSystemConfig(configId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return configList(pageController);
	}
	
	@RequestMapping("/checkSys.do")
	@ResponseBody
	public Result checkSys(String version) {
		Result result = new Result();
		result.setStatus(0);
		try {
			SystemConfigView systemConfigView = manageService.getSystemConfigByVersion(version);
			if (systemConfigView!=null) {
				result.setStatus(1);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
}
