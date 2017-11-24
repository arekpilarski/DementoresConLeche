/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackymoon;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
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
    private Label label;
    
    @FXML
    private JFXButton signUpButton;
    
    @FXML
    private void pressSignUpButton(ActionEvent event) throws IOException {
        Parent logInSceneParent = FXMLLoader.load(getClass().getResource("FXMLSignUpScene.fxml"));
        Scene signUpScene = new Scene (logInSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        appStage.hide();
        appStage.setScene(signUpScene);
        appStage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
