/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db.repository;

import com.hackmonDB.db.entity.Event;
import com.hackmonDB.db.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author MM27P
 */
public interface EventRepository extends  JpaRepository<Event, Long>{
    
}
