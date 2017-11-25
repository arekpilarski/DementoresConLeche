/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackmonDB.db;

import com.hackmonDB.db.SingleService;
import com.hackmonDB.db.controller.EventController;
import com.hackmonDB.db.controller.GroupController;
import com.hackmonDB.db.controller.ResourceController;
import com.hackmonDB.db.controller.TaskController;
import com.hackmonDB.db.controller.UserController;
import java.io.IOException;
import static java.lang.System.exit;
import java.net.Socket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author MM27P
 */

public class Enginee implements CommandLineRunner {
   
   /* @Autowired
    private UserController userController;
    @Autowired
    private TaskController taskController;
    @Autowired
    private GroupController groupController;
    @Autowired
    private EventController eventController;
    @Autowired
    private ResourceController resourceController;*/
    
     public static void main(String args[]) {
         SpringApplication app = new SpringApplication(Enginee.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
     }

    @Override
    public void run(String... strings) throws Exception {
          Socket socket = null;
       /* ScoobyServer tcpServer = null;
        try {
            tcpServer = new ScoobyServer(userController,taskController,groupController, eventController,resourceController);
            System.out.println("Server started");

            while (true) {
                socket = tcpServer.serverSocket.accept();
                SingleService singleService = new SingleService(socket);
                singleService.realize();
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
        }*/
        exit(0);
    }
     
}
