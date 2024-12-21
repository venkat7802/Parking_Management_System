package com.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.exception.SlotNotAvailableException;
import com.model.ParkingSlot;
import com.model.ParkingSlotBooking;
import com.model.Vehicle;
import com.service.ParkingSlotBookingService;
import com.util.ApplicationUtil;

public class ParkingSlotBookingManagement {

	// Book Parking Slot
	public boolean addSlotRecords(List<ParkingSlotBooking> parkingslotBooking) throws SlotNotAvailableException {
		boolean isBooked = false;
		for (int i = 0; i < parkingslotBooking.size(); i++) {
			ParkingSlotBooking p = parkingslotBooking.get(i);
			try (Connection con = DBConnectionManager.getConnection()) {
				// Check if the slot is available

				ParkingSlot pk = p.getP();
				Vehicle vk = p.getV();
				PreparedStatement ps = con
						.prepareStatement("SELECT * FROM ParkingSlot WHERE slotId=? AND isAvailable=1");
//                AND isAvailable=1
				ps.setString(1, pk.getSlotId());
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					// Slot is available
					// Reserve the slot
					PreparedStatement bookSlotStmt = con.prepareStatement(
							"INSERT INTO ParkingslotBooking (slotId, bookingId, vehicleId, startTime, endTime, status) VALUES (?, ?, ?, ?, ?, ?)");
					bookSlotStmt.setString(1, pk.getSlotId());
					bookSlotStmt.setString(2, ParkingSlotBookingService.generateBookingId());
					bookSlotStmt.setString(3, vk.getVehicleId());
					ApplicationUtil aobj = new ApplicationUtil();
					bookSlotStmt.setTimestamp(4, aobj.convertSqlDate(p.getStartTime()));
					bookSlotStmt.setTimestamp(5, aobj.convertSqlDate(p.getEndTime()));
					bookSlotStmt.setString(6, "RESERVED");

					int rowsAffected = bookSlotStmt.executeUpdate();
					if (rowsAffected > 0) {
						String updateSlotQuery = "UPDATE ParkingSlot SET isAvailable=0 WHERE slotId = ?";
						PreparedStatement updateSlotStmt = con.prepareStatement(updateSlotQuery);
						updateSlotStmt.setString(1, p.getP().getSlotId());
						updateSlotStmt.executeUpdate();
						isBooked = true;
					}
				}

			} catch (ClassNotFoundException | SQLException  e) {
				e.printStackTrace();
			}
		}
		return isBooked;
	}

	public boolean cancelParkingBooking(String slotId) {
		boolean isCanceled = false;
		try (Connection con = DBConnectionManager.getConnection()) {
			// Cancel the booking
			PreparedStatement cancelStmt = con.prepareStatement("DELETE FROM ParkingSlotBooking WHERE slotId = ?");
			cancelStmt.setString(1, slotId);
            int rowsAffected = cancelStmt.executeUpdate();
			if (rowsAffected > 0) {
				// Update slot status
				String updateSlotQuery = "UPDATE parkingSlot SET isAvailable =1  WHERE slotId = ?";
				PreparedStatement updateSlotStmt = con.prepareStatement(updateSlotQuery);
				updateSlotStmt.setString(1, slotId);
				updateSlotStmt.executeUpdate();
				isCanceled = true;
			}
		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
		}
		return isCanceled;
	}

	public boolean viewBookingStatus(String slotId) {
		boolean status = false;
		try (Connection con = DBConnectionManager.getConnection()) {
			String query = "SELECT status FROM ParkingSlotBooking WHERE slotId = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, slotId);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String availability = rs.getString("status");
				if (availability.equalsIgnoreCase("Reserved"))
					return true;
			}
		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean updateBookingDetails(String slotId, LocalDateTime newStartTime, LocalDateTime newEndTime) {
		boolean isUpdated = false;
		try (Connection con = DBConnectionManager.getConnection()) {
			String query = "UPDATE ParkingslotBooking SET startTime = ?, endTime = ? WHERE slotId = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			ApplicationUtil aobj = new ApplicationUtil();
			stmt.setTimestamp(1, aobj.convertSqlDate(newStartTime));
			stmt.setTimestamp(2, aobj.convertSqlDate(newEndTime));
			stmt.setString(3, slotId);
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				isUpdated = true;
			}
		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	public Vehicle getVehicleDetails(String id) {
		Vehicle v = null;
		Connection con;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("Select * from Vehicle where vehicleId=?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				v = new Vehicle(rs.getString("vehicleId"), rs.getString("licenseNumber"), rs.getString("vehicleType"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return v;

	}

	public ParkingSlot getSlotDetails(String id) {
		ParkingSlot v = null;
		Connection con;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("Select * from ParkingSlot where slotId=?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				v = new ParkingSlot(rs.getString("slotId"), rs.getString("location"), rs.getBoolean("isAvailable"),
						rs.getDouble("price"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return v;

	}

	public static String initializeIdCounter() {
		int id3 = 0;
		Connection con;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT count(bookingId) FROM ParkingSlotBooking");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id3 = rs.getInt(1);
				id3++;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return "BId" + id3;
	}

}
