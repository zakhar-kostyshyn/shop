package com.example.shopapplication.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String message;

    private String date;

    

    public Message(String username, String message, String date) {
        this.username = username;
        this.message = message;
        this.date = date;
    }

    public String toString () {
        return "\nid : " + id + "\n username : " + username + "\n message : " + message + "\n date : " + date;
    }
}
