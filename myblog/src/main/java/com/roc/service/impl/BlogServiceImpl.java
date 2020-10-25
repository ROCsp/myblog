package com.roc.service.impl;

import com.roc.NotFoundException;
import com.roc.dao.BlogDao;
import com.roc.pojo.Blog;
import com.roc.pojo.BlogQuery;
import com.roc.pojo.Type;
import com.roc.service.BlogService;
import com.roc.utils.MarkdownUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
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

    /**
     * 根据Id获取博客并转换内容格式
     * markdown转html
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog findByIdAndConvert(Long id) {
        Blog blog = blogDao.getOne(id);
        if (blog == null){
            throw new NotFoundException("博客不存在");
        }
        String content = blog.getContent();
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String s = MarkdownUtil.markdownToHtmlExtensions(content);
        b.setContent(s);
        blogDao.updateViews(id);
        return b;
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
        blog.setUpdateTime(new Date());
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

    @Override
    public List<Blog> findTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogDao.findTop(pageable);
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogDao.findBlogsPublished(pageable);
    }

    @Override
    public Page<Blog> findAll(String query, Pageable pageable) {
        return blogDao.findBlogsPublished(query,pageable);
    }

    @Override
    public Page<Blog> findBlogsByTypeId(Long id, Pageable pageable) {
        return blogDao.findBlogsByTypeIdAndPublishedTrue(id,pageable);
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

    public Page<Blog> findBlogsByTagIdAndPublished(Long tagId,Pageable pageable){
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //关联表
                Join join = root.join("tags");
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(join.get("id"),tagId));
                list.add(cb.isTrue(root.get("published")));
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        },pageable);
    }

    public Long countBlogsByTagIdAndPublished(Long tagId){
        return blogDao.count(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(join.get("id"),tagId));
                list.add(cb.isTrue(root.get("published")));
                return  cb.and(list.toArray(new Predicate[list.size()]));
            }
        });
    }

    @Override
    public List<Blog> listBlogsSortByUpdateTime() {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                cq.where(cb.isTrue(root.get("published")));
                return null;
            }
        },sort);
    }

    @Override
    public Long countBlogsAndPublishedTrue() {
        return blogDao.count(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                cq.where(cb.isTrue(root.get("published")));
                return null;
            }
        });
    }
}
