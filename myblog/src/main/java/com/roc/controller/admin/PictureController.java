package com.roc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class PictureController {

    @GetMapping("/pictures")
    public String pictures(){
     return "admin/pictures";
    }
}
