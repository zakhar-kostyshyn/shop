package com.example.shopapplication.Repositories;

import com.example.shopapplication.Model.Message;
import org.springframework.data.repository.CrudRepository;


public interface MessageRepository extends CrudRepository<Message, Integer> {

}
