package com.example.shopapplication.Services;

import com.example.shopapplication.Model.Chat;
import com.example.shopapplication.Model.Message;
import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Payload.Request.ChatRequest;
import com.example.shopapplication.Repositories.ChatRepository;
import com.example.shopapplication.Repositories.MessageRepository;
import com.example.shopapplication.Repositories.MobilePhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService{

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    MobilePhoneRepository mobilePhoneRepository;

    @Autowired
    MessageRepository messageRepository;

    //  write data into bd
    public ResponseEntity<?> postMessage (ChatRequest chatRequest) {

        //  Create new Message
       Message newMessage = new Message(chatRequest.getUsername(), chatRequest.getMessage());
       messageRepository.save(newMessage);

        //   Add new message to List

        //  first find Mobile  id
        MobilePhone existPhone = mobilePhoneRepository
                .findByMobileIdentifier(chatRequest.getPhoneId());

        //  take chat from Mobile
        Chat existChat = existPhone.getChat();

        //  Add message to list in chat that was founded
        existChat.getData().add(newMessage);
        chatRepository.save(existChat);

        return new ResponseEntity<ChatRequest>(chatRequest, HttpStatus.OK);
    }

    public List<Message> getChatList (String phoneId) {

        //  Find and return List of Message

        // find Mobile using phoneId
        MobilePhone existPhone = mobilePhoneRepository
                .findByMobileIdentifier(phoneId);

        return existPhone.getChat().getData();
    }

}
