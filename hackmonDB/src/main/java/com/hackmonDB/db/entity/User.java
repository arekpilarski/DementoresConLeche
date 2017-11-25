/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author MM27P
 */
@Entity
@Table(name="user_table")
public class User  implements Serializable{

    public User(){
    }

    public User( String name, String secondName, String Login,String password) {
        this.name = name;
        this.secondName = secondName;
        this.Login = Login;
        this.passw0rd = password;
    }
    public User( String name, String secondName, String Login,String password, String email) {
        this.name = name;
        this.secondName = secondName;
        this.Login = Login;
        this.passw0rd = password;
         this.email = email;
    }
    
        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="secondName")
    private String secondName;
    @Column(name = "login")
    private String Login;
     @Column(name = "password")
    private String passw0rd;
     @Column(name = "email")
    private String email;
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the secondName
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * @param secondName the secondName to set
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * @return the Login
     */
    public String getLogin() {
        return Login;
    }

    /**
     * @param Login the Login to set
     */
    public void setLogin(String Login) {
        this.Login = Login;
    }
    
    public User( String login)
    {
       this.Login = login;
    }

    /**
     * @return the passw0rd
     */
    public String getPassw0rd() {
        return passw0rd;
    }

    /**
     * @param passw0rd the passw0rd to set
     */
    public void setPassw0rd(String passw0rd) {
        this.passw0rd = passw0rd;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    
}
