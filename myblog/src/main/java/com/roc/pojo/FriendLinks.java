package com.roc.pojo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "friendlinks")
public class FriendLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String blogName;
    private String blogAddr;
    private String blogAvatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne(targetEntity = User.class)
    private User user;

    public FriendLinks() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBlogAddr() {
        return blogAddr;
    }

    public void setBlogAddr(String blogAddr) {
        this.blogAddr = blogAddr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
    }

    public String getBlogAvatar() {
        return blogAvatar;
    }

    public void setBlogAvatar(String blogAvatar) {
        this.blogAvatar = blogAvatar;
    }
}
