package com.service;
import java.time.LocalDateTime;
import com.management.BillingManagement;
import com.model.Billing;
import java.time.Duration;
public class BillingService {

    BillingManagement bm=new BillingManagement();
	
    public Billing retrieveBillDetailsforBookingId(String bookingId) {
		Billing b = bm.retrieveBillDetailsForBookingId(bookingId);
		String bId=b.getBookingId();
		LocalDateTime sDate=b.getStartTime();
		LocalDateTime eDate=b.getEndTime();
		double price=b.getTotalBill();
		double totalBill=calculateBillAmount(sDate,eDate,price);
		LocalDateTime billDate=eDate;
		b=new Billing(bId,sDate,eDate,totalBill,billDate);
		return b;
		
	}
	public static double calculateBillAmount(LocalDateTime startTime,LocalDateTime endTime, double pricePerHour) {
        // Calculate the duration in hours
		Duration duration=Duration.between(startTime, endTime);
		double totalBill=duration.toHours()*pricePerHour;
		return totalBill;
    }

	public boolean addVehicleDetails(Billing billObj) {
		return bm.addVehicleDetails(billObj);
	}
	
	public Billing retrieveBillingDetails(String bookingId) {
		return bm.retrieveBillingDetails(bookingId);
	}
	
}
