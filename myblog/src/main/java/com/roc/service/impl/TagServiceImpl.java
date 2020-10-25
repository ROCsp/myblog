package com.roc.service.impl;

import com.roc.NotFoundException;
import com.roc.dao.BlogDao;
import com.roc.dao.TagDao;
import com.roc.pojo.Tag;
import com.roc.pojo.Type;
import com.roc.service.BlogService;
import com.roc.service.TagService;
import javafx.scene.control.TableColumn;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("tagService")
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Transactional
    public Tag saveTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.findByTagName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return  tagDao.findAllById(convertToArray(ids));

    }

    public List<Long> convertToArray(String str){
        List<Long> list = new ArrayList<>();
        if (str != null && !"".equals(str)) {
            String[] strings = str.split(",");
            for (int i = 0; i < strings.length; i++) {
                list.add(new Long(strings[i]));
            }
        }
        return list;
    }

    /**
     * 更新标签
     * @param id
     * @param tag
     * @return
     */
    @Override
    @Transactional
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagDao.getOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,t);
        return tagDao.save(t);
    }

    /**
     * 删除标签
     * @param id
     */
    @Override
    @Transactional
    public void deleteTag(Long id) {
        tagDao.deleteById(id);
    }

    @Override
    public List<Tag> listTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagDao.findTop(pageable);
    }

}
