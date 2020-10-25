package com.roc.dao;

import com.roc.pojo.Comment;
import com.roc.pojo.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MessageDao extends JpaRepository<Message,Long> , JpaSpecificationExecutor<Message> {

    List<Message> findMessagesByParentMessageNull(Sort sort);

    Long countMessagesByAdminFalse();
}
