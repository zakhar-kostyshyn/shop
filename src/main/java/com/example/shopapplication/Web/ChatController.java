package com.example.shopapplication.Web;

import com.example.shopapplication.Model.Message;
import com.example.shopapplication.Payload.Request.ChatRequest;
import com.example.shopapplication.Services.ChatService;
import com.example.shopapplication.Services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/mobilePhone/chat")
@CrossOrigin("*")
public class ChatController {

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @Autowired
    ChatService chatService;

    @PostMapping("/post")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> chatPost (@Valid @RequestBody  ChatRequest chatRequest, BindingResult bindingResult) {

        ResponseEntity<?> error = mapValidationErrorService.mapValidationService(bindingResult);

        if (error != null)
            return error;

        return chatService.postMessage(chatRequest);
    }

    @GetMapping("/{phoneId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> chatGet(@PathVariable String phoneId) {

        List<Message> list = chatService.getChatList(phoneId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
