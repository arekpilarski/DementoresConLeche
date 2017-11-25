/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackymoon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import static java.nio.file.Files.list;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Arkadiusz
 */
public class FXMLNotesSceneController implements Initializable {

    @FXML
    private void pressAccountButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLHomePageScene.fxml"));
        Scene homePageScene = new Scene(homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        appStage.hide();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void pressTasksButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLTaskScene.fxml"));
        Scene taskScene = new Scene(homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        appStage.hide();
        appStage.setScene(taskScene);
        appStage.show();
    }

    @FXML
    private void pressEventsButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLEventsScene.fxml"));
        Scene eventsScene = new Scene(homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        appStage.hide();
        appStage.setScene(eventsScene);
        appStage.show();
    }

    @FXML
    private void pressContactsButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLContactsScene.fxml"));
        Scene contactsScene = new Scene(homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        appStage.hide();
        appStage.setScene(contactsScene);
        appStage.show();
    }

    @FXML
    private void pressManagementButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLManagementScene.fxml"));
        Scene managementScene = new Scene(homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        appStage.hide();
        appStage.setScene(managementScene);
        appStage.show();
    }

    @FXML
    private void pressNotesButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLNotesScene.fxml"));
        Scene notesScene = new Scene(homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        appStage.hide();
        appStage.setScene(notesScene);
        appStage.show();
    }

    @FXML
    public ListView<String> listOfObjects;

    @FXML
    private TextField filePath;

    @FXML
    private ImageView imageView;
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            Socket socket = new Socket("10.5.0.37", 8888);
            BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader messageInput = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            PrintWriter commandOutput = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())), true);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            commandOutput.println("GET");
            while (!messageInput.ready()) {
            };
            String serverMessage = messageInput.readLine();
            if (serverMessage.toUpperCase().equals("GET")) {
                while (!messageInput.ready()) {
                };
                serverMessage = messageInput.readLine();
                List<String> list = new ArrayList();
                if (serverMessage.toUpperCase().equals("LIST")) {
                    while (true) {
                        while (!messageInput.ready()) {
                        };
                        serverMessage = messageInput.readLine();
                        if (!serverMessage.toUpperCase().equals("ENDLIST")) {
                            System.out.println(serverMessage);

                            list.add(serverMessage);
                        } else {
                            break;
                        }
                    }

                    ObservableList<String> names = FXCollections.observableArrayList(list);  
                    listOfObjects.setItems(names);
                }

                while (!messageInput.ready()) {
                };
                serverMessage = messageInput.readLine();
                System.out.println(serverMessage);
                commandOutput.println("lol");
                while (!messageInput.ready()) {
                };
                serverMessage = messageInput.readLine();
                if (serverMessage.toUpperCase().equals("EXISTS")) {
                    int len = dataInputStream.readInt();
                    byte[] data = new byte[len];
                    dataInputStream.readFully(data);

                    //FileOutputStream fileToStore = new FileOutputStream(System.getProperty("user.dir") + "/received_" + fileName);
                    //fileToStore.write(data);
                    //fileToStore.close();
                    System.out.println("Got file!");
                } else {

                    System.out.println("File not found on server!");
                }
                commandOutput.println("QUIT");
            }
        }
        catch(Exception e)
        {
            
        }

    }
    
    @FXML
    private void fileViewerButton(ActionEvent event)
    {
        String fileName = listOfObjects.getSelectionModel().getSelectedItem();
        byte[] data;
        try
        {
            Socket socket = new Socket("10.5.0.37", 8888);
            BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader messageInput = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            PrintWriter commandOutput = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())), true);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            commandOutput.println("GET");
            while (!messageInput.ready()) {
            };
            String serverMessage = messageInput.readLine();
            if (serverMessage.toUpperCase().equals("GET")) {
                while (!messageInput.ready()) {
                };
                serverMessage = messageInput.readLine();
                if (serverMessage.toUpperCase().equals("LIST")) {
                    while (true) {
                        while (!messageInput.ready()) {
                        };
                        serverMessage = messageInput.readLine();
                        if (!serverMessage.toUpperCase().equals("ENDLIST")) {
                            System.out.println(serverMessage);

                        } else {
                            break;
                        }
                    }
                }

                while (!messageInput.ready()) {
                };
                serverMessage = messageInput.readLine();
                System.out.println(serverMessage);
                commandOutput.println(fileName);
                while (!messageInput.ready()) {
                };
                serverMessage = messageInput.readLine();
                if (serverMessage.toUpperCase().equals("EXISTS")) {
                    int len = dataInputStream.readInt();
                    data = new byte[len];
                    dataInputStream.readFully(data);

                    //FileOutputStream fileToStore = new FileOutputStream(System.getProperty("user.dir") + "/received_" + fileName);
                    //fileToStore.write(data);
                    //fileToStore.close();
                    System.out.println("Got file!");
                } else {

                    System.out.println("File not found on server!");
                }
                commandOutput.println("QUIT");
            }
        }
        catch(Exception e)
        {
            
        }
        
        
    }
    
    @FXML
    private void fileChooserButton(ActionEvent event) throws RuntimeException, IOException {
        
        Socket socket = new Socket("10.5.0.37", 8888);
        BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader messageInput = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        PrintWriter commandOutput = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

        
        
        
        FileChooser fc=new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        
        if(selectedFile!=null){
            String path = selectedFile.getAbsolutePath();
            commandOutput.println("SEND");
          while(!messageInput.ready()) {};
            String serverMessage = messageInput.readLine();
            if(serverMessage.toUpperCase().equals("SEND"))
            {
                if(selectedFile.exists())
                {
                    while(!messageInput.ready()) {};
                    serverMessage = messageInput.readLine();
                    commandOutput.println(selectedFile.getName());


                    Path addPath;
                    addPath = Paths.get(path);
                    byte[] data = Files.readAllBytes(addPath);

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
        } else {
            System.out.println("file is");
        }
         
    }

    /**
     * Initializes the controller class.
     */


}
