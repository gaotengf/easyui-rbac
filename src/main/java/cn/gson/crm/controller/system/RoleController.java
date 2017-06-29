package cn.gson.crm.controller.system;

import java.util.ArrayList;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gson.crm.common.AjaxResult;
import cn.gson.crm.common.DataGrid;
import cn.gson.crm.model.dao.ResourceDao;
import cn.gson.crm.model.dao.RoleDao;
import cn.gson.crm.model.domain.Resource;
import cn.gson.crm.model.domain.Role;
import cn.gson.crm.service.ResourceService;

/**
 * 角色管理控制器
 * 
 * @author gson
 *
 */
@Controller
@RequestMapping("/system/role")
@Transactional(readOnly = true)
public class RoleController {

	Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	RoleDao roleDao;

	@Autowired
	ResourceService resourceService;

	@Autowired
	ResourceDao resourceDao;

	@RequestMapping
	public String index() {
		return "system/role";
	}

	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Role> list() {
		return new DataGrid<>(roleDao.findAll(new Sort(Direction.DESC, "id")));
	}

	@RequestMapping({ "/save", "/update" })
	@Transactional(readOnly = false)
	@ResponseBody
	public Object save(@Valid Role role, BindingResult br) {
		if (br.hasErrors()) {
			logger.error("对象校验失败：" + br.getAllErrors());
			return new AjaxResult(false).setData(br.getAllErrors());
		} else {
			return roleDao.save(role);
		}
	}

	@RequestMapping("/delete")
	@Transactional(readOnly = false)
	@ResponseBody
	public AjaxResult delete(Long id) {
		try {
			roleDao.delete(id);
		} catch (Exception e) {
			return new AjaxResult().setMessage(e.getMessage());
		}
		return new AjaxResult();
	}

	@RequestMapping("/resource/tree")
	@ResponseBody
	public Iterable<Resource> resourceTree() {
		return resourceService.getResourceTree(true);
	}

	/**
	 * 角色关联资源
	 * 
	 * @param roleId
	 * @param resources
	 * @return
	 */
	@RequestMapping("/resource/save")
	@Transactional(readOnly = false)
	@ResponseBody
	public AjaxResult resourceSave(Long roleId, Long[] resourceId) {
		Role role = roleDao.findOne(roleId);
		if (role != null && resourceId != null && resourceId.length > 0) {
			try {
				role.setResource(new ArrayList<Resource>());
				for (Long rid : resourceId) {
					// 将资源关联到角色
					role.getResource().add(resourceDao.findOne(rid));
				}
				roleDao.save(role);
				return new AjaxResult();
			} catch (Exception e) {
				logger.error("角色资源关联错误", e);
			}
		}
		return new AjaxResult(false, "关联失败！");
	}
}
