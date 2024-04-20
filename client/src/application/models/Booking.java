package application.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {

	private Integer bookingID;
	private String guestName;
	private String roomType;
	private Integer num_rooms;
	private Integer num_days;
	private BigDecimal ratePerNight;
	private BigDecimal totalAmount;
	private LocalDateTime check_in;
	private LocalDateTime check_out;
	private LocalDate book_date;
	
	
	
	public Booking(Integer bookingID, String guestName, String roomType, Integer num_rooms, Integer num_days,
			BigDecimal ratePerNight, BigDecimal totalAmount) {
		super();
		this.bookingID = bookingID;
		this.guestName = guestName;
		this.roomType = roomType;
		this.num_rooms = num_rooms;
		this.num_days = num_days;
		this.ratePerNight = ratePerNight;
		this.totalAmount = totalAmount;
	}
	public Integer getBookingID() {
		return bookingID;
	}
	public void setBookingID(Integer bookingID) {
		this.bookingID = bookingID;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public Integer getNum_rooms() {
		return num_rooms;
	}
	public void setNum_rooms(Integer num_rooms) {
		this.num_rooms = num_rooms;
	}
	public Integer getNum_days() {
		return num_days;
	}
	public void setNum_days(Integer num_days) {
		this.num_days = num_days;
	}
	public BigDecimal getRatePerNight() {
		return ratePerNight;
	}
	public void setRatePerNight(BigDecimal ratePerNight) {
		this.ratePerNight = ratePerNight;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public LocalDateTime getCheck_in() {
		return check_in;
	}
	public void setCheck_in(LocalDateTime check_in) {
		this.check_in = check_in;
	}
	public LocalDateTime getCheck_out() {
		return check_out;
	}
	public void setCheck_out(LocalDateTime check_out) {
		this.check_out = check_out;
	}
	public LocalDate getBook_date() {
		return book_date;
	}
	public void setBook_date(LocalDate book_date) {
		this.book_date = book_date;
	}
	public Booking(Integer bookingID, String guestName, String roomType, Integer num_rooms, Integer num_days, BigDecimal ratePerNight, BigDecimal totalAmount, LocalDateTime check_in, LocalDateTime check_out,
			LocalDate book_date) {
		super();
		this.guestName = guestName;
		this.roomType = roomType;
		this.num_rooms = num_rooms;
		this.num_days = num_days;
		this.ratePerNight = ratePerNight;
		this.totalAmount = totalAmount;
		this.check_in = check_in;
		this.check_out = check_out;
		this.book_date = book_date;
	}
	
	
	
	public Booking(Integer bookingID, String guestName, String roomType, Integer num_rooms, Integer num_days) {
		super();
		this.guestName = guestName;
		this.roomType = roomType;
		this.num_rooms = num_rooms;
		this.num_days = num_days;
		this.bookingID = bookingID;
	}
	
	
	
}
