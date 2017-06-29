package cn.gson.crm.controller.system;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.gson.crm.common.AjaxResult;
import cn.gson.crm.common.DataGrid;
import cn.gson.crm.model.dao.ResourceDao;
import cn.gson.crm.model.domain.Resource;
import cn.gson.crm.service.ResourceService;

/**
 * 资源管理控制器
 * 
 * @author gson
 *
 */
@Controller
@RequestMapping("/system/resource")
@Transactional(readOnly = true)
public class ResourceController {

	Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	ResourceDao resourceDao;
	
	@Autowired
	ResourceService resourceService;

	@RequestMapping
	public String index() {
		return "system/resource";
	}

	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Resource> list() {
		return new DataGrid<>(resourceDao.findAll());
	}

	@RequestMapping("/parent/tree")
	@ResponseBody
	public Iterable<Resource> parentTree() {
		return resourceService.getResourceTree();
	}

	@RequestMapping("form")
	public String form(Long id, Model model) {
		if (id != null) {
			Resource resource = resourceDao.findOne(id);
			model.addAttribute("resource", JSONObject.toJSONString(resource));
			if(resource.getParent() != null){
				model.addAttribute("parentId", resource.getParent().getId());
			}
		}
		return "system/resource/form";
	}

	@RequestMapping({ "/save", "/update" })
	@Transactional(readOnly = false)
	@ResponseBody
	public Object save(@Valid Resource resource, BindingResult br) {
		if (br.hasErrors()) {
			logger.error("对象校验失败：" + br.getAllErrors());
			return new AjaxResult(false).setData(br.getAllErrors());
		} else {
			return resourceDao.save(resource);
		}
	}

	@RequestMapping("/delete")
	@Transactional(readOnly = false)
	@ResponseBody
	public AjaxResult delete(Long id) {
		try {
			resourceDao.delete(id);
		} catch (Exception e) {
			return new AjaxResult().setMessage(e.getMessage());
		}
		return new AjaxResult();
	}
}
