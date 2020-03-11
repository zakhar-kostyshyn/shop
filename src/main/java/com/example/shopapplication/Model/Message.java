package com.example.shopapplication.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@NoArgsConstructor
@Entity
@Setter
@Getter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String message;

    private String date;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_reply_id")
    private Message parent;

    @OneToMany(mappedBy = "parent")
    private Set<Message> replies = new HashSet<>();

    public Message(String username, String message, String date) {
        this.username = username;
        this.message = message;
        this.date = date;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", replies=" + replies +
                '}';
    }


}
