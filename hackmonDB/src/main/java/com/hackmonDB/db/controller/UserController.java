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
    
 
    
    @GetMapping
    public String insert(String login, String userName, String SecondName,String password,String email){
          userRepository.save(new User(userName,SecondName,login, password,email));
      return "inserted";
    }
    
    @GetMapping
    public void Delete(long id)
    {
        userRepository.delete(id);
    }
    
}
