package com.pluralsight;

import com.pluralsight.ShippersDataManager;
import com.pluralsight.Shipper;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class NorthwindShippersApp {
    public static void main(String[] args) {
        ShippersDataManager dataManager = new ShippersDataManager();
        Scanner scanner = new Scanner(System.in);

        try {
            // Prompt the user for new shipper data
            System.out.println("Enter new shipper data:");
            System.out.print("Company Name: ");
            String companyName = scanner.nextLine();
            System.out.print("Phone: ");
            String phone = scanner.nextLine();

            Shipper newShipper = new Shipper();
            newShipper.setCompanyName(companyName);
            newShipper.setPhone(phone);

            // Insert the new shipper
            int newShipperId = dataManager.insertShipper(newShipper);
            System.out.println("New shipper inserted with ShipperID: " + newShipperId);

            // Display all shippers
            System.out.println("\nAll Shippers:");
            displayShippers(dataManager.getAllShippers());

            // Prompt the user to change the phone number of a shipper
            System.out.print("\nEnter the ShipperID to update phone number: ");
            int shipperIdToUpdate = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            System.out.print("Enter the new phone number: ");
            String newPhone = scanner.nextLine();
            dataManager.updateShipperPhone(shipperIdToUpdate, newPhone);
            System.out.println("Shipper phone number updated successfully.");

            // Display all shippers after update
            System.out.println("\nAll Shippers after update:");
            displayShippers(dataManager.getAllShippers());

            // Prompt the user to delete a shipper
            System.out.print("\nEnter the ShipperID to delete (excluding 1-3): ");
            int shipperIdToDelete = scanner.nextInt();
            dataManager.deleteShipper(shipperIdToDelete);
            System.out.println("Shipper deleted successfully.");

            // Display all shippers after delete
            System.out.println("\nAll Shippers after delete:");
            displayShippers(dataManager.getAllShippers());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayShippers(List<Shipper> shippers) {
        for (Shipper shipper : shippers) {
            System.out.println(shipper);
        }
    }
}
