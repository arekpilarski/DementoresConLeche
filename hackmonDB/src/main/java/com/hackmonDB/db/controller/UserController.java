/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db.controller;

import com.hackmonDB.db.entity.User;
import com.hackmonDB.db.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MM27P
 */
@Service
@RequestMapping("/User")
public class UserController {
    @Autowired
    private   UserRepository userRepository;
    
 
    @GetMapping
    public List<User> listUsers()
    {
        return userRepository.findAll();
    }
    
    @GetMapping("/insert")
    public String insert(User user){
      for(int i=0;i<10;++i)
      {
          userRepository.save(user);
      }
      return "inserted";
    }
}
