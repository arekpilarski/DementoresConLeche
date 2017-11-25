/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db.controller;

import com.hackmonDB.db.entity.Event;
import com.hackmonDB.db.entity.Group;
import com.hackmonDB.db.repository.EventRepository;
import com.hackmonDB.db.repository.GroupRepository;
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
@RequestMapping("/Group")
public class GroupController {
    
    private final GroupRepository groupRepository;
    
     @Autowired
    public GroupController (GroupRepository groupRepository){
    
        this.groupRepository = groupRepository;
    }
    @GetMapping
    public List<Group> listTasks()
    {
        return groupRepository.findAll();
    }
}
