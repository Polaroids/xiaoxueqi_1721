package com.buaa.springBoot.service;

import com.buaa.springBoot.entity.User;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Component;

@Component
public interface UserInterface{
    public User insert(User user);
    public User findByName(String userName);
    public User findByEmail(String email);
    public User findByToken(String token);
    public String updateToken(User user);
    public UpdateResult updateLastOperation(User user);
}
