package application.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MenuController {
	
	@FXML
    private Label bookRoomLabel;
	
	@FXML
    private Label title;
	

    @FXML
    void availRoomLabelClick(MouseEvent event) {
    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Rooms.fxml"));
	        Parent root;
			root = loader.load();
			Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }

    @FXML
    void billServiceLabelClick(MouseEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/BookingID.fxml"));
	        Parent root;
			root = loader.load();
			Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void bookRoomLabelClick(MouseEvent event) {
    	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Guest.fxml"));
	        Parent root;
			root = loader.load();
			Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    @FXML
    void currBookingLabelClick(MouseEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Bookings.fxml"));
	        Parent root;
			root = loader.load();
			Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void exitLabelClick(MouseEvent event) {
    	System.exit(0);
    }

}
