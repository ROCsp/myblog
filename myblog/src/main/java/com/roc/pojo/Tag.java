package com.roc.pojo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 文章分类
 *
 * id           主键
 * tagName     标签名称
 */
@Entity
@Table(name = "t_tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tagName;

    @ManyToMany(targetEntity = Blog.class,mappedBy = "tags")
    private Set<Blog> blogs = new HashSet<>();

    public Tag() {
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", typeName='" + tagName + '\'' +
                '}';
    }
}
