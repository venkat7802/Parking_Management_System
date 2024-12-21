package com.service;

import java.util.ArrayList;
import java.util.List;
import com.management.VehicleManagement;
import com.model.Vehicle;
import com.util.ApplicationUtil;

public class VehicleService {
	ApplicationUtil util = new ApplicationUtil();
	VehicleManagement vm = new VehicleManagement();

	public boolean add(String[] details) {
		boolean status = false;
		if (details.length != 0) {
			List<String> strList = util.splitRecord(details);
			List<Vehicle> vehList = build(strList);
			status = addVehicleRecords(vehList);
		}

		return status;
	}

	public List<Vehicle> build(List<String> Vehicledetails) {
		List<Vehicle> vehicleList = new ArrayList<>();
		for (int i = 0; i < Vehicledetails.size(); i++) {
			String[] details = Vehicledetails.get(i).split(":");
			String vehicleId = "";
			String licenseNumber = details[0];
			String vehicleType = details[1];

			Vehicle v = new Vehicle(vehicleId, licenseNumber, vehicleType);
			vehicleList.add(v);
		}
		return vehicleList;
	}

	public boolean addVehicleRecords(List<Vehicle> v) {
		return vm.addVehicleRecords(v);
	}

	public boolean vehicleIdExistance(String vehicleId) {
		return vm.vehicleIdExistance(vehicleId);
	}

	public Vehicle getVehicleDetails(String vehicleId) {
		return vm.getVehicleDetails(vehicleId);
	}

	public List<Vehicle> getVehicleByType(String vehicleType) {
		return vm.getVehicleByType(vehicleType);
	}

	public boolean deleteVehicleRecords(String vehicleId) {
		return vm.deleteVehicleRecords(vehicleId);
	}

	public static String generateVehicleId() {
		// Initialize the counter at program start
		return VehicleManagement.initializeIdCounter();

	}

	public boolean validLicense(String license) {
		return util.validLicense(license);

	}

}
