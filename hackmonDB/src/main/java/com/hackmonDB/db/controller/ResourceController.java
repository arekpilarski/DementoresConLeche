/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db.controller;

import com.hackmonDB.db.entity.Event;
import com.hackmonDB.db.entity.Resource;
import com.hackmonDB.db.repository.EventRepository;
import com.hackmonDB.db.repository.ResourceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MM27P
 */

@Service
@RequestMapping("/Resource")
public class ResourceController {
   
    @Autowired
    private ResourceRepository resourceRepository;
    
    @GetMapping
    public List<Resource> listResource()
    {
        return resourceRepository.findAll();
    }
    
    @GetMapping
    public void Insert(String name, String extension, byte[] data)
    {
        resourceRepository.save(new Resource(name, extension,data));
    }
     @GetMapping
    public void Delete (long id)
    {
        resourceRepository.delete(id);
    }
    
}
