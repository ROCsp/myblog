package com.roc.controller;

import com.roc.pojo.Message;
import com.roc.pojo.User;
import com.roc.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/message")
    public String message() {
        return "message";
    }

    @GetMapping("/messagecomment")
    public String message(Model model){
        List<Message> messages = messageService.listMessages();
        model.addAttribute("messages",messages);
        return "message::messageList";
    }

    @PostMapping("/messages")
    public String messages(Message message , HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null){
            message.setNickName(user.getNickName());
            message.setEmail(user.getEmail());
            message.setAvatar(user.getAvatar());
            message.setAdmin(true);
        }else {
            message.setAvatar(avatar);
            message.setAdmin(false);
        }
        messageService.save(message);
        return "redirect:messagecomment";
    }

    @GetMapping("/message/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        messageService.delete(id);
        return "redirect:/message";
    }
}
