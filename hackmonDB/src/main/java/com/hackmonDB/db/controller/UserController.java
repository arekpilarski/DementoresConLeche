/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db.controller;

import com.hackmonDB.db.entity.User;
import com.hackmonDB.db.entity.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MM27P
 */
@RestController
@RequestMapping("/test")
public class UserController {
    private final UserRepository userRepository;
    
    @Autowired
    public UserController (UserRepository userRepository){
    
        this.userRepository = userRepository;
    }
    @GetMapping
    public List<User> listUsers()
    {
        return userRepository.findAll();
    }
    
    @GetMapping("/insert")
    public String insert(){
      for(int i=0;i<10;++i)
      {
          userRepository.save(new User("Name"));
      }
      return "inserted";
    }
}
