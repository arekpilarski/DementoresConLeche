/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackymoon;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScoobyClient1 implements Runnable
{
    private Socket socket;
    
    private BufferedReader messageInput;
    
    private BufferedReader commandReader;
    
    private PrintWriter commandOutput;
    
    private DataOutputStream dataOutputStream;
    
    private DataInputStream dataInputStream;
    
    public ScoobyClient1(Socket socket, BufferedReader commandReader, BufferedReader messageInput, PrintWriter commandOutput, DataOutputStream dataOutputStream, DataInputStream dataInputStream) throws IOException
    {
        this.socket = socket;
        this.commandReader = commandReader;
        this.messageInput = messageInput;
        this.commandOutput = commandOutput;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
    }
    
    public void sendFileToServer() throws IOException
    {
        
    }
    
    public void acquireFileFromServer() throws IOException
    {
        

        
    }
    
    public BufferedReader getCommandReader()
    {
        return commandReader;
    }
    
    @Override
    public void run()
    {
        try
        {
                while(!messageInput.ready()) {};
                String serverMessage = messageInput.readLine();
                if(serverMessage.toUpperCase().equals("QUIT"))
                {
                    
                }
                else if(serverMessage.toUpperCase().equals("SEND"))
                {
                    sendFileToServer();
                }
                else if(serverMessage.toUpperCase().equals("GET"))
                {
                    acquireFileFromServer();
                }
            socket.close();
        }
        catch(Exception e)
        {
            
        }
    }


    
}
