package application.controllers;

import java.io.IOException;

import application.database.JdbcDA;
import application.models.GuestSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML
    private TextField emailIdTextFiled;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button registerButton;

    @FXML
    void loginButtonEvent(ActionEvent event) {

    	Window owner = loginButton.getScene().getWindow();
    	if(emailIdTextFiled.getText().isEmpty()) {
    		showAlert(Alert.AlertType.ERROR,owner,"Form Error","Please enter the Admin ID");
    	}
    	String userId = emailIdTextFiled.getText();
    	String password = passwordTextField.getText();
    	
    	JdbcDA da = new JdbcDA();
    	boolean flag = da.validateUser(userId, password);
    	if(flag) {
    		try {
    			da.close();
    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Menu.fxml"));
    	        Parent root = loader.load();
    	        Stage stage = new Stage();
    	        stage.setScene(new Scene(root));
    	        stage.show();
    	        Stage stage_login = (Stage) loginButton.getScene().getWindow();
    	        stage_login.close();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	        // Handle the exception
    	    }
    	}
    	
    	if(flag) {
    		showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Successfull", "Thank you");
    	}
    }
    
    private static void showAlert(Alert.AlertType alt, Window win, String title, String msg) {
    	Alert alert = new Alert(alt);
    	alert.setTitle(title);
    	alert.setHeaderText(null);
    	alert.setContentText(msg);
    	alert.initOwner(win);
    	alert.showAndWait();
    }

    @FXML
    void registerButtonEvent(MouseEvent event) throws IOException {
//    	String currentDirectory = System.getProperty("user.dir");
//	    final String registerViewLocation = currentDirectory + "/src/application/views/Register.FXML";
//	    System.out.println("curr_dir " + currentDirectory + " registerViewLocation: " + registerViewLocation);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Register.FXML"));
    	Parent regLoad = loader.load();
    	
    	Stage st = new Stage();
    	st.setTitle("Registration Page");
    	Scene sc = new Scene(regLoad, 800,500);
    	st.setScene(sc);
    	st.show();
    }

}
