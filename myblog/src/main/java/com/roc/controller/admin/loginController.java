package com.roc.controller.admin;

import com.roc.pojo.User;
import com.roc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 后台登录管理
 */
@Controller
@RequestMapping("/admin")
public class loginController {

    @Autowired
    private UserService userService;

    /**
     * 跳转i登录页
     * @return
     */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    /**
     * 登录操作
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if (user == null){
            attributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }else {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }
    }

    /**
     * 登出操作
     * @param session
     * @return
     */
    @GetMapping("/loginout")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
