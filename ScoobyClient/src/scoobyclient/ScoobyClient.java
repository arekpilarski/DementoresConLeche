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
        socket = new Socket("10.5.0.37", 8888);
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
    
    public void sendFileToServer() throws IOException
    {
        while(!messageInput.ready()) {};
        String serverMessage = messageInput.readLine();
        System.out.println(serverMessage);

        String filePath = commandReader.readLine();

        File fileToSend = new File(filePath);

        if(fileToSend.exists())
        {
            commandOutput.println(fileToSend.getName());


            Path path;
            path = Paths.get(filePath);
            byte[] data = Files.readAllBytes(path);

            dataOutputStream.writeInt(data.length);
            dataOutputStream.write(data, 0, data.length);

            while(!messageInput.ready()) {};
            serverMessage = messageInput.readLine();
            System.out.println(serverMessage);
        }
        else
        {
            commandOutput.println("NOPE");
        }
    }
    
    public void acquireFileFromServer() throws IOException
    {
        while(!messageInput.ready()) {};
        String serverMessage = messageInput.readLine();
        System.out.println(serverMessage);

        String fileName = commandReader.readLine();
        commandOutput.println(fileName);

        while(!messageInput.ready()) {};
        serverMessage = messageInput.readLine();
        if(serverMessage.toUpperCase().equals("EXISTS"))
        {
            int len = dataInputStream.readInt();
            byte[] data = new byte[len];
            dataInputStream.readFully(data);

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
                client.sendFileToServer();
            }
            else if(clientString.toUpperCase().equals("GET"))
            {
                
            }
        }
        client.socket.close();

    }
    
}
