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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MM27P
 */

@Service
@RequestMapping("/Task")
public class TaskController {
    @Autowired
    private  TaskRepository taskRepository;
    
    @GetMapping
    public List<Task> listTasks()
    {
        return taskRepository.findAll();
    }
    
    @GetMapping("/insert")
    public String insert(String name, Date date){

          taskRepository.save(new Task(name, date));
      return "inserted";
    }
    
    @GetMapping
    public void Delete(long id)
    {
        taskRepository.delete(id);
    }
    
}
