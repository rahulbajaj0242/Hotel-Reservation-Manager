package application.controllers;

import java.io.IOException;
import java.util.ArrayList;

import application.database.JdbcDA;
import application.models.Booking;
import application.models.GuestSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookingIDController {

    @FXML
    private TextField bookIdLabel;

    @FXML
    private Label title;

    @FXML
    void submitBtn(ActionEvent event) {
    	int bookingId = Integer.parseInt(bookIdLabel.getText());    
    	GuestSession.setBookingID(bookingId);
        try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/BillService.fxml"));
	        Parent root = loader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();
	        Stage stage_login = (Stage) bookIdLabel.getScene().getWindow();
	        stage_login.close();
	    } catch (IOException e) {
	        e.printStackTrace();

	    }
    }

}
