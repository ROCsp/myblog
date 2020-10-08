package com.roc.controller.admin;

import com.roc.pojo.Tag;
import com.roc.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 标签管理
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 查询标签
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    //@PageableDefault  设置分页参数
    //size = 10             每页10条数据
    // sort = {"id"}        根据id排序
    // direction = Sort.Direction.DESC      倒序排列
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Tag> tags = tagService.listTag(pageable);
        model.addAttribute("page",tags);
        return "admin/tags";
    }

    /**
     * 跳转添加标签页面
     * @return
     */
    @GetMapping("/tags/input")
    public String input(){
        return "admin/tags-input";
    }

    /**
     * 添加标签
     * @param tag
     * @return
     */
    @PostMapping("/tags")
    public String tags(Tag tag, RedirectAttributes attributes){
        if (tag == null || tag.getTagName() == ""){
            attributes.addFlashAttribute("message","请输入标签名称");
            return "redirect:/admin/tags/input";
        }
        Tag tag1 = tagService.getTagByName(tag.getTagName());
        if (tag1 != null){
            attributes.addFlashAttribute("message","已有标签，不能重复添加");
            return "redirect:/admin/tags/input";
        }
        Tag tag2 = tagService.saveTag(tag);
        if (tag2 == null){
            attributes.addFlashAttribute("message","添加失败");
            return "redirect:/admin/tags/input";
        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     *查询要修改的值，并在输入框显示
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/input/{id}")
    public String input(@PathVariable("id") Long id, Model model) {
        Tag tag = tagService.getTag(id);
        model.addAttribute("tagName",tag.getTagName());
        return "admin/tags-input";
    }

    /**
     *修改标签
     * @param tag
     * @return
     */
    @PostMapping("/tags/{id}")
    public String update(Tag tag,@PathVariable("id") Long id , RedirectAttributes attributes){
        if (tag == null || tag.getTagName() == ""){
            attributes.addFlashAttribute("message","请输入标签名称");
            return "redirect:/admin/tags/input";
        }
        Tag type1 = tagService.getTagByName(tag.getTagName());
        if (type1 != null){
            attributes.addFlashAttribute("message","已有标签");
            return "redirect:/admin/tags/input";
        }
        Tag tag2 = tagService.updateTag(id, tag);
        if (tag2 == null){
            attributes.addFlashAttribute("message","修改失败");
            return "redirect:/admin/tags/input";
        }else {
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @GetMapping("/tags/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
