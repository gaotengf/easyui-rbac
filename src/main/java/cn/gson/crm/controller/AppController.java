package cn.gson.crm.controller;

import cn.gson.crm.common.AjaxResult;
import cn.gson.crm.model.dao.MemberDao;
import cn.gson.crm.model.dao.ResourceDao;
import cn.gson.crm.model.domain.Member;
import cn.gson.crm.model.domain.Resource;
import cn.gson.crm.model.domain.Role;
import cn.gson.crm.model.enums.ResourceType;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNoneEmpty;

/**
 * 系统的入口控制器，入口控制器里面的请求，理论上都受权限控制
 */
@Controller
@Transactional(readOnly = true)
public class AppController {

    @Autowired
    MemberDao memberDao;

    @Autowired
    ResourceDao resourceDao;

    /**
     * 超级管理员id
     */
    @Value("${crm.system.super-user-id}")
    Long superUserId;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    /**
     * 权限resource的js资源
     *
     * @param session
     * @return
     */
    @RequestMapping("/resource")
    public String login(HttpSession session, Model model) {
        Object resourceKey = session.getAttribute("resourceKey");
        model.addAttribute("resourceKey", JSONArray.toJSONString(resourceKey));
        return "resource";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLoin(String userName, String password, RedirectAttributes rAttributes, HttpSession session) {
        // 参数校验
        if (isEmpty(userName) || isEmpty(password)) {
            rAttributes.addFlashAttribute("error", "参数错误！");
            return "redirect:/login";
        }

        Member member = memberDao.findByUserName(userName);

        // 校验密码
        if (member == null || !member.getStatus()) {
            rAttributes.addFlashAttribute("error", "用户不存在或已被禁用！");
            return "redirect:/login";
        } else if (!member.getPassword().equals(DigestUtils.sha256Hex(password))) {
            rAttributes.addFlashAttribute("error", "用户名或密码错误！");
            return "redirect:/login";
        }

        final List<Resource> allResources;

        // 获取用户可用菜单,所有有权限的请求，所有资源key
        List<Role> roles = member.getRoles();

        List<Resource> menus = new ArrayList<Resource>();
        Set<String> urls = new HashSet<>();
        Set<String> resourceKey = new HashSet<>();

        if (superUserId == member.getId()) {
            // 超级管理员，直接获取所有权限资源
            allResources = resourceDao.findByStatus(true, new Sort(Direction.DESC, "weight"));
        } else {
            allResources = new ArrayList<>();
            // forEach 1.8jdk才支持
            roles.forEach(t -> allResources.addAll(t.getResource()));
        }

        for (Resource t : allResources) {

            // 可用菜单
            if (t.getResType().equals(ResourceType.MENU)) {
                menus.add(t);
            }

            //所有请求资源
            if (isNoneEmpty(t.getMenuUrl())) {
                urls.add(t.getMenuUrl());
            }

            String[] funUrls = t.getFunUrls().split(",");
            for (String url : funUrls) {
                if (isNoneEmpty(url)) {
                    urls.add(url);
                }
            }

            // 资源key
            resourceKey.add(t.getResKey());
        }

        // 将用户可用菜单和权限存入session
        session.setAttribute("menus", menus);
        session.setAttribute("urls", urls);
        session.setAttribute("resourceKey", resourceKey);
        session.setAttribute("s_member", member);
        // 是否是管理员
        session.setAttribute("isSuper", superUserId == member.getId());
        return "redirect:/";
    }

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/desktop")
    public String desktop() {
        return "desktop";
    }

    /**
     * 请求权限被拒绝的提醒页面
     *
     * @return
     */
    @RequestMapping("/reject")
    public String reject() {
        return "reject";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/change/password")
    public String changePassword() {
        return "password";
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public AjaxResult doChangePassword(@SessionAttribute(name = "s_member") Member member, String oldPassword, String newPassword1, String newPassword2) {
        if (isEmpty(oldPassword) || isEmpty(newPassword1) || isEmpty(newPassword2)) {
            return new AjaxResult(false).setMessage("参数错误！");
        }

        if (!member.getPassword().equals(DigestUtils.sha256Hex(oldPassword))) {
            return new AjaxResult(false).setMessage("原密码错误！");
        }

        if (!DigestUtils.sha256Hex(newPassword1).equals(DigestUtils.sha256Hex(newPassword2))) {
            return new AjaxResult(false).setMessage("新密码，两次不匹配！");
        }

        Member m = memberDao.findOne(member.getId());
        m.setPassword(DigestUtils.sha256Hex(newPassword1));
        memberDao.save(m);

        return new AjaxResult();
    }

    /**
     * 修改用户资料
     */
    @RequestMapping("/change/info")
    public String changeInfo() {
        return "info";
    }

    /**
     * 修改用户资料
     */
    @RequestMapping(value = "/change/info", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public AjaxResult doChangeInfo(Member member, HttpSession session) {
        Member smember = (Member) session.getAttribute("s_member");

        if (isEmpty(member.getRealName()) || isEmpty(member.getTelephone()) || isEmpty(member.getEmail()) || member.getGender() == null) {
            return new AjaxResult(false).setMessage("参数错误！");
        }

        smember.setRealName(member.getRealName());
        smember.setGender(member.getGender());
        smember.setTelephone(member.getTelephone());
        smember.setEmail(member.getEmail());

        memberDao.save(smember);

        session.setAttribute("s_member", smember);

        return new AjaxResult();
    }
}
