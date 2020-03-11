package com.example.shopapplication.Services;

import com.example.shopapplication.Model.Chat;
import com.example.shopapplication.Model.Message;
import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Payload.Request.ChatRequest;
import com.example.shopapplication.Payload.Request.ChatUpdate;
import com.example.shopapplication.Payload.Request.ReplyPostRequest;
import com.example.shopapplication.Repositories.ChatRepository;
import com.example.shopapplication.Repositories.MessageRepository;
import com.example.shopapplication.Repositories.MobilePhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ChatService{

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    MobilePhoneRepository mobilePhoneRepository;

    @Autowired
    MessageRepository messageRepository;

    public ResponseEntity<?> postMessage (ChatRequest chatRequest) {

       Message newMessage = new Message(chatRequest.getUsername(), chatRequest.getMessage(), chatRequest.getDate());

       messageRepository.save(newMessage);

        MobilePhone existPhone = mobilePhoneRepository
                .findByMobileIdentifier(chatRequest.getPhoneId());

        Chat existChat = existPhone.getChat();

        existChat.getData().add(newMessage);
        chatRepository.save(existChat);

        return new ResponseEntity<List<Message>>(existChat.getData(), HttpStatus.OK);
    }

    public ResponseEntity<?> updateMessage (ChatUpdate chatUpdate) {

        Message updateMessage = new Message(chatUpdate.getUsername(), chatUpdate.getMessage(), chatUpdate.getDate());
        updateMessage.setId(Integer.parseInt(chatUpdate.getMessageId()));

        System.out.println(updateMessage);

        messageRepository.save(updateMessage);

        return new ResponseEntity<Message>(updateMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> getChatList (String phoneId) {

        MobilePhone existPhone = mobilePhoneRepository
                .findByMobileIdentifier(phoneId);

        List<Message> getList = existPhone.getChat().getData();

        if (getList != null)
            return new ResponseEntity<List<Message>>(existPhone.getChat().getData(), HttpStatus.OK);
        else
            return new ResponseEntity<String>("list of messages is null", HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<?> postReplyMessage (ReplyPostRequest replyPostRequest) {

        //  Create new Reply Message
        Message newReply = new Message(replyPostRequest.getUsername(), replyPostRequest.getMessage(), replyPostRequest.getDate());
        System.out.println("Reply : " + newReply);

        //  ok save newMessage in DB
        messageRepository.save(newReply);

        //  Take from DB Parent message by id
        Message parentMessage = messageRepository.findById(replyPostRequest.getMessageParentId()).get();
        System.out.println("Parent of our reply message : " + parentMessage);

        // ok add our reply to Set in parent Message
        parentMessage.getReplies().add(newReply);

        //  update parent Message
        messageRepository.save(parentMessage);

        //  and set, by updating, parentMessage id to our Reply (Reply can have only one Parent Message)
        newReply.setParent(parentMessage);

        //  update reply
        messageRepository.save(newReply);

        // test
        System.out.println("Reply in DB: " + newReply);
        System.out.println("Parent Message in DB: " + parentMessage);

        // for front-end we need to return also parent id sooo...

        List<Object> returnedState = new ArrayList<>();

        returnedState.add(newReply);
        returnedState.add(parentMessage.getId());

        return new ResponseEntity<List<Object>>(returnedState, HttpStatus.OK);
    }

    public ResponseEntity<?> getReplies (String messageId) {

        System.out.println("Id : " + messageId);

        //  find message by id
        Message existMessage = messageRepository.findById(Integer.parseInt(messageId)).get();

        // and take set of replies from this message
        Set<Message> parentSet = existMessage.getReplies();
        System.out.println("----------------");
        System.out.println(parentSet);
        System.out.println("----------------");

        return new ResponseEntity<Set<Message>> (parentSet, HttpStatus.OK);
    }
}
