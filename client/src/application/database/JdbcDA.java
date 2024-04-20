package application.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;


import application.models.Booking;
import application.models.Room;



public class JdbcDA {
//     LOCATION = Main.class.getResource
//        ("/database/Database.db").toExternalForm();
	private static String currentDirectory = System.getProperty("user.dir");
    private static final String LOCATION = currentDirectory + "/src/database/Database.db";
    private Connection conn;

    public JdbcDA() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + LOCATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean registerUser(String user_id, String password) {

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (user_id, password) VALUES (?, ?)");
            stmt.setString(1, user_id);
            stmt.setString(2, password);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }
    
    

    public boolean validateUser(String user_id, String password) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE user_id = ? AND password = ?");
            stmt.setString(1, user_id);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            boolean isValid = rs.next();
            return isValid;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }
    
    public void bookRoom(Integer room_id, Integer guest_id) {
    	try {
    		PreparedStatement stmt = conn.prepareStatement("INSERT INTO Booked_rooms (room_id, guest_id) VALUES (?, ?)");
			stmt.setInt(1, room_id);
            stmt.setInt(2, guest_id);

            
            stmt.executeUpdate();
            

		} catch (SQLException e) {
			System.out.println("Error adding guest record: " + e.getMessage());
		}
    }
    
    public ArrayList<Booking> getBookings() {
        ArrayList<Booking> bookings = new ArrayList<>();

        try {
            
            String query = "SELECT booking_id, guest_name, room_type, num_rooms, num_days FROM Bookings";
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int bookingId = resultSet.getInt("booking_id");
                String guestName = resultSet.getString("guest_name");
                String roomType = resultSet.getString("room_type");
                int numRooms = resultSet.getInt("num_rooms");
                int numDays = resultSet.getInt("num_days");

                Booking booking = new Booking(bookingId, guestName, roomType, numRooms, numDays);
                bookings.add(booking);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }
    
    public ArrayList<Room> getAvailableRooms() {
        ArrayList<Room> rooms = new ArrayList<>();

        try {
            
        	PreparedStatement stmt = conn.prepareStatement(
                    "SELECT room_id, type FROM Rooms WHERE room_id NOT IN (SELECT room_id FROM Booked_rooms)" 
                );

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int roomId = resultSet.getInt("room_id");
                String roomType = resultSet.getString("type");
                
                Room room = new Room(roomId,roomType);
                rooms.add(room);
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }
    
    public Booking getBooking(Integer bookingId) {
    	 Booking b = null;
    	try {
            
        	PreparedStatement stmt = conn.prepareStatement(
                    "SELECT booking_id, guest_name, num_rooms, num_days, room_type, total_amount FROM Bookings WHERE booking_id = ?" 
                );
        	stmt.setInt(1, bookingId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int bookingID = resultSet.getInt("booking_id");
                String guestName = resultSet.getString("guest_name");
                int numRooms = resultSet.getInt("num_rooms");
                int numDays = resultSet.getInt("num_days");
                String roomType = resultSet.getString("room_type");
                BigDecimal totalAmount = resultSet.getBigDecimal("total_amount");
                BigDecimal rateNight = totalAmount.divide(new BigDecimal(numDays));
                
                b = new Booking(bookingID, guestName, roomType, numRooms, numDays, rateNight, totalAmount);
                
            }

            stmt.close();	
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return b;
    }
    
    public void createBill(Integer bookingID, BigDecimal discount, BigDecimal totalAmount) {
    	try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Bills (booking_id, discount, payment_amount) VALUES (?, ?, ?)");
			stmt.setInt(1, bookingID);
			stmt.setBigDecimal(2, discount);
			stmt.setBigDecimal(3, totalAmount);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e);
		}
    }
    
    
    public ArrayList<Integer> getAvailableRooms(String roomType) {
        ArrayList<Integer> availableRooms = new ArrayList<>();
        String roomTypeQuery = "";

        if (roomType.equals("Single") || roomType.equals("2 Single Rooms")) {
            roomTypeQuery = "type = 'single'";
        } else if (roomType.equals("Double") || roomType.equals("Multiple Double")) {
            roomTypeQuery = "type = 'double'";
        } else if (roomType.equals("Delux")) {
            roomTypeQuery = "type = 'delux'";
        } else if (roomType.equals("Single + Double")) {
            roomTypeQuery = "type IN ('single', 'double')";
        } else if (roomType.equals("Penthouse")) {
            roomTypeQuery = "type = 'pent house'";
        }

        try {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT room_id FROM Rooms WHERE room_id NOT IN (SELECT room_id FROM Booked_rooms) AND " + roomTypeQuery
            );

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int roomID = rs.getInt("room_id");
                availableRooms.add(roomID);
            }

            rs.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return availableRooms;
    }
    
    public String getGuestName(int guestId) {
        String guestName = null;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT first_name, last_name FROM Guests WHERE guest_id = ?");
            stmt.setInt(1, guestId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                guestName = firstName + " " + lastName;
            }

            rs.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return guestName;
    }
    
    public void insertBooking(int guestId, String guestName, String roomType, int numRooms, int numDays,
            String checkIn, String checkOut, String bookDate, BigDecimal totalAmount) {
			try {
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO Bookings (guest_id, guest_name, room_type, num_rooms, " +
				  "num_days, check_in, check_out, book_date, total_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				stmt.setInt(1, guestId);
				stmt.setString(2, guestName);
				stmt.setString(3, roomType);
				stmt.setInt(4, numRooms);
				stmt.setInt(5, numDays);
				stmt.setString(6, checkIn);
				stmt.setString(7, checkOut);
				stmt.setString(8, bookDate);
				stmt.setBigDecimal(9, totalAmount);
				
				stmt.executeUpdate();
			} catch (SQLException e) {
				handleSQLException(e);
			}
    }
    
    public BigDecimal getRoomRate(Integer room_id) {
    	double rate = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT rate FROM Rooms WHERE room_id = ?");
            stmt.setInt(1, room_id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                rate = rs.getDouble("rate");
            }

            rs.close();
            
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return new BigDecimal(rate);
    }

	
    
    public void insertGuestRecord(String title, String firstName, String lastName, String address, String phone, String email) {
    	try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Guests (title, first_name, last_name, address, phone, email) VALUES (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, title);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, address);
            stmt.setString(5, phone);
            stmt.setString(6, email);
            
            stmt.executeUpdate();
            

		} catch (SQLException e) {
			System.out.println("Error adding guest record: " + e.getMessage());
		}
    	
    	
    }
    
    public Integer getGuestID(String email) {
    	int guestId = -1; // Default value if guest ID is not found
    	try {
			PreparedStatement stmt = conn.prepareStatement("SELECT Guest_id FROM Guests WHERE email = ?");
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                guestId = rs.getInt("Guest_id");
            }
            rs.close();

		} catch (SQLException e) {
			System.out.println("Error retrieving guest ID: " + e.getMessage());
		}
    	return guestId;
    }
    
    
    
    
    

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    
    private void handleSQLException(SQLException e) {
        if (e.getErrorCode() == 5) { // SQLITE_BUSY error code
            try {
                Thread.sleep(1000); // Wait for 1 second
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            handleSQLException(e); // Retry the database operation
        } else {
            e.printStackTrace();
        }
    }
}