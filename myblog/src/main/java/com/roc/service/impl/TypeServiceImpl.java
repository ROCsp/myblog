package com.roc.service.impl;

import com.roc.NotFoundException;
import com.roc.dao.BlogDao;
import com.roc.dao.TypeDao;
import com.roc.pojo.Type;
import com.roc.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("typeService")
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private BlogDao blogDao;

    @Override
    @Transactional
    public Type saveType(Type type) {
        return typeDao.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeDao.getOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.findByTypeName(name);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        List<Type> types = typeDao.findAll();
        for (Type type : types){
            Integer typeBlogsNum = findTypeBlogsNum(type.getId());
            type.setPublishedBlogNum(typeBlogsNum);
        }
        return types;
    }

    @Override
    public List<Type> listTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        List<Type> types = typeDao.findTop(pageable);
        for (Type type : types){
            Integer typeBlogsNum = findTypeBlogsNum(type.getId());
            type.setPublishedBlogNum(typeBlogsNum);
        }
        return types;
    }


    @Override
    @Transactional
    public Type updateType(Long id, Type type) {
        Type t = typeDao.getOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeDao.save(t);
    }



    @Override
    @Transactional
    public void deleteType(Long id) {
        typeDao.deleteById(id);
    }

    @Override
    public Integer findTypeBlogsNum(Long typeId) {
        return blogDao.findTypeBlogsNum(typeId);
    }
}
