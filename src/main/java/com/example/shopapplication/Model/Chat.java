package com.example.shopapplication.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chat_message",
               joinColumns = @JoinColumn(name =  "chat_id"),
               inverseJoinColumns = @JoinColumn(name = "message_id"))
    private List<Message> data = new LinkedList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "chat")
    private MobilePhone mobilePhone;



//    public String toString() {
//        return "\nmessage : " + message + " \nusername : " + username;
//    }
}
