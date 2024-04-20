package application.models;

import java.math.BigDecimal;

public class Room {

	private Integer id;
	private String type;
	private BigDecimal rate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Room(Integer id, String type, BigDecimal rate) {
		super();
		this.id = id;
		this.type = type;
		this.rate = rate;
	}
	
	
	
	public Room(Integer id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	@Override
	public String toString() {
		return "Room [id=" + id + ", type=" + type + ", rate=" + rate + "]";
	}
	
	
	
}
