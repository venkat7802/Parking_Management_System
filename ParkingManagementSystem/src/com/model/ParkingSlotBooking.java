package com.model;

import java.time.LocalDateTime;


public class ParkingSlotBooking {
	//Vehicle v = new Vehicle();
	
	private String bookingId;
	private  ParkingSlot p;
	private  Vehicle v;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String status;
	
	public ParkingSlotBooking()
	{
		super();
	}
	public ParkingSlotBooking(String bookingId, ParkingSlot p, Vehicle v, LocalDateTime startTime, LocalDateTime endTime,
			String status) {
		super();
		this.bookingId = bookingId;
		this.p = p;
		this.v = v;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}
	
	
	public ParkingSlotBooking(ParkingSlot p, Vehicle v, LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.p = p;
		this.v = v;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public ParkingSlot getP() {
		return p;
	}
	public void setP(ParkingSlot p) {
		this.p = p;
	}
	public Vehicle getV() {
		return v;
	}
	public void setV(Vehicle v) {
		this.v = v;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ParkingSlotBooking [bookingId=" + bookingId + ", p=" + p + ", v=" + v + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", status=" + status + "]";
	}
	
	
	

}
