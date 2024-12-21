package com.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.Vehicle;
import com.service.VehicleService;

public class VehicleManagement {

	// 1.Insert vehicle list
	public boolean addVehicleRecords(List<Vehicle> vehicle) {
		boolean status = false;
		for (int i = 0; i < vehicle.size(); i++) {
			Vehicle v = vehicle.get(i);
			Connection con;
			try {
				con = DBConnectionManager.getConnection();
				PreparedStatement ps = con
						.prepareStatement("insert into Vehicle(vehicleId,licenseNumber,vehicleType)VALUES(?,?,?)");
				ps.setString(1, VehicleService.generateVehicleId());
				ps.setString(2, v.getLicenseNumber());
				ps.setString(3, v.getVehicleType());
				int row = ps.executeUpdate();
				if (row > 0)
					status = true;
				con.close();

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	// 2.checkVehicleId Existence
	public boolean vehicleIdExistance(String vehicleId) {
		Connection con;
		boolean status = false;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from vehicle where vehicleId=?");
			ps.setString(1, vehicleId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				status = true;
				return status;
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	// 3.Retrieve vehicle_Details by vehicle Id
	public Vehicle getVehicleDetails(String vehicleId) {
		Connection con;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from vehicle where vehicleId=?");
			ps.setString(1, vehicleId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new Vehicle(rs.getString("vehicleId"), rs.getString("licenseNumber"),
						rs.getString("vehicleType"));
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 4.Retrieve vehicles by Type
	public List<Vehicle> getVehicleByType(String vehicleType) {
		List<Vehicle> vehicles = new ArrayList<>();
		Connection con;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from vehicle where vehicleType=?");
			ps.setString(1, vehicleType);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				vehicles.add(new Vehicle(rs.getString("vehicleId"), rs.getString("licenseNumber"),
						rs.getString("vehicleType")));
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return vehicles;
	}

	// Delete vehicle Records
	public boolean deleteVehicleRecords(String vehicleId) {
		boolean status = false;
		Connection con;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("Delete from Vehicle where vehicleId=?");
			ps.setString(1, vehicleId);
			int rowAffected = ps.executeUpdate();
			if (rowAffected > 0) {
				status = true;
				return status;
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public static String initializeIdCounter() {
		Connection con;
		int id2 = 0;
		try {
			con = DBConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT count(VehicleId) FROM Vehicle");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id2 = rs.getInt(1);
				id2++;
			}
		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
		}
		return "VId" + id2;
	}

}
