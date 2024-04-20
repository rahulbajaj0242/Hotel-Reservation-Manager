package application.models;

public class GuestSession {
    private static Integer guestID;
    private static String guestName;
    private static Integer bookingID;

    public static Integer getBookingID() {
		return bookingID;
	}

	public static void setBookingID(Integer bookingID) {
		GuestSession.bookingID = bookingID;
	}

	public static String getGuestName() {
		return guestName;
	}

	public static void setGuestName(String guestName) {
		GuestSession.guestName = guestName;
	}

	public static Integer getGuestID() {
        return guestID;
    }

    public static void setGuestID(Integer guestId) {
        GuestSession.guestID = guestId;
    }
    
}