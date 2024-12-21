package com.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.exception.SlotNotAvailableException;
import com.management.ParkingSlotBookingManagement;
import com.model.ParkingSlot;
import com.model.ParkingSlotBooking;
import com.model.Vehicle;
import com.util.ApplicationUtil;

public class ParkingSlotBookingService {
	ApplicationUtil util= new ApplicationUtil();
	ParkingSlotBookingManagement vm = new ParkingSlotBookingManagement();
	ParkingSlot ps = new ParkingSlot();
	Vehicle vs = new Vehicle();
	
	public boolean add(String[] details) throws SlotNotAvailableException
	{
		boolean status=false;
		if(details.length!=0) {
			List<String> strList=util.splitRecord(details);
			List<ParkingSlotBooking> slotList = build(strList);
			status = addSlotRecords(slotList);
			
		}
		
		return status;
	}
	
	public List<ParkingSlotBooking> build(List<String> Bookingdetails)
	{
		List<ParkingSlotBooking> parkingslot = new ArrayList<>();
		for(int i=0;i<Bookingdetails.size();i++)
		{
			String[] details = Bookingdetails.get(i).split(";");
			String bookingId = "";
			ParkingSlot slot = getSlotDetails(details[0]);
			Vehicle vehicle =  getVehicleDetails(details[1]);
			LocalDateTime startTime =util.convertUtilDate(details[2]);
			LocalDateTime endTime = util.convertUtilDate(details[3]);
			
			String status = "";
			ParkingSlotBooking v = new ParkingSlotBooking(bookingId,slot,vehicle,startTime,endTime,status);
			parkingslot.add(v);
		}
		return parkingslot;
	}
	   
	public boolean addSlotRecords(List<ParkingSlotBooking> parkingslotBooking) throws SlotNotAvailableException
	{
		boolean b = vm.addSlotRecords(parkingslotBooking);
		if(b)
			return b;
		else
        	throw new SlotNotAvailableException("slot not available");
	}
	
	public boolean cancelParkingBooking(String slotId) throws SlotNotAvailableException {
	  boolean b = vm.cancelParkingBooking(slotId);
	  if(b)
		  return b;
	  else
		  throw new SlotNotAvailableException("slotId not available");
	}
	
	public boolean viewBookingStatus(String slotId){
	  return vm.viewBookingStatus(slotId);
	 }
	   
	 public Vehicle getVehicleDetails(String id)
     {
		 return vm.getVehicleDetails(id);
     }
	 public ParkingSlot getSlotDetails(String id)
     {
		 return vm.getSlotDetails(id);
     }
	 
	 public static String  generateBookingId(){
	        // Initialize the counter at program start
		 return ParkingSlotBookingManagement.initializeIdCounter();
	        
	    }
	 
	 public boolean updateBookingDetails(String slotId,String newStartTime, String newEndTime) throws SlotNotAvailableException {
		 //vm.updateBookingDetails();
		 ApplicationUtil aobj=new ApplicationUtil();
		 LocalDateTime startDate = aobj.convertUtilDate(newStartTime);
		 LocalDateTime endDate = aobj.convertUtilDate(newEndTime);
		 boolean status= vm.updateBookingDetails(slotId,startDate,endDate);
		 if(status)
		   return status;
		 else
			 throw new SlotNotAvailableException("slotId not available");
	 }

}
