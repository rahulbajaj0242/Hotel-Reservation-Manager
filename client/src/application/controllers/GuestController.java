package application.controllers;

import java.io.IOException;
import java.util.regex.Pattern;

import application.database.JdbcDA;
import application.models.GuestSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class GuestController {
	
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @FXML
    private TextField addressTF;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField fNameTF;

    @FXML
    private TextField lNameTF;

    @FXML
    private TextField phoneTF;

    @FXML
    private Button submitBtn;

    @FXML
    private TextField titleTF;

    @FXML
    void cancelButtonEvent(ActionEvent event) {

    }

    @FXML
    void submitButtonEvent(ActionEvent event) {
        String title = titleTF.getText();
        String firstName = fNameTF.getText();
        String lastName = lNameTF.getText();
        String address = addressTF.getText();
        String phone = phoneTF.getText();
        String email = emailTF.getText();

        if (validateInput(title, firstName, lastName, address, phone, email)) {
        	JdbcDA da = new JdbcDA();
            da.insertGuestRecord(title, firstName, lastName, address, phone, email);
            final Integer guestId = da.getGuestID(email);
			GuestSession.setGuestID(guestId); 
            da.close();
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/BookRoom.fxml"));
		        Parent root;
				root = loader.load();
				Stage stage = new Stage();
		        stage.setScene(new Scene(root));
		        stage.show();
		        Stage stage_guest = (Stage) submitBtn.getScene().getWindow();
		        stage_guest.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    private boolean validateInput(String title, String firstName, String lastName, String address, String phone, String email) {
        if (title.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            showAlert(AlertType.ERROR, "Missing Information", "Please fill in all fields.");
            return false;
        }

        if (!Pattern.matches(EMAIL_REGEX, email)) {
            showAlert(AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return false;
        }


        return true;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

	JdbcDA da = new JdbcDA();

}
