package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @RequestMapping(method=RequestMethod.GET, value ="/user/{id}")
    public User getUser(@PathVariable("id") int id){
        return new User(id,"Deepak - Get User");
    }

    @RequestMapping(method=RequestMethod.POST, value ="/user")
    public void setUser(@RequestBody User user){
        System.out.println("User : "+user.getUserId()+" "+user.getUserName());
    }

    @RequestMapping(method=RequestMethod.PUT, value ="/user/{id}")
    public void updateUser(@RequestBody User user,@PathVariable("id") int userId){
        System.out.println("Updating user with Id "+userId);
        System.out.println(user);
    }

    @RequestMapping(method=RequestMethod.DELETE, value ="/user/{id}")
    public void deleteUser(@PathVariable("id") int userId){
        System.out.println("Deleting user with Id "+userId);
    }
}
