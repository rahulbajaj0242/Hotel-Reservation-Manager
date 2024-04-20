package application.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.database.JdbcDA;
import application.models.GuestSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class BookRoomController {

    @FXML
    private DatePicker checkInDP;

    @FXML
    private DatePicker checkOutDP;

    @FXML
    private TextField daysTF;

    @FXML
    private Label doubleRoomLabel;

    @FXML
    private TextField doubleRoomTF;

    @FXML
    private TextField numGuestTF;

    @FXML
    private TextField numRoomTF;

    @FXML
    private TextField rateTF;

    @FXML
    private ComboBox<String> roomCB;

    @FXML
    private ComboBox<String> roomTypeCB;

    @FXML
    private Label singleRoomLabel;

    @FXML
    private TextField singleRoomTF;

    @FXML
    void submitBtn(ActionEvent event) {
    	JdbcDA da = new JdbcDA();
    	
        String roomType = roomTypeCB.getValue();
        int numRooms = Integer.parseInt(numRoomTF.getText());
        
        String checkIn = checkInDP.getValue().toString();
        String checkOut = checkOutDP.getValue().toString();
        LocalDate currentDate = LocalDate.now();
        String bookDate = currentDate.toString();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int guestId = GuestSession.getGuestID();
        String guestName = da.getGuestName(guestId);
        LocalDate checkInDate = checkInDP.getValue();
        LocalDate checkOutDate = checkOutDP.getValue();
        int numDays = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        		
        // Perform validation checks
        if (guestName.isEmpty() || roomType == null || checkIn.isEmpty() || checkOut.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all required fields.");
            return;
        }


        if (guestId == -1) {
            showAlert(Alert.AlertType.ERROR, "Guest Not Found", "Guest ID not found.");
            return;
        }
        
        if (checkOutDate.isBefore(checkInDate)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Check-out date cannot be before check-in date.");
            return;
        }
        
        if (numRooms == 1) {
            Integer room =  Integer.parseInt(roomCB.getValue());
            BigDecimal roomPrice = da.getRoomRate(room);
            totalAmount = roomPrice.multiply(BigDecimal.valueOf(numDays));
            da.bookRoom(room, guestId);
        } else if (numRooms > 1 && roomType.equals("Single + Double")) {
        	
        	List<Integer> rooms = new ArrayList<>();
        	
        	int numSingleRooms = Integer.parseInt(singleRoomTF.getText());
            int numDoubleRooms = Integer.parseInt(doubleRoomTF.getText());
            
        	ArrayList<Integer> availableDoubleRooms = da.getAvailableRooms("Double");
        	ArrayList<Integer> availableSingleRooms = da.getAvailableRooms("Single");
        	
        	for (int i = 0; i < numSingleRooms; i++) {
                rooms.add(availableSingleRooms.get(i));
            }
        	
        	for (int i = 0; i < numDoubleRooms; i++) {
                rooms.add(availableDoubleRooms.get(i));
            }
        	
            
            BigDecimal totalRoomPrice = BigDecimal.ZERO;
            for (Integer room : rooms) {
                BigDecimal roomPrice = da.getRoomRate(room);
                totalRoomPrice = totalRoomPrice.add(roomPrice);
                da.bookRoom(room, guestId);
            }
            totalAmount = totalRoomPrice.multiply(BigDecimal.valueOf(numDays));
        } else {
        	List<String> rooms = new ArrayList<>();
            // Get 'numRooms' number of rooms from the roomCB
            for (int i = 0; i < numRooms; i++) {
                rooms.add(roomCB.getItems().get(i));
            }
            BigDecimal totalRoomPrice = BigDecimal.ZERO;
            for (String room : rooms) {
            	Integer r = Integer.parseInt(room);
                BigDecimal roomPrice = da.getRoomRate(r);
                totalRoomPrice = totalRoomPrice.add(roomPrice);
                da.bookRoom(r, guestId);
            }
            totalAmount = totalRoomPrice.multiply(BigDecimal.valueOf(numDays));
        }
        

        // Insert the booking information into the Bookings table
        da.insertBooking(guestId, guestName, roomType, numRooms, numDays, checkIn, checkOut, bookDate, totalAmount);

        showAlert(Alert.AlertType.INFORMATION, "Booking Successful", "Booking has been successfully submitted.");
        da.close();
        Stage stage_login = (Stage) numRoomTF.getScene().getWindow();
        stage_login.close();
    }
    
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private ArrayList<String> singleRoomTypes = new ArrayList<>(Arrays.asList("Single", "Delux", "Penthouse"));
    private ArrayList<String> doubleRoomTypes = new ArrayList<>(Arrays.asList("2 Single Rooms", "Double", "Delux", "Penthouse"));
    private ArrayList<String> multipleDoubleTypes = new ArrayList<>(Arrays.asList("Multiple Double"));
    private ArrayList<String> singleDoubleTypes = new ArrayList<>(Arrays.asList("Single + Double"));
    
    @FXML
	void initialize() {
    	
    	singleRoomLabel.setDisable(true);
        singleRoomTF.setDisable(true);
        doubleRoomLabel.setDisable(true);
        doubleRoomTF.setDisable(true);
    	
    	numGuestTF.setOnAction(event -> {
            int numGuests = Integer.parseInt(numGuestTF.getText());
            ArrayList<String> availableRoomTypes;
            
            if (numGuests <= 2) {
                availableRoomTypes = singleRoomTypes;
            } else if (numGuests <= 4) {
                availableRoomTypes = doubleRoomTypes;
            } else {
                availableRoomTypes = new ArrayList<>();
                availableRoomTypes.addAll(multipleDoubleTypes);
                availableRoomTypes.addAll(singleDoubleTypes);
            }
            roomTypeCB.getItems().clear();
            roomTypeCB.getItems().addAll(availableRoomTypes);
        });

    	roomTypeCB.setOnAction(event -> {
            String selectedRoomType = roomTypeCB.getValue();
            if (selectedRoomType != null) {
            	JdbcDA da = new JdbcDA();
                ArrayList<Integer> availableRooms = da.getAvailableRooms(selectedRoomType);
                roomCB.getItems().clear();
                roomCB.getItems().addAll(availableRooms.stream().map(Object::toString).collect(Collectors.toList()));
                roomCB.getSelectionModel().selectFirst();
                
                if (singleDoubleTypes.contains(selectedRoomType)) {
                    singleRoomLabel.setDisable(false);
                    singleRoomTF.setDisable(false);
                    doubleRoomLabel.setDisable(false);
                    doubleRoomTF.setDisable(false);
                } 
                
                if(selectedRoomType.equals("2 Single Rooms")) {
                	numRoomTF.setText("2");
                }
                if(selectedRoomType.equals("Single") || selectedRoomType.equals("Double") || selectedRoomType.equals("Delux") || selectedRoomType.equals("Penthouse")) {
                	numRoomTF.setText("1");
                }
            }
        });
    	
    	
    	
    	
    }

}
