package com.roc.controller.admin;

import com.roc.pojo.Type;
import com.roc.service.TypeService;
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
 * 分类管理
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 查询分类
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    //@PageableDefault  设置分页参数
    //size = 10             每页10条数据
    // sort = {"id"}        根据id排序
    // direction = Sort.Direction.DESC      倒序排列
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Type> types = typeService.listType(pageable);
        model.addAttribute("page",types);
        return "admin/types";
    }

    /**
     * 跳转添加分类页面
     * @return
     */
    @GetMapping("/types/input")
    public String input(){
        return "/admin/types-input";
    }

    /**
     * 添加分类
     * @param type
     * @return
     */
    @PostMapping("/types")
    public String types(Type type, RedirectAttributes attributes){
        if (type == null || type.getTypeName() == ""){
            attributes.addFlashAttribute("message","请输入分类名称");
            return "redirect:/admin/types/input";
        }
        Type type1 = typeService.getTypeByName(type.getTypeName());
        if (type1 != null){
            attributes.addFlashAttribute("message","已有分类，不能重复添加");
            return "redirect:/admin/types/input";
        }
        Type type2 = typeService.saveType(type);
        if (type2 == null){
            attributes.addFlashAttribute("message","添加失败");
            return "redirect:/admin/types/input";
        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }

    /**
     *查询要修改的值，并在输入框显示
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/input/{id}")
    public String input(@PathVariable("id") Long id, Model model) {
        Type type = typeService.getType(id);
        model.addAttribute("typeName",type.getTypeName());
        return "admin/types-input";
    }

    /**
     *修改分类
     * @param type
     * @return
     */
    @PostMapping("/types/{id}")
    public String update(Type type,@PathVariable("id") Long id , RedirectAttributes attributes){
        if (type == null || type.getTypeName() == ""){
            attributes.addFlashAttribute("message","请输入分类名称");
            return "redirect:/admin/types/input";
        }
        Type type1 = typeService.getTypeByName(type.getTypeName());
        if (type1 != null){
            attributes.addFlashAttribute("message","已有分类");
            return "redirect:/admin/types/input";
        }
        Type type2 = typeService.updateType(id, type);
        if (type2 == null){
            attributes.addFlashAttribute("message","修改失败");
            return "redirect:/admin/types/input";
        }else {
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        typeService.deleteType(id);
        return "redirect:/admin/types";
    }
}
