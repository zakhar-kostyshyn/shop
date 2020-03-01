package com.example.shopapplication.Services;

import com.example.shopapplication.Model.Chat;
import com.example.shopapplication.Model.Message;
import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Payload.Request.ChatRequest;
import com.example.shopapplication.Payload.Request.ChatUpdate;
import com.example.shopapplication.Repositories.ChatRepository;
import com.example.shopapplication.Repositories.MessageRepository;
import com.example.shopapplication.Repositories.MobilePhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

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

        return new ResponseEntity<List<Message>>(existPhone.getChat().getData(), HttpStatus.OK);
    }

}
