/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db.repository;

import com.hackmonDB.db.entity.User;
import com.hackmonDB.db.entity.userGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author MM27P
 */
public interface  userGroupRepository extends  JpaRepository<userGroup, Long> {
    
}
