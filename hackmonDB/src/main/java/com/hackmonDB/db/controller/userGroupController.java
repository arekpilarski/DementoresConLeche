/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db.controller;

import com.hackmonDB.db.entity.User;
import com.hackmonDB.db.entity.userGroup;
import com.hackmonDB.db.repository.UserRepository;
import com.hackmonDB.db.repository.userGroupRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author MM27P
 */
@Service
@RequestMapping("/UserGroup")
public class userGroupController {
    
    
    @Autowired
    private   userGroupRepository userGroupRepository;
    
 
    @GetMapping
    public List<userGroup> listUsers()
    {
        return userGroupRepository.findAll();
    }
    
 
    
    @GetMapping
    public String insert(long userId, long groupId){
          userGroupRepository.save(new userGroup(userId, groupId));
      return "inserted";
    }
    
    @GetMapping
    public void Delete(long id)
    {
        userGroupRepository.delete(id);
    }
}
