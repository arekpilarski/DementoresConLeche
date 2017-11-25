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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Arkadiusz
 */
public class FXMLSignUpSceneController implements Initializable {

    
    
    /**
     * Initializes the controller class.
     */
    

    
    @FXML
    private void pressBackButton(ActionEvent event) throws IOException {
        Parent logInSceneParent = FXMLLoader.load(getClass().getResource("FXMLLogInScene.fxml"));
        Scene logInScene = new Scene (logInSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        appStage.hide();
        appStage.setScene(logInScene);
        appStage.show();
    }
    
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField mailField;
    @FXML
    private Label signUpMessage;
    
    @FXML
    private void signUpButton (ActionEvent event) {
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
            commandOutput.println("REGISTER");
            while (!messageInput.ready()) {
            };
            String serverMessage = messageInput.readLine();
            if (serverMessage.toUpperCase().equals("REGISTER")) {
                commandOutput.println(usernameField.getText());
                System.out.println(usernameField.getText());
                commandOutput.println(nameField.getText());
                System.out.println(nameField.getText());
                commandOutput.println(surnameField.getText());
                System.out.println(surnameField.getText());
                commandOutput.println(passwordField.getText());
                System.out.println(passwordField.getText());
                commandOutput.println(mailField.getText());
                System.out.println(mailField.getText());
                while(!messageInput.ready()) {};
                serverMessage = messageInput.readLine();
                if(serverMessage.toUpperCase().equals("USEREXIST")) {
                    System.out.println("User already exists.");
                    signUpMessage.setText("User already exists!");
                } else if (serverMessage.toUpperCase().equals("OK")) {
                    System.out.println("Signed up!");
                    signUpMessage.setText("Signed up!");
                }
                
            }
                commandOutput.println("QUIT");
            } catch (IOException e) {
                    
                    }   
        
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    }
    
}
