package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.management.ParkingManagement;
import com.model.ParkingSlot;
import com.util.ApplicationUtil;

public class ParkingSlotService {

	ApplicationUtil util = new ApplicationUtil();
	ParkingManagement pm = new ParkingManagement();

	public boolean add(String[] details) {
		boolean status = false;
		if (details.length != 0) {
			List<String> list = util.splitRecord(details);
			List<ParkingSlot> slotList = build(list);
			status = addSlotRecords(slotList);
		}
		return status;
	}

	public List<ParkingSlot> build(List<String> Parkingdetails) {
		List<ParkingSlot> parkingslotObj = new ArrayList<>();
		for (int i = 0; i < Parkingdetails.size(); i++) {
			String[] details = Parkingdetails.get(i).split(":");
			String slotId = "";
			String location = details[0];
			boolean isAvailable = Boolean.parseBoolean(details[1]);
			double price = Double.parseDouble(details[2]);

			ParkingSlot v = new ParkingSlot(slotId, location, isAvailable, price);
			parkingslotObj.add(v);
		}
		return parkingslotObj;
	}

	// 3.Generate SLot Id
	public static String generateSlotId() {
		// Initialize the counter at program start
		return ParkingManagement.initializeIdCounter();
	}

	public boolean addSlotRecords(List<ParkingSlot> slot) {
		return pm.addSlotRecords(slot);
	}

	public boolean UpdateSlotAvailability(String slotId, boolean isAvailable) {
		return pm.UpdateSlotAvailability(slotId, isAvailable);
	}

	public List<ParkingSlot> retrieveAvailableSlots() {
		return pm.retrieveAvailableSlots();
	}

	public boolean deleteParkingSlot(String slotId) {
		return pm.deleteParkingSlot(slotId);
	}

	public boolean checkSlotAvailability(String slotId) {
		return pm.checkSlotAvailability(slotId);
	}
	
	public boolean validSlotDetails(String slot) {
		return util.validSlotDetails(slot);
	}
	
	
	

}
