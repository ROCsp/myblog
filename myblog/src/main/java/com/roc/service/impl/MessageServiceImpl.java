package com.roc.service.impl;

import com.roc.dao.MessageDao;
import com.roc.pojo.Comment;
import com.roc.pojo.Message;
import com.roc.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Transactional
    @Override
    public Message save(Message message) {
        Long parentId = message.getParentMessage().getId();
        if (parentId != -1){
            message.setParentMessage(messageDao.getOne(parentId));
        }else{
            message.setParentMessage(null);
        }
        message.setCreateDate(new Date());
        return messageDao.save(message);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        messageDao.deleteById(id);
    }

    @Override
    public List<Message> listMessages() {
        Sort sort = Sort.by(Sort.Direction.ASC, "createDate");
        List<Message> messages = messageDao.findMessagesByParentMessageNull(sort);
        return eachMessage(messages);
    }

    /**
     * 循环每个顶级的评论节点
     * @param messages
     * @return
     */
    private List<Message> eachMessage(List<Message> messages) {
        List<Message> newMessages = new ArrayList<>();
        for (Message comment : messages){
            Message c = new Message();
            BeanUtils.copyProperties(comment,c);
            newMessages.add(c);
        }
        combineChildren(newMessages);
        return newMessages;
    }

    /**
     *
     * @param messages root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Message> messages) {
        for (Message message : messages){
            List<Message> replayMessages = message.getReplayMessages();
            for (Message reply : replayMessages){
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            message.setReplayMessages(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Message> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param message 被迭代的对象
     * @return
     */
    private void recursively(Message message) {
        tempReplys.add(message);
        if (message.getReplayMessages().size() > 0){
            List<Message> replayMessages = message.getReplayMessages();
            for (Message reply : replayMessages){
                    recursively(reply);
            }
        }
    }

    @Override
    public Long countMessages() {
        return messageDao.countMessagesByAdminFalse();
    }


}
