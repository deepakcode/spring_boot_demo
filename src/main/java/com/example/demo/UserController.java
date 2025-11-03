package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method=RequestMethod.GET, value ="/user/{id}")
    public User getUser(@PathVariable("id") long id){
        if(id==100){
            throw new ResourceNotFoundException("User with id "+id+" not found");
        }
        return userService.getUser(id);
    }

    @RequestMapping(method=RequestMethod.POST, value ="/user")
    public void setUser(@RequestBody @Valid User user){
        userService.createUser(user);
        System.out.println("User : "+user.getUserId()+" "+user.getUserName());
    }

    @RequestMapping(method=RequestMethod.PUT, value ="/user/{id}")
    public void updateUser(@RequestBody User user,@PathVariable("id") int userId){
        System.out.println("Updating user with Id "+userId);
        user.setUserId(userId);
        userService.updatUser(user);
    }

    @RequestMapping(method=RequestMethod.DELETE, value ="/user/{id}")
    public void deleteUser(@PathVariable("id") int userId){
        System.out.println("Deleting user with Id "+userId);
    }
}
