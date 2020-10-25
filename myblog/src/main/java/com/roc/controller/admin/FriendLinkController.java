package com.roc.controller.admin;

import com.roc.pojo.FriendLinks;
import com.roc.pojo.User;
import com.roc.service.FriendLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class FriendLinkController {

    @Autowired
    private FriendLinksService friendLinksService;

    /**
     * 跳转友链页面
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/friendlinks")
    public String friends(@PageableDefault(sort = "createTime",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<FriendLinks> page = friendLinksService.list(pageable);
        model.addAttribute("page",page);
        return "admin/friendlinks";
    }

    /**
     * 跳转添加好友链接页面
     * @return
     */
    @GetMapping("/friendlinks/input")
    public String input(Model model){
        model.addAttribute("friendLink",new FriendLinks());
        return "admin/friendlinks-input";
    }

    /**
     * 跳转编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/friendlinks/input/{id}")
    public String input(@PathVariable("id") Long id,Model model){
        FriendLinks friendLink = friendLinksService.getFriendLink(id);
        model.addAttribute("friendLink",friendLink);
        return "admin/friendlinks-input";
    }

    /**
     * 添加链接
     * @param friendLinks
     * @param attributes
     * @return
     */
    @PostMapping("/friendlinks")
    public String friends(FriendLinks friendLinks, RedirectAttributes attributes, HttpSession session){
        if (friendLinks == null || friendLinks.getBlogName().trim().length() == 0 || friendLinks.getBlogAddr().trim().length() == 0 ){
            attributes.addFlashAttribute("message","请输入博客名称或博客链接");
            return "redirect:/admin/friendlinks/input";
        }
        FriendLinks fl = friendLinksService.getFriendLink(friendLinks.getBlogAddr());
        if (fl != null){
            attributes.addFlashAttribute("message","该链接已存在");
            return "redirect:/admin/friendlinks/input";
        }
        User user = (User) session.getAttribute("user");
        friendLinks.setUsers(user);
        FriendLinks friendLinks1 = friendLinksService.addFriendLink(friendLinks);
        if (friendLinks1 == null){
            attributes.addFlashAttribute("message","添加失败");
            return "redirect:/admin/friendlinks/input";
        }
        attributes.addFlashAttribute("message","添加成功");
        return "redirect:/admin/friendlinks";
    }

    @PostMapping("/friendlinks/{id}")
    public String friends(@PathVariable("id") Long id,RedirectAttributes attributes,FriendLinks friendLinks){
        if (friendLinks == null || friendLinks.getBlogName().trim().length() == 0 || friendLinks.getBlogAddr().trim().length() == 0 ){
            attributes.addFlashAttribute("message","请输入博客名称或博客链接");
            return "redirect:/admin/friendlinks/input";
        }
        FriendLinks fl = friendLinksService.getFriendLink(friendLinks.getBlogAddr());
        if (fl != null){
            attributes.addFlashAttribute("message","该链接已存在");
            return "redirect:/admin/friendlinks/input";
        }
        FriendLinks friendLinks1 = friendLinksService.addFriendLink(friendLinks);
        if (friendLinks1 == null){
            attributes.addFlashAttribute("message","添加失败");
            return "redirect:/admin/friendlinks/input";
        }
        return "redirect:/admin/friendlinks";
    }
    /**
     * 删除链接
     * @param id
     * @return
     */
    @GetMapping("/friendlinks/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        friendLinksService.delete(id);
        return "redirect:/admin/friendlinks";
    }


}
