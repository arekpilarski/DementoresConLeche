/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackymoon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Arkadiusz
 */
public class FXMLLogInSceneController implements Initializable {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label messageError;
    
    //public static String login;

    @FXML
    private void pressSignUpButton(ActionEvent event) throws IOException {
        Parent logInSceneParent = FXMLLoader.load(getClass().getResource("FXMLSignUpScene.fxml"));
        Scene signUpScene = new Scene(logInSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        appStage.hide();
        appStage.setScene(signUpScene);
        appStage.show();
    }

    @FXML
    private void pressLogInButton(ActionEvent event) throws IOException {

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
            commandOutput.println("LOGIN");
            while (!messageInput.ready()) {
            };
            String serverMessage = messageInput.readLine();
            if (serverMessage.toUpperCase().equals("LOGIN")) {
                //login=usernameField.getText();
                commandOutput.println(usernameField.getText());
                commandOutput.println(passwordField.getText());
                while (!messageInput.ready()) {
                };
                serverMessage = messageInput.readLine();
                if (serverMessage.toUpperCase().equals("USEREXIST")) {
                    Parent logInSceneParent = FXMLLoader.load(getClass().getResource("FXMLHomePageScene.fxml"));
                    Scene homePageScene = new Scene(logInSceneParent);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    appStage.hide();
                    appStage.setScene(homePageScene);
                    appStage.show();
                    
                } else {
                    messageError.setText("Wrong data!");
                }
            }
            commandOutput.println("QUIT");
        } catch (IOException e) {

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
