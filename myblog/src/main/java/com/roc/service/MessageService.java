package com.roc.service;

import com.roc.pojo.Message;

import java.security.MessageDigest;
import java.util.List;

public interface MessageService {


    Message save(Message message);

    void delete(Long id);

    Long countMessages();

    List<Message> listMessages();
}
