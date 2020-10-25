package com.roc.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论类
 *
 * id           主键
 * nickName     昵称
 * email        邮箱
 * content      评论内容
 * avatar       头像
 * createDate   发布日期
 */
@Entity
@Table(name = "t_message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickName;
    private String email;
    private String content;
    private String avatar;
    private Boolean admin;
    //指定数据库生成时间类型
    //第一种：@Temporal(TemporalType.DATE)——>实体类会封装成日期“yyyy-MM-dd”的 Date类型。
    //第二种：@Temporal(TemporalType.TIME)——>实体类会封装成时间“hh-MM-ss”的 Date类型。
    //第三种：@Temporal(TemporalType.TIMESTAMP)——>实体类会封装成完整的时间“yyyy-MM-dd hh:MM:ss”的 Date类型。
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;


    //自关联评论关系
    //父评论
    @OneToMany(mappedBy = "parentMessage",cascade = CascadeType.REMOVE)
    private List<Message> replayMessages = new ArrayList<>();
    //回复评论
    @ManyToOne
    private Message parentMessage;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Message> getReplayMessages() {
        return replayMessages;
    }

    public void setReplayMessages(List<Message> replayMessages) {
        this.replayMessages = replayMessages;
    }

    public Message getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Message parentMessage) {
        this.parentMessage = parentMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", admin=" + admin +
                ", createDate=" + createDate +
                '}';
    }
}
