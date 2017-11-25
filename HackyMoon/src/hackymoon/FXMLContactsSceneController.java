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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
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
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Arkadiusz
 */
public class FXMLContactsSceneController implements Initializable {
    
    @FXML
    private ListView<String> contactsList;
     @FXML
    private void pressAccountButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLHomePageScene.fxml"));
        Scene homePageScene = new Scene (homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        appStage.hide();
        appStage.setScene(homePageScene);
        appStage.show();
    }
    
    @FXML
    private void pressTasksButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLTaskScene.fxml"));
        Scene taskScene = new Scene (homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        appStage.hide();
        appStage.setScene(taskScene);
        appStage.show();
    }
    
    @FXML
    private void pressEventsButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLEventsScene.fxml"));
        Scene eventsScene = new Scene (homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        appStage.hide();
        appStage.setScene(eventsScene);
        appStage.show();
    }
    
    @FXML
    private void pressContactsButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLContactsScene.fxml"));
        Scene contactsScene = new Scene (homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        appStage.hide();
        appStage.setScene(contactsScene);
        appStage.show();
    }
    
    @FXML
    private void pressManagementButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLManagementScene.fxml"));
        Scene managementScene = new Scene (homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        appStage.hide();
        appStage.setScene(managementScene);
        appStage.show();
    }
    
    @FXML
    private void pressNotesButton(ActionEvent event) throws IOException {
        Parent homePageSceneParent = FXMLLoader.load(getClass().getResource("FXMLNotesScene.fxml"));
        Scene notesScene = new Scene (homePageSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        appStage.hide();
        appStage.setScene(notesScene);
        appStage.show();
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("10.5.0.45", 8888);
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
            
            List<String> list = new ArrayList();
            commandOutput.println("GETUSERS");
            while (!messageInput.ready()) {
            };
            String serverMessage = messageInput.readLine();
            if (serverMessage.toUpperCase().equals("GETUSERS")) {
                while (!messageInput.ready()) {
                };
                serverMessage = messageInput.readLine();
                if ("STARTGETUSERS".equals(serverMessage)){
                    while(true){
                        String complexData = "";
                        while (!messageInput.ready()) {
                        };
                        serverMessage = messageInput.readLine();
                        if(serverMessage.equals("ENDGETUSERS"))
                            break;
                        complexData+=serverMessage + " ";
                        while (!messageInput.ready()) {
                        };
                        serverMessage = messageInput.readLine();
                        complexData+=serverMessage + " ";
                        while (!messageInput.ready()) {
                        };
                        serverMessage = messageInput.readLine();
                        complexData+=serverMessage + " ";
                        while (!messageInput.ready()) {
                        };
                        serverMessage = messageInput.readLine();
                        complexData+=serverMessage + " ";
                        
                        list.add(complexData);
                    }
                }

                ObservableList<String> names = FXCollections.observableArrayList(list);
                    contactsList.setItems(names);
              
                
            }
            commandOutput.println("QUIT");
        } catch (Exception e) {

        }
        
    } 
    
}
