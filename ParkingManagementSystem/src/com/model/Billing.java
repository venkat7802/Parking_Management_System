package com.model;

import java.time.LocalDateTime;

public class Billing {
	private int billId;
	private String bookingId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double totalBill;
	private LocalDateTime billDate;
	
	public Billing(int billId, String bookingId, LocalDateTime startTime, LocalDateTime endTime, double totalBill,
			LocalDateTime billDate) {
		super();
		this.billId = billId;
		this.bookingId = bookingId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalBill = totalBill;
		this.billDate = billDate;
	}
	
	

	public Billing(String bookingId, LocalDateTime startTime, LocalDateTime endTime, double totalBill) {
		super();
		this.bookingId = bookingId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalBill = totalBill;
	}
	
	public Billing(String bookingId, LocalDateTime startTime, LocalDateTime endTime, double totalBill,LocalDateTime billDate) {
		super();
		this.bookingId = bookingId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalBill = totalBill;
		this.billDate=billDate;
	}



	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
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
	public double getTotalBill() {
		return totalBill;
	}
	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}
	public LocalDateTime getBillDate() {
		return billDate;
	}
	public void setBillDate(LocalDateTime billDate) {
		this.billDate = billDate;
	}

	@Override
	public String toString() {
	    return "=================================\n" +
	           "             BILL               \n" +
	           "=================================\n" +
	           String.format("Bill ID      : %-10s \n", billId) +
	           String.format("Booking ID   : %-10s \n", bookingId) +
	           String.format("Start Time   : %-20s \n", startTime) +
	           String.format("End Time     : %-20s \n", endTime) +
	           String.format("Total Bill   : %-10s \n", totalBill) +
	           String.format("Bill Date    : %-20s \n", billDate) +
	           "=================================";
	}

	
	
}
