/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db.controller;

import com.hackmonDB.db.entity.Task;
import com.hackmonDB.db.repository.TaskRepository;
import java.util.Date;
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
@RequestMapping("/Task")
public class TaskController {
    private final TaskRepository taskRepository;
    
     @Autowired
    public TaskController (TaskRepository taskRepository){
    
        this.taskRepository = taskRepository;
    }
    @GetMapping
    public List<Task> listTasks()
    {
        return taskRepository.findAll();
    }
    
    @GetMapping("/insert")
    public String insert(){
      for(int i=0;i<10;++i)
      {
          taskRepository.save(new Task("Name",new Date()));
      }
      return "inserted";
    }
}
