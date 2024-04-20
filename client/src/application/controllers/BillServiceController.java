package application.controllers;

import application.models.Booking;
import application.models.GuestSession;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import application.database.JdbcDA;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class BillServiceController {

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField discountTF;

    @FXML
    private TextField idTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField numRoomTF;

    @FXML
    private TextField rateTF;

    @FXML
    private Button submitBtn;

    @FXML
    private TextField totalAmountTF;

    @FXML
    private TextField typeTF;

    @FXML
    void cancelButtonEvent(ActionEvent event) {
    	discountTF.setText("0");
    	totalAmountTF.setText(booking.getTotalAmount().toString());
    }

    @FXML
    void submitButtonEvent(ActionEvent event) {        
        JdbcDA da = new JdbcDA();
    	da.createBill(GuestSession.getBookingID(), new BigDecimal(discountTF.getText()), new BigDecimal(totalAmountTF.getText()));
    	da.close();
    	showAlert(Alert.AlertType.INFORMATION, "Bill Created", "Bill has been successfully submitted.");
    	Stage stage_login = (Stage) typeTF.getScene().getWindow();
        stage_login.close();
    }
    
    private Booking booking;
    private BigDecimal total;
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    void initialize() {
    	JdbcDA da = new JdbcDA();
        booking = da.getBooking(GuestSession.getBookingID());
        da.close();
        
        idTF.setText(String.valueOf(booking.getBookingID()));
        nameTF.setText(booking.getGuestName());
        typeTF.setText(booking.getRoomType());
        numRoomTF.setText(String.valueOf(booking.getNum_rooms()));
        rateTF.setText(booking.getRatePerNight().toString());
        discountTF.setText("0");
        totalAmountTF.setText(booking.getTotalAmount().toString());
        total = new BigDecimal(totalAmountTF.getText());
        discountTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	BigDecimal discount = new BigDecimal(discountTF.getText());
            	BigDecimal totalAmount = total;
            	
            	BigDecimal discountAmount = totalAmount.multiply(discount.divide(BigDecimal.valueOf(100)));
                BigDecimal discountedTotalAmount = totalAmount.subtract(discountAmount);
                
                totalAmountTF.setText(discountedTotalAmount.toString());
            }
        });
        
    }

}
