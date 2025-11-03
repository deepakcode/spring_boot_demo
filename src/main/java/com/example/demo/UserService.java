package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found for id : "+id));
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updatUser(User user){
        User existingUser = userRepository.getReferenceById(user.getUserId());
        existingUser.setUserName(user.getUserName());
        return userRepository.save(existingUser);
    }
}
