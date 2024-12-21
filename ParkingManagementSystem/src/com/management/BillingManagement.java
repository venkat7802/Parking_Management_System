package com.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.model.Billing;
import com.util.ApplicationUtil;

public class BillingManagement {
	public Billing retrieveBillDetailsForBookingId(String bookingId) {
		Billing billObj = null;
		try (Connection con = DBConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM parkingslotbooking WHERE bookingId = ?");
			ps.setString(1, bookingId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// Retrieve booking details
				String bId = rs.getString("bookingId");
				String slotId = rs.getString("SlotId");
				Timestamp startTimestamp = rs.getTimestamp("startTime");
				LocalDateTime startTime = startTimestamp.toLocalDateTime();
				Timestamp endTimestamp = rs.getTimestamp("endTime");
				LocalDateTime endTime = endTimestamp.toLocalDateTime();

				PreparedStatement stmt = con.prepareStatement("SELECT price FROM parkingslot WHERE SlotId = ?");
				stmt.setString(1, slotId);
				ResultSet rset = stmt.executeQuery();
				if (rset.next()) {
					double price = rset.getDouble("price");
					// Create Billing object
					billObj = new Billing(bId, startTime, endTime, price);
				}
			}
		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
		}

		return billObj;
	}

	public Billing retrieveBillingDetails(String bookingId) {
		Billing bObj = null;
		ResultSet rs = null;
		try (Connection con = DBConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from billing where bookingId=?");
			ps.setString(1, bookingId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int billId = rs.getInt("billId");
				String bookId = rs.getString("bookingId");
				LocalDateTime sTime = rs.getTimestamp("startTime").toLocalDateTime();
				LocalDateTime eTime = rs.getTimestamp("endTime").toLocalDateTime();
				double totalBill = rs.getDouble("totalBill");
				LocalDateTime billDate = rs.getTimestamp("billDate").toLocalDateTime();
				bObj = new Billing(billId, bookId, sTime, eTime, totalBill, billDate);
			}
			return bObj;
		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
		}
		return bObj;
	}

	public boolean addVehicleDetails(Billing billObj) {
		try (Connection con = DBConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"insert into billing(bookingId,startTime,endTime,totalBill,billDate) values(?,?,?,?,?)");
			ps.setString(1, billObj.getBookingId());
			ApplicationUtil au = new ApplicationUtil();
			ps.setTimestamp(2, au.convertSqlDate(billObj.getStartTime()));
			ps.setTimestamp(3, au.convertSqlDate(billObj.getEndTime()));
			ps.setDouble(4, billObj.getTotalBill());
			ps.setTimestamp(5, au.convertSqlDate(billObj.getBillDate()));
			int result = ps.executeUpdate();
			if (result != 0) {
				return true;
			}
		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
		}
		return false;
	}
}
