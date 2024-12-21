package com.model;

public class Vehicle {
	private String vehicleId;
	private String licenseNumber;
	private String vehicletype;
	public Vehicle(String vehicleId, String licenseNumber, String vehicletype) {
		super();
		this.vehicleId = vehicleId;
		this.licenseNumber = licenseNumber;
		this.vehicletype = vehicletype;
	}
	
	public Vehicle() {
		super();
	}

	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getVehicleType() {
		return vehicletype;
	}
	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}
	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", licenseNumber=" + licenseNumber + ", vehicletype=" + vehicletype
				+ "]";
	}
     
}
