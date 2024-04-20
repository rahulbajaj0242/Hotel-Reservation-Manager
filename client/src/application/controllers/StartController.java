package application.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StartController {

    @FXML
    private Label title;

    @FXML
    void loginLabelClick(MouseEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Login.fxml"));
	        Parent root;
			root = loader.load();
			Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();
	        Stage stage_login = (Stage) title.getScene().getWindow();
	        stage_login.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
