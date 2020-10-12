package com.roc.service.impl;

import com.roc.NotFoundException;
import com.roc.dao.BlogDao;
import com.roc.pojo.Blog;
import com.roc.pojo.BlogQuery;
import com.roc.pojo.Type;
import com.roc.service.BlogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public Blog findById(Long id) {
        return blogDao.getOne(id);
    }

    @Override
    public Page<Blog> listBlogs(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Override
    public List<Blog> listBlogs() {
        List<Blog> blogs = blogDao.findAll();
        return blogs;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setViews(0);
        return blogDao.save(blog);
    }

    @Transactional
    @Override
    public Blog update(Long id, Blog blog) {
        Blog b = blogDao.getOne(id);
        blog.setUpdateTime(new Date());
        blog.setCreateTime(b.getCreateTime());
        blog.setUser(b.getUser());
        blog.setViews(b.getViews());
        if (b == null) {
            throw new NotFoundException("不存在该博客");
        }
        return blogDao.save(blog);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        blogDao.deleteById(id);
    }

    /**
     * 条件分页查询
     * @param pageable
     * @param blog
     * @return
     */
    @Override
    public Page<Blog> listBlogs(Pageable pageable, BlogQuery blog) {
        Specification<Blog> spec = new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        };
        return blogDao.findAll(spec,pageable);
    }
}
