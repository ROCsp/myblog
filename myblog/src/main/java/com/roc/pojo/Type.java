package com.roc.pojo;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 文章分类
 *
 * id           主键
 * typeName     类型名称
 */
@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeName;

    @OneToMany(targetEntity = Blog.class,mappedBy = "type")
    private Set<Blog> blogs = new HashSet<>();

    @Transient
    private Integer publishedBlogNum;

    public Type() {
    }

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getPublishedBlogNum() {
        return publishedBlogNum;
    }

    public void setPublishedBlogNum(Integer publishedBlogNum) {
        this.publishedBlogNum = publishedBlogNum;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
