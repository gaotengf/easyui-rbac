package cn.gson.crm.controller.system;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gson.crm.common.AjaxResult;
import cn.gson.crm.common.DataGrid;
import cn.gson.crm.model.dao.MemberDao;
import cn.gson.crm.model.domain.Member;

/**
 * 用户管理控制器
 * 
 * @author gson
 *
 */
@Controller
@RequestMapping("/system/member")
@Transactional(readOnly = true)
public class MemberController {

	Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	MemberDao memberDao;

	@RequestMapping
	public String index() {
		return "system/member";
	}

	@RequestMapping("/list")
	@ResponseBody
	public DataGrid<Member> list(int page, int rows, String userName) {
		PageRequest pr = new PageRequest(page - 1, rows, Direction.DESC, "id");
		Page<Member> pageData;

		if (StringUtils.isEmpty(userName)) {
			pageData = memberDao.findAll(pr);
		} else {
			pageData = memberDao.findByUserNameLike(pr, "%" + userName + "%");
		}

		return new DataGrid<>(pageData);

	}

	@RequestMapping({ "/save", "/update" })
	@Transactional(readOnly = false)
	@ResponseBody
	public Object save(@Valid Member member, BindingResult br) {
		if (br.hasErrors()) {
			logger.error("对象校验失败：" + br.getAllErrors());
			return new AjaxResult(false).setData(br.getAllErrors());
		} else {
			if(member.getId() != null){
				//不在这里更新角色和密码
				Member orig = memberDao.findOne(member.getId());
				//理论上这里一定是要找得到对象的
				if(orig != null){
					member.setPassword(orig.getPassword());
					member.setRoles(orig.getRoles());
				}
			}
			
			return memberDao.save(member);
		}
	}

	@RequestMapping("/delete")
	@Transactional(readOnly = false)
	@ResponseBody
	public AjaxResult delete(Long id) {
		try {
			memberDao.delete(id);
		} catch (Exception e) {
			return new AjaxResult(false).setMessage(e.getMessage());
		}
		return new AjaxResult();
	}
}
