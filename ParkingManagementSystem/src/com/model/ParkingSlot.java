package com.model;

public class ParkingSlot {
	private String slotId;
	private String location;
	private boolean isAvailable;
	private double price;
	
	public ParkingSlot(String slotId, String location, boolean isAvailable, double price) {
		super();
		this.slotId = slotId;
		this.location = location;
		this.isAvailable = isAvailable;
		this.price = price;
	}
	
	public ParkingSlot() {
		super();
	}

	public String getSlotId() {
		return slotId;
	}
	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ParkingSlot [slotId=" + slotId + ", location=" + location + ", isAvailable=" + isAvailable + ", price="
				+ price + "]";
	}

}
