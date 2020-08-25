package fun.zrbac.service.impl;


import fun.zrbac.dao.MessageDao;
import fun.zrbac.entity.Message;
import fun.zrbac.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    //存放迭代找出所有子代的集合
    private List<Message> tempReplys = new ArrayList<>();

    @Override
    public List<Message> listMessage() {
        //查询父节点
        List<Message> messages = messageDao.findByParentIdNull(Long.parseLong("-1"));
        for (Message message : messages) {
            Long id = message.getId();
            String parentNickname1 = message.getNickname();
            List<Message> childMessages = messageDao.findByParentIdNotNull(id);
            //查询子留言
            combineChildren(childMessages, parentNickname1);
            message.setReplyMessages(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return messages;
    }

    private void combineChildren(List<Message> childMessages, String parentNickname1) {
        //判断是否有一级回复
        if (childMessages.size() > 0) {
            //循环找出子留言id
            for (Message childMessage : childMessages) {
                String parentNickname = childMessage.getNickname();
                childMessage.setParentNickname(parentNickname1);
                tempReplys.add(childMessage);
                Long childId = childMessage.getId();
                //查询二级以及子集回复
                recursively(childId, parentNickname);
            }
        }
    }

    private void recursively(Long childId, String parentNickname1) {
        //根据以及留言的id找到子二级留言
        List<Message> replyMessages = messageDao.findByReplyId(childId);
        if (replyMessages.size() > 0) {
            for (Message replyMessage : replyMessages) {
                String parentNickname = replyMessage.getNickname();
                replyMessage.setParentNickname(parentNickname1);
                Long replyId = replyMessage.getId();
                tempReplys.add(replyMessage);
                //迭代找出子集回复
                recursively(replyId, parentNickname);
            }
        }
    }

    @Override
    public int saveMessage(Message message) {
        message.setCreateTime(new Date());
        return messageDao.saveMessage(message);
    }

    @Override
    public void deleteMessage(Long id) {
        messageDao.deleteMessage(id);
    }
}
