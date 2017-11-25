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
import com.hackmonDB.db.entity.Event;
import com.hackmonDB.db.entity.Group;
import com.hackmonDB.db.entity.Resource;
import com.hackmonDB.db.entity.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MM27P
 */
public class SingleService 
{


    private Socket socket;

    private BufferedReader commandInput;

    private PrintWriter messageOutput;
    
    private DataInputStream dataInputStream;
    
    private DataOutputStream dataOutputStream;
    
    
    private UserController userController;
    private TaskController taskController;
    private GroupController groupController;
    private EventController eventController;
    private ResourceController resourceController;

    public SingleService(Socket socket) throws IOException {
        this.socket = socket;
        messageOutput = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        commandInput = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }
     public SingleService(Socket socket,UserController userController, TaskController taskController, GroupController groupController, EventController eventController,ResourceController resourceController) throws IOException {
      this.userController = userController;
      this.taskController = taskController;
      this.groupController = groupController;
      this.eventController = eventController;
      this.resourceController = resourceController;
         
         this.socket = socket;
        messageOutput = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        commandInput = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }
    private void addResourceToDatabase(String fileName, String fileExtension, byte[] data)
    {
        System.out.println("File to be added to database!");
        System.out.println(fileName);
        System.out.println(fileExtension);
        resourceController.Insert(fileName, fileExtension, data);
    }
    
    private void acquireFileFromClient() throws IOException
    {
        messageOutput.println("SEND");
        messageOutput.println("Give location of file to send: ");

        while(!commandInput.ready()) {}
        String fileName = commandInput.readLine();

        if(!fileName.toUpperCase().equals("NOPE"))
        {
            String fileExtension = "";

            int i = fileName.lastIndexOf('.');
            if (i > 0) 
            {
                fileExtension = fileName.substring(i+1);
            }

            int len = dataInputStream.readInt();
            byte[] data = new byte[len];
            dataInputStream.readFully(data);

            FileOutputStream fileToStore = new FileOutputStream(System.getProperty("user.dir") + "/" + fileName);
            
            fileToStore.write(data);
            fileToStore.close();

            addResourceToDatabase(fileName, fileExtension, data);
            messageOutput.println("OK");
        }
    }
    
    private void sendFileToClient() throws IOException
    {
        messageOutput.println("GET");
        messageOutput.println("LIST");
        File dir = new File(System.getProperty("user.dir"));
        File[] dirList = dir.listFiles();
        for(File child : dirList)
        {
            messageOutput.println(child.getName());
        }
        messageOutput.println("ENDLIST");
        
        messageOutput.println("Give name of file to get: ");

        
            
        
        while(!commandInput.ready()) {}
        String fileName = commandInput.readLine();

        File fileToRecieve = new File(System.getProperty("user.dir") + "/" + fileName);

        if(fileToRecieve.exists())
        {
            messageOutput.println("EXISTS");

            Path path;
            path = Paths.get(fileToRecieve.getPath());
            byte[] data = Files.readAllBytes(path);

            dataOutputStream.writeInt(data.length);
            dataOutputStream.write(data, 0, data.length);

        }
        else
        {
            messageOutput.println("NOPE");
        }
    }
    List<User> getAllUsers()
    {
        return userController.listUsers();
    }
    List<Event> getAllEvents()
    {
        return eventController.listEvents();
    }
     List<Group> getAllGroups()
    {
        return groupController.listGroups();
    }
      List<Resource> getAllResource()
    {
         return resourceController.listResource();
    }
   
      void  addUser(String name, String secondName, String login, String password, String email)  
    {
        userController.insert(login, name, secondName, password, email);
    }
     void  addGroup(String name)  
    {
        groupController.Insert(name);
    }
      void  addEvents(String name, Date date,String description)  
    {
        eventController.Insert(name, date,description);
    }
    void addTask(String name, Date date)
    {
        taskController.insert(name, date);
    }
    
    boolean checkUserExist(String login)
    {
        List<User> list =userController.listUsers();;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getLogin().equals(login))
                return true;
        }
        return false;
    }
      
    public void realize() {
        try {

            while (true) {
                
                String str = commandInput.readLine();
                if (str.toUpperCase().equals("QUIT")) 
                {
                    break;
                }
                else if (str.toUpperCase().equals("SEND"))
                {
                    acquireFileFromClient();
                }
                else if(str.toUpperCase().equals("GET"))
                {
                    sendFileToClient();
                }
                else if(str.toUpperCase().equals("GETUSERS"))
                {
                     messageOutput.println("GETUSERS");
                    messageOutput.println("STARTGETUSERS");
                   List<User> list= getAllUsers();
                    for (int i = 0; i < list.size(); i++) {
                        messageOutput.println(Long.toString( list.get(i).getId()));
                        messageOutput.println(list.get(i).getLogin());
                        messageOutput.println(list.get(i).getName());
                        messageOutput.println(list.get(i).getSecondName());
                    }
                    messageOutput.println("ENDGETUSERS");
                }
                 else if(str.toUpperCase().equals("GETEVENTS"))
                {
                     messageOutput.println("GETEVENTS");
                    messageOutput.println("STARTGETEVENTS");
                   List<Event> list= getAllEvents();
                    for (int i = 0; i < list.size(); i++) {
                        String message = Long.toString( list.get(i).getId());
                        messageOutput.println(message);
                         message = list.get(i).getName();
                        messageOutput.println(message);
                        message = list.get(i).getDate().toString();
                        messageOutput.println(message);               
                    }
                    messageOutput.println("ENDGETEVENTS");
                }
                 else if(str.toUpperCase().equals("GETGROUPS"))
                {
                     messageOutput.println("GETGROUPS");
                    messageOutput.println("STARTGETGROUPS");
                   List<Group> list= getAllGroups();
                    for (int i = 0; i < list.size(); i++) {
                        messageOutput.println(Long.toString( list.get(i).getId()));
                        messageOutput.println(list.get(i).getName());            
                    }
                    messageOutput.println("ENDGETGROUPS");
                }
                   else if(str.toUpperCase().equals("GETRESOURCES"))
                {
                     messageOutput.println("GETRESOURCES");
                    messageOutput.println("STARTGETRESOURCES");
                   List<Resource> list= getAllResource();
                    for (int i = 0; i < list.size(); i++) {
                        messageOutput.println(Long.toString( list.get(i).getId()));
                        messageOutput.println(list.get(i).getName());     
                        messageOutput.println(list.get(i).getFormat());
                        dataOutputStream.write(list.get(i).getData());
                    }
                    messageOutput.println("ENDGETRESOURCES");
                }
                
         
                   else if(str.toUpperCase().equals("ADDEVENT"))
                {
                     messageOutput.println("ADDEVENT");                   
                     while(!commandInput.ready()) {};
                     String eventName= commandInput.readLine();
                     while(!commandInput.ready()) {};
                     String dateS= commandInput.readLine();
                     Date date = new Date(dateS);
                      while(!commandInput.ready()) {};
                     String description= commandInput.readLine();

                     addEvents(eventName, date,description);
                     messageOutput.println("OK");                  
                }
                   else if(str.toUpperCase().equals("ADDTASK"))
                {
                     messageOutput.println("ADDTASK");                   
                     while(!commandInput.ready()) {};
                     String eventName= commandInput.readLine();
                     while(!commandInput.ready()) {};
                     String dateS= commandInput.readLine();
                     Date date = new Date(dateS);
                     addTask(eventName, date);
                      messageOutput.println("OK"); 
                }
                    else if(str.toUpperCase().equals("ADDTASK"))
                {
                     messageOutput.println("ADDGROUP");                   
                     while(!commandInput.ready()) {};
                     String eventName= commandInput.readLine();         
                     addGroup(eventName);
                      messageOutput.println("OK"); 
                }
                 else if(str.toUpperCase().equals("REGISTER"))
                 {messageOutput.println("REGISTER");
                 
                     while(!commandInput.ready()) {};
                     String login= commandInput.readLine();
                     while(!commandInput.ready()) {};
                     String username= commandInput.readLine();
                     while(!commandInput.ready()) {};
                     String secondName= commandInput.readLine();  
                     while(!commandInput.ready()) {};
                     String password= commandInput.readLine(); 
                     while(!commandInput.ready()) {};
                     String email= commandInput.readLine();
                     if(checkUserExist(login))          
                     {
                         messageOutput.println("USEREXIST");
                     }
                     else
                     {
                         addUser(username,secondName, login, password,email);
                         messageOutput.println("OK");
                     }
                  }
                System.out.println("Command: " + str);
            }
            System.out.println("closing...");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
