package com.buaa.springBoot.service.Impl;

import com.buaa.springBoot.dao.UserRepository;
import com.buaa.springBoot.entity.User;
import com.buaa.springBoot.service.UserInterface;
import com.buaa.springBoot.tool.Token;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class UserInterfaceImpl implements UserInterface {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User insert(User user){
        return userRepository.save(user);
    }

    @Override
    public String updateToken(User user) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(Criteria.where("userName").is(user.getUserName()));
        String collectionsName = "User";
        Update update = new Update();
        String token = Token.create();
        update.set("token", token);
        update.set("lastOperationTime",new Date().getTime());
        mongoTemplate.updateFirst(query, update, collectionsName);
        return token;
    }
//
//    @Override
//    public void delete(String id){
//        userRepository.deleteById(id);
//    }


    @Override
    public UpdateResult updateLastOperation(User user) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(Criteria.where("userName").is(user.getUserName()));
        String collectionsName = "User";
        Update update = new Update();
        update.set("lastOperationTime",new Date().getTime());
        return mongoTemplate.updateFirst(query, update, collectionsName);
    }

    @Override
    public User findByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }
}
