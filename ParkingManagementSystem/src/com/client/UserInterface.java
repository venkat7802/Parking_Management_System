package com.client;

import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.exception.SlotNotAvailableException;
import com.model.Billing;
import com.model.ParkingSlot;
import com.model.Vehicle;
import com.service.BillingService;
import com.service.ParkingSlotBookingService;
import com.service.ParkingSlotService;
import com.service.VehicleService;

public class UserInterface {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		VehicleService vs = new VehicleService();
		ParkingSlotService ps = new ParkingSlotService();
		String red = "\u001B[31m";
		String r1 = "\u001B[0m";
		String green = "\u001B[32m";
		String r2 = "\u001B[0m";
		String blue = "\u001B[34m";
		String r3 = "\u001B[0m";
		String darkGreen = "\033[32m ";
		String r4 = "\033[0m";
		String megenta = "\033[35m";
		String r5 = "\033[0m";
		String darkYellow = "\033[33m";
		String r6 = "\033[0m";

		while (true) {
			try {

				System.out.printf(blue + " %-40S \n", "|===============================================|");
				System.out.printf(" %-40S \n", "|              Welcome to EasyPark!!!           |");
				System.out.printf(" %-40S \n", "|  Your Trusted Solution for Hassle-Free Parking|");
				System.out.printf(" %-40S \n", "|===============================================|");
				System.out.printf(" %-40S \n", "| Please choose from the following options:     |");
				System.out.printf(" %-40S \n", "|-----------------------------------------------|");
				System.out.printf(" %-40S \n", "| 1.Vehicle Management                          |");
				System.out.printf(" %-40S \n", "| 2.ParkingSlot Management                      |");
				System.out.printf(" %-40S \n", "| 3.ParkingSlotBooking Management               |");
				System.out.printf(" %-40S \n", "| 4.Billing Management                          |");
				System.out.printf(" %-40S \n", "| 5.Exit                                        |");
				System.out.printf(" %-40S \n", "|-----------------------------------------------|");
				System.out.println(r3 + "➤➤Enter your choice");
				int choice = Integer.parseInt(sc.nextLine());
				while (true) {
					if (choice == 1) {
						System.out.printf(blue + " %-40S \n", "|-----------------------------------------------|");
						System.out.printf(" %-40S \n", "| Please choose from the following options:     |");
						System.out.printf(" %-40S \n", "|-----------------------------------------------|");
						System.out.printf(" %-40S \n", "| 1.Insert Vehicle Details                      |");
						System.out.printf(" %-40S \n", "| 2.checkVehicleId Existence                    |");
						System.out.printf(" %-40S \n", "| 3.Retrieve vehicle_Details by vehicle Id      |");
						System.out.printf(" %-40S \n", "| 4.Retrieve vehicles by Type                   |");
						System.out.printf(" %-40S \n", "| 5.Delete vehicle Records                      |");
						System.out.printf(" %-40S \n", "| 6.Back to main menu                           |");
						System.out.printf(" %-40S \n", "|-----------------------------------------------|");

						System.out.println(r3 + "➤➤Enter your choice");
						int choice1 = Integer.parseInt(sc.nextLine());
						outer:
						if (choice1 == 1) {
							System.out.println("➤Enter the number of details to be inserted");
							int size = Integer.parseInt(sc.nextLine());
							String[] array = new String[size];
							System.out.println("➤Enter the detail to be inserted");
							System.out.println("------------here(↓)-------------");
							for (int i = 0; i < size; i++) {
							    
								System.out.println("➤Enter License Number");
								String LicenseNumber = sc.nextLine();
								if (!vs.validLicense(LicenseNumber)) {
									System.out.println(darkYellow+"Sorry,Invalid License Number"+r6);
									break outer;
								}
								else
								{
								System.out.println("➤Enter vehicle Type");
								String vehicleType = sc.nextLine();
								array[i] = LicenseNumber + ":" + vehicleType;
								}
							}	
							boolean status = vs.add(array);
							if (status)
								System.out.println(green+"Inserted successfully"+r2);
							System.out.println("--------------------------------------------------");
						}  else if (choice1 == 2) {
							System.out.println("➤Enter the VehicleId to be Checked");
							String vehicleId = sc.nextLine();
							Boolean status = vs.vehicleIdExistance(vehicleId);
							if (status)
								System.out.println(green + "Vehicle Id present" + r2);
							else
								System.out.println(darkYellow + "Sorry,Vehicle Id is not Available" + r6);
							System.out.println("--------------------------------------------------");
						} else if (choice1 == 3) {
							System.out.println("➤Enter the Vehicle Id");
							String vehicleId = sc.nextLine();
							try {
								Vehicle v = vs.getVehicleDetails(vehicleId);

								System.out.println("-------------------------------------");
								System.out.println("            Vehicle Details         ");
								System.out.println("-------------------------------------");
								System.out.printf(" %-20s : %-20s \n", "Vehicle ID", v.getVehicleId());
								System.out.printf(" %-20s : %-20s \n", "License Number", v.getLicenseNumber());
								System.out.printf(" %-20s : %-20s \n", "Vehicle Type", v.getVehicleType());
								System.out.println("-------------------------------------");
							} catch (NullPointerException n) {
								System.out.println(darkYellow + "Sorry,no such VehicleId found" + r6);
							}
							System.out.println("--------------------------------------------------");
						} else if (choice1 == 4) {
							System.out.println("➤Enter the vehicle Type");
							String vehicleType = sc.nextLine();
							List<Vehicle> list = vs.getVehicleByType(vehicleType);
							if (list.size() == 0) {
								System.out.println(darkYellow + "Sorry,no such vehicle Available" + r6);
							}
							for (int i = 0; i < list.size(); i++) {
								Vehicle v = list.get(i);
								System.out.println("--------------------------------------------------");
								System.out.printf("| %-20s : %-20s |\n", "Vehicle ID: ", v.getVehicleId());
								System.out.printf("| %-20s : %-20s |\n", "License Number: ", v.getLicenseNumber());
								System.out.printf("| %-20s : %-20s |\n", "Vehicle Type: ", v.getVehicleType());
								System.out.println("--------------------------------------------------");
							}
							System.out.println("--------------------------------------------------");
						} else if (choice1 == 5) {
							System.out.println("➤Enter VehicleId to be deleted");
							String vehicleId = sc.nextLine();
							boolean status = vs.deleteVehicleRecords(vehicleId);
							if (status) {
								System.out.println(green + "VehicleId deleted Successfully" + r2);
							} else
								System.out.println(darkYellow + "Sorry,Vehicle Id is not found" + r6);
							System.out.println("--------------------------------------------------");
						} else if (choice1 == 6) {
							System.out.println(darkGreen + "Thank You,Welcome Again" + r4);
							System.out.println("--------------------------------------------------");
							break;
						} else {
							System.out.println(red + "OOPS...Invalid choice" + r1);
							System.out.println("--------------------------------------------------");
							break;
						}
					}

					else if (choice == 2) {

						System.out.printf(blue + " %-40S \n", "|-----------------------------------------------|");
						System.out.printf(" %-40S \n", "| Please choose from the following options:     |");
						System.out.printf(" %-40S \n", "|-----------------------------------------------|");
						System.out.printf(" %-40S \n", "| 1.Add Parking Slot                            |");
						System.out.printf(" %-40S \n", "| 2.Update Slot Availability                    |");
						System.out.printf(" %-40S \n", "| 3.Retrieve Reservation List                   |");
						System.out.printf(" %-40S \n", "| 4.Delete Slot                          |");
						System.out.printf(" %-40S \n", "| 5.Check Slot Availability                     |");
						System.out.printf(" %-40S \n", "| 6.Back to main menu                           |");
						System.out.printf(" %-40S \n", "|-----------------------------------------------|");
						System.out.println(r3 + "➤➤Enter your choice");
						int choice2 = Integer.parseInt(sc.nextLine());
						outer:
						if (choice2 == 1) {
							System.out.println("➤Enter Number of Slot details to be added");
							int size = Integer.parseInt(sc.nextLine());
							String[] arr = new String[size];
							System.out.println("➤Enter the Parking Slot Details to be added");
							System.out.println("------------here(↓)-------------");
							for (int i = 0; i < size; i++) {
								System.out.println("➤Enter location to be Added");
								String location = sc.nextLine();
								boolean Avail_Status = ps.validSlotDetails(location);
								if(!Avail_Status)
								{
									System.out.println(darkYellow + "Sorry,No Such Location Available" + r6);
									break outer;
								}
								System.out.println("➤Availability Status");
								boolean status = Boolean.parseBoolean(sc.nextLine());
								System.out.println("➤Enter price");
								double price = Double.parseDouble(sc.nextLine());
								arr[i] = location + ":" + status + ":" + price;
							}
							boolean status = ps.add(arr);
							if (status)
								System.out.println(green+"Slot Id Inserted successfully"+r2);
						} else if (choice2 == 2) {
							// update slotId and status
							System.out.println("➤Enter slotId to be updated");
							String slotId = sc.nextLine();
							System.out.println("➤Enter Availabalilty status");
							boolean status = Boolean.parseBoolean(sc.nextLine());
							boolean updatedStatus = ps.UpdateSlotAvailability(slotId, status);
							if (updatedStatus)
								System.out.println(green + "slotId updated successfully" + r2);
							else
								System.out.println(darkYellow + "Sorry,Invalid slotId" + r6);
						} 
						else if (choice2 == 3) {
							System.out.println("➤Reserved slots are");
							List<ParkingSlot> list = ps.retrieveAvailableSlots();
							if (list.isEmpty()) {
								System.out.println(darkYellow + "Sorry,No Reserved slots" + r6);
							} else {
								for (int i = 0; i < list.size(); i++) {
									ParkingSlot p = list.get(i);
									System.out.println("--------------------------------------------------");
									System.out.printf(" %-20s : %-20s \n", "Slot Id: ", p.getSlotId());
									System.out.printf(" %-20s : %-20s \n", "Location: ", p.getLocation());
									System.out.printf(" %-20s : %-20s \n", "Price: ", p.getPrice());
									System.out.println("--------------------------------------------------");
								}
							}

						} else if (choice2 == 4) {
							System.out.println("➤Enter the slotId to be deleted");
							String slotId = sc.nextLine();
							Boolean status = ps.deleteParkingSlot(slotId);
							if (status)
								System.out.println(green + "Deleted successfully" + r2);
							else
								System.out.println(darkYellow + "Sorry,Vehicle is present..cannot be deleted" + r6);
						} else if (choice2 == 5) {
							System.out.println("➤Enter the slotId to check Availability status");
							String slotId = sc.nextLine();
							Boolean status = ps.checkSlotAvailability(slotId);
							if (status)
								System.out.println(green + "Slot Available" + r2);
							else
								System.out.println(darkYellow + "Sorry,Slot not Available" + r6);
						} else if (choice2 == 6) {
							System.out.println(darkGreen + "Thank you,Welcome Again" + r4);
							break;
						} else {
							System.out.println(red + "OOPS...Invalid choice" + r1);
							System.out.println("--------------------------------------------------");
							break;
						}
					}

					else if (choice == 3) {
						ParkingSlotBookingService psb = new ParkingSlotBookingService();

						System.out.printf(blue + " %-40S \n", "|-----------------------------------------------|");
						System.out.printf(" %-40S \n", "| Please choose from the following options:     |");
						System.out.printf(" %-40S \n", "|-----------------------------------------------|");
						System.out.printf(" %-40S \n", "| 1.Book a Slot                                 |");
						System.out.printf(" %-40S \n", "| 2.Cancel Booking                              |");
						System.out.printf(" %-40S \n", "| 3.View Booking Status                         |");
						System.out.printf(" %-40S \n", "| 4.Update Booking Status                       |");
						System.out.printf(" %-40S \n", "| 5.Back to main menu                           |");
						System.out.printf(" %-40S \n", "|-----------------------------------------------|");

						System.out.println(r3 + "➤➤Enter your choice");
						try {
							int choice3 = Integer.parseInt(sc.nextLine());
							if (choice3 == 1) {
								System.out.println("➤Enter number of Bookings");
								int size = Integer.parseInt(sc.nextLine());
								String[] arr = new String[size];
								for (int i = 0; i < size; i++) {
									ParkingSlot p = new ParkingSlot();
									Vehicle v = new Vehicle();
									System.out.println(
											"➤Enter the StartTime(please ensure the format be in [YYYY-MM-DD HH:mm:SS])");
									String startTime = sc.nextLine();
									System.out.println(
											"➤Enter the endTime(please ensure the format be in [YYYY-MM-DD HH:mm:SS])");
									String endTime = sc.nextLine();
									System.out.println("➤Enter vehicleId");
									String VehicleId = sc.nextLine();
									System.out.println("➤Enter slotId");
									String slotId = sc.nextLine();
									arr[i] = slotId + ";" + VehicleId + ";" + startTime + ";" + endTime;
								}
								boolean status = psb.add(arr);
								if (status)
									System.out.println(green + "Slot Booked successfully" + r2);
							} else if (choice3 == 2) {
								System.out.println("➤Enter the slot Id to be deleted");
								String slotId = sc.nextLine();
								boolean status = psb.cancelParkingBooking(slotId);
								if (status)
									System.out.println(green + "Booking deleted successfully" + r2);
								// cancelParkingBooking
							} else if (choice3 == 3) {
								System.out.println("➤Enter the slotId for which status to be checked");
								String slotId = sc.nextLine();
								boolean status = psb.viewBookingStatus(slotId);
								if (status)
									System.out.println(green + "Booking Status of " + slotId + ": Reserved" + r2);
								else
									System.out.println(darkYellow + "Sorry,BookingId not Available" + r6);

							} else if (choice3 == 4) {
								System.out.println("➤Enter the slotId to Which Date be updated");
								String slotId = sc.nextLine();
								System.out.println("➤Enter the startingDate to be updated");
								String startDate = sc.nextLine();
								System.out.println("➤Enter the endDate to be updated");
								String endDate = sc.nextLine();
								Boolean status = psb.updateBookingDetails(slotId, startDate, endDate);
								if (status)
									System.out.println(
											green + "Date Details updated successfully for the given slot" + r2);
							} else if (choice3 == 5) {
								System.out.println(darkGreen + "Thank you,Welcome Again" + r4);
								break;
							} else {
								System.out.println(red + "OOPS...Invalid choice" + r1);
								System.out.println("--------------------------------------------------");
								break;
							}

						} catch (SlotNotAvailableException e) {
							System.out.println(red+e.getMessage()+r1);
							break;
						}
						catch (NullPointerException e) {
							System.out.println(red+"Please check the VehicleId /SlotId you have Entered"+r1);
							break;
						}
						catch (DateTimeParseException e) {
							System.out.println(red+"Invalid Date Format"+r1);
							break;
						}

					} else if (choice == 4) {
						BillingService bm = new BillingService();

						System.out.printf(blue + " %-40S \n", "|-----------------------------------------------|");
						System.out.printf(" %-40S \n", "| Please choose from the following options:     |");
						System.out.printf(" %-40S \n", "|-----------------------------------------------|");
						System.out.printf(" %-40S \n", "| 1.Generate Bill for BookingId                 |");
						System.out.printf(" %-40S \n", "| 2.To see the Bill details Based on BookingId  |");
						System.out.printf(" %-40S \n", "| 3.Return to main menu                         |");
						System.out.printf(" %-40S \n", "|-----------------------------------------------|");

						System.out.println(r3 + "➤➤Enter your choice");
						int choice4 = Integer.parseInt(sc.nextLine());
						try {
							if (choice4 == 1) {
								System.out.println("➤Enter bookingId to which bill to be generated");
								String bkId = sc.nextLine();
								Billing b = bm.retrieveBillDetailsforBookingId(bkId);
								boolean status = bm.addVehicleDetails(b);
								if (status)
									System.out.println(green + "Bill Generated successfully" + r2);
								System.out.println("If you need to display the bill details press 1");
								int choice5 = Integer.parseInt(sc.nextLine());
								if (choice5 == 1) {
									Billing billing = bm.retrieveBillingDetails(bkId);
									System.out.println(billing);
								}
							} else if (choice4 == 2) {
								System.out.println("➤Enter the bookingId to which you need to see Bill Details");
								String BkId = sc.nextLine();
								Billing billing = bm.retrieveBillingDetails(BkId);
								if (billing == null) {
									System.out.println(darkYellow + "Sorry,No such Billing Id found" + r6);
									break;
								}
								System.out.println(billing);
							} else if (choice4 == 3) {
								System.out.println(darkYellow + "↑↑Returning to main menu......↑↑" + r6);
								break;

							}
						}

						catch (NullPointerException e) {
							System.out.println(darkYellow + "Sorry,No such Billing Id found" + r6);
						}

					} else if (choice == 5) {
						String message = "🙏Thank you for using the Application!!!";
						System.out.println(megenta + "********************************************");
						System.out.println("**" + message + "**");
						System.out.println("********************************************" + r5);
						return;
					} else {
						System.out.println(red + "Invalid Input,Please try again if you want" + r1);
						break;
					}

				}
			}

			catch (InputMismatchException | NumberFormatException e) {
				System.out.println(red + "Invalid Input,Please try again if you want" + r1);
				break;
			}
		}
	}

}
