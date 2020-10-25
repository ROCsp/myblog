package com.roc.controller.admin;

import com.roc.pojo.FriendLinks;
import com.roc.pojo.Picture;
import com.roc.pojo.Type;
import com.roc.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("/pictures")
    public String pictures(Model model,@PageableDefault Pageable pageable){
        Page<Picture> pictures = pictureService.listPictures(pageable);
        model.addAttribute("page",pictures);
        return "admin/pictures";
    }

    @GetMapping("/pictures/input")
    public String input(Model model){
        model.addAttribute("picture",new Picture());
        return "admin/pictures-input";
    }

    @GetMapping("/pictures/input/{id}")
    public String input(Model model,@PathVariable("id") Long id){
        Picture picture = pictureService.getPictureById(id);
        System.out.println(picture);
        model.addAttribute("picture",picture);
        return "admin/pictures-input";
    }

    @PostMapping("/pictures")
    public String pictures(Picture picture , RedirectAttributes attributes){
        if (picture == null || picture.getLink().trim().length() == 0){
            attributes.addFlashAttribute("message","请输入图片地址");
            return "redirect:/admin/pictures/input";
        }
        Picture picture2 = pictureService.save(picture);
        if (picture2 == null){
            attributes.addFlashAttribute("message","添加失败");
            return "redirect:/admin/pictures/input";
        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/pictures";
    }

    @PostMapping("/pictures/{id}")
    public String pictures(Picture picture , RedirectAttributes attributes,@PathVariable("id") Long id){
        if (picture == null || picture.getLink().trim().length() == 0){
            attributes.addFlashAttribute("message","请输入图片地址");
            return "redirect:/admin/pictures/input";
        }
        Picture picture2 = pictureService.update(id, picture);
        if (picture2 == null){
            attributes.addFlashAttribute("message","添加失败");
            return "redirect:/admin/pictures/input";
        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/pictures";
    }

    @GetMapping("/pictures/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        pictureService.delete(id);
        return "redirect:/admin/pictures";
    }
}
