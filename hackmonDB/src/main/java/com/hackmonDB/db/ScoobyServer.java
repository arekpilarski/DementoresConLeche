/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db;

import com.hackmonDB.db.controller.EventController;
import com.hackmonDB.db.controller.GroupController;
import com.hackmonDB.db.controller.ResourceController;
import com.hackmonDB.db.controller.TaskController;
import com.hackmonDB.db.controller.UserController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.image.BufferedImage;
import java.io.File;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class ScoobyServer {
    @Autowired
    public UserController userController;
    @Autowired
    public TaskController taskController;
    @Autowired
    public GroupController groupController;
    @Autowired
    public EventController eventController;
    @Autowired
    public ResourceController resourceController;

    final private int PORT = 8888;


   public ServerSocket serverSocket;


    ScoobyServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }
    
    ScoobyServer(UserController userController, TaskController taskController, GroupController groupController, EventController eventController,ResourceController resourceController) throws IOException {
        serverSocket = new ServerSocket(PORT);
      this.userController = userController;
      this.taskController = taskController;
      this.groupController = groupController;
      this.eventController = eventController;
      this.resourceController = resourceController;
    }


    public static void main(String args[]) {
	  Socket socket = null;
        ScoobyServer tcpServer = null;
        try {
            tcpServer = new ScoobyServer();
            System.out.println("Server started");

            while (true) {
                socket = tcpServer.serverSocket.accept();
                try {
                    SingleService singleService = new SingleService(socket,tcpServer.userController,tcpServer.taskController,tcpServer.groupController, tcpServer.eventController,tcpServer.resourceController);
                    singleService.realize();
                } catch (IOException e) {
                    socket.close();
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (tcpServer.serverSocket != null) {
                try {
                    tcpServer.serverSocket.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}

 





