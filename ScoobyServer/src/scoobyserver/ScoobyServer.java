/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoobyserver;

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


public class ScoobyServer {


    final private int PORT = 8888;


    private ServerSocket serverSocket;


    ScoobyServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
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
                    SingleService singleService = new SingleService(socket);
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


class SingleService 
{


    private Socket socket;

    private BufferedReader commandInput;

    private PrintWriter messageOutput;
    
    private DataInputStream dataInputStream;
    
    private DataOutputStream dataOutputStream;

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
    
    private void addResourceToDatabase(String fileName, String fileExtension, byte[] data)
    {
        System.out.println("File to be added to database!");
        System.out.println(fileName);
        System.out.println(fileExtension);
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

            FileOutputStream fileToStore = new FileOutputStream("C:/Users/antek/Desktop/" + fileName);
            fileToStore.write(data);
            fileToStore.close();

            addResourceToDatabase(fileName, fileExtension, data);
            messageOutput.println("OK");
        }
    }
    
    private void sendFileToClient() throws IOException
    {
        messageOutput.println("GET");
        messageOutput.println("Give name of file to get: ");

        while(!commandInput.ready()) {}
        String fileName = commandInput.readLine();

        File fileToRecieve = new File("C:/Users/antek/Desktop/" + fileName);

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

