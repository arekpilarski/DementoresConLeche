/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoobyclient;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScoobyClient 
{
    private Socket socket;
    
    private BufferedReader messageInput;
    
    private BufferedReader commandReader;
    
    private PrintWriter commandOutput;
    
    private DataOutputStream dataOutputStream;
    
    private DataInputStream dataInputStream;
    
    public ScoobyClient() throws IOException
    {
        socket = new Socket("localhost", 8888);
        commandReader = new BufferedReader(new InputStreamReader(System.in));
        messageInput = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        commandOutput = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
    }
    
    public static void main(String[] args) throws IOException 
    {
        ScoobyClient client = new ScoobyClient();
        while(true)
        {
            
            String clientString = client.commandReader.readLine();
            client.commandOutput.println(clientString);
            while(!client.messageInput.ready()) {};
            String serverMessage = client.messageInput.readLine();
            if(clientString.toUpperCase().equals("QUIT"))
            {
                break;
            }
            else if(clientString.toUpperCase().equals("SEND"))
            {
                while(!client.messageInput.ready()) {};
                serverMessage = client.messageInput.readLine();
                System.out.println(serverMessage);
            
                String filePath = client.commandReader.readLine();
                
                File fileToSend = new File(filePath);
                
                if(fileToSend.exists())
                {
                    client.commandOutput.println(fileToSend.getName());
                    
                    
                    Path path;
                    path = Paths.get(filePath);
                    byte[] data = Files.readAllBytes(path);
                    
                    client.dataOutputStream.writeInt(data.length);
                    client.dataOutputStream.write(data, 0, data.length);
                    
                    while(!client.messageInput.ready()) {};
                    serverMessage = client.messageInput.readLine();
                    System.out.println(serverMessage);
                }
                else
                {
                    client.commandOutput.println("NOPE");
                }
            }
            else if(clientString.toUpperCase().equals("GET"))
            {
                while(!client.messageInput.ready()) {};
                serverMessage = client.messageInput.readLine();
                System.out.println(serverMessage);
                
                String fileName = client.commandReader.readLine();
                client.commandOutput.println(fileName);
                
                while(!client.messageInput.ready()) {};
                serverMessage = client.messageInput.readLine();
                if(serverMessage.toUpperCase().equals("EXISTS"))
                {
                    int len = client.dataInputStream.readInt();
                    byte[] data = new byte[len];
                    client.dataInputStream.readFully(data);
                
                    FileOutputStream fileToStore = new FileOutputStream(System.getProperty("user.dir") + "/received_" + fileName);
                    fileToStore.write(data);
                    fileToStore.close();
                    
                    System.out.println("Got file!");
                }
                else
                {
                    System.out.println("File not found on server!");
                }
            }
        }
        client.socket.close();

    }
    
}
