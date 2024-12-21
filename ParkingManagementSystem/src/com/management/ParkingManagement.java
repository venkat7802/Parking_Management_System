package com.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.ParkingSlot;
import com.service.ParkingSlotService;

public class ParkingManagement {

	// 1.Insert vehicle list
	public boolean addSlotRecords(List<ParkingSlot> slot) {
		int row = 0;
		for (int i = 0; i < slot.size(); i++) {
			ParkingSlot v = slot.get(i);
			Connection con;
			try {
				con = DBConnectionManager.getConnection();
				PreparedStatement ps = con
						.prepareStatement("insert into ParkingSlot(SlotId,location,isAvailable,price)VALUES(?,?,?,?)");
				ps.setString(1, ParkingSlotService.generateSlotId());
				ps.setString(2, v.getLocation());
				ps.setBoolean(3, v.getIsAvailable());
				ps.setDouble(4, v.getPrice());
				row += ps.executeUpdate();
				if (row == slot.size()) {
					return true;
				}
				con.close();

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// 4.delete Reservation
	public boolean deleteParkingSlot(String slotId) {
		Connection con;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("delete from ParkingSlot where slotId=? and isAvailable=1;");
			ps.setString(1, slotId);
			int row = ps.executeUpdate();
			if (row > 0)
				return true;
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 3.Retrieve Reservation list
	public List<ParkingSlot> retrieveAvailableSlots() {
		List<ParkingSlot> slots = new ArrayList<>();
		Connection con;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from Parkingslot where isAvailable=0");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				slots.add(new ParkingSlot(rs.getString("slotId"), rs.getString("location"),
						rs.getBoolean("isAvailable"), rs.getDouble("price")));
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return slots;
	}

	// 5.Check slot Availability
	public boolean checkSlotAvailability(String slotId) {
		boolean Status = false;
		Connection con;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("select isAvailable from Parkingslot where slotId=?");
			ps.setString(1, slotId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Status = rs.getBoolean("isAvailable");
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Status;
	}

	// 2.update SLot AVailability

	public boolean UpdateSlotAvailability(String slotId, boolean isAvailable) {
		Connection con;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("Update Parkingslot set isAvailable=? where slotId=?");
			ps.setBoolean(1, isAvailable);
			ps.setString(2, slotId);
			int row = ps.executeUpdate();
			if (row > 0)
				return true;
			ps.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String initializeIdCounter() {
		Connection con;
		int id2 = 0;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT count(SlotId) FROM parkingSlot");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id2 = rs.getInt(1);
				id2++;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return "Slot" + id2;
	}

}
