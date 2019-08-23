package com.buaa.springBoot.dao;

import com.buaa.springBoot.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
        public User findByUserName(String userName);
        public User findByToken(String token);
        public User findByEmail(String email);
}
