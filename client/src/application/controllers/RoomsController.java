package application.controllers;

import java.util.ArrayList;

import application.database.JdbcDA;
import application.models.Booking;
import application.models.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class RoomsController {

    @FXML
    private Label numRoomsLabel;

    @FXML
    private TableColumn<Room, Integer> roomIdTC;

    @FXML
    private TableColumn<Room, String> roomTypeTC;

    @FXML
    private TableView<Room> roomsTable;
    
    @FXML
    void initialize() {
        roomIdTC.setCellValueFactory(new PropertyValueFactory<>("id"));
       
        roomTypeTC.setCellValueFactory(new PropertyValueFactory<>("type"));
       

        JdbcDA da = new JdbcDA();
        ArrayList<Room> rooms = da.getAvailableRooms();
        da.close();


        roomsTable.getItems().clear();
        roomsTable.getItems().addAll(rooms);
        numRoomsLabel.setText(Integer.toString(roomsTable.getItems().size()));
    }

}
