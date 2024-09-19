package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Method to add a new patient to the database
    public void addPatient() {
    	
        System.out.println("Enter Patient Name: ");
        // Consume the remaining newline from next()
        scanner.nextLine(); 
        String name = scanner.nextLine();
   
       
        System.out.println("Enter Patient Age: ");
        int age = scanner.nextInt();
        // Consume the remaining newline from next()
        scanner.nextLine(); 
        System.out.println("Enter Patient Gender: ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient added successfully!");
            } else {
                System.out.println("Failed to add patient details.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view all patients in the database
    public void viewPatient() {
        String query = "SELECT * FROM patients";  // Corrected table name
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Display header for table-like structure
            System.out.println("+----+-------------------------+----------+-------------+");
            System.out.println("| ID | Name                    | Age      | Gender      |");
            System.out.println("+----+-------------------------+----------+-------------+");

            // Iterate over the ResultSet and display each patient's information
            while (resultSet.next()) {
                int id = resultSet.getInt("id"); 
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                System.out.format("| %-2d | %-23s | %-8d | %-11s |%n", id, name, age, gender);
            }

            System.out.println("+----+-------------------------+----------+-------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a patient by their ID
    public boolean getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id = ?"; 

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);  

            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a patient is found
            if (resultSet.next()) {
                System.out.println("Patient Found:");
                System.out.println("ID: " + resultSet.getInt("id")); 
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Age: " + resultSet.getInt("age"));
                System.out.println("Gender: " + resultSet.getString("gender"));
                return true;
            } else {
                System.out.println("No patient found with ID: " + id);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 // Method to delete patient by ID
    public void deletePatientById() {
        System.out.println("Enter Patient ID to delete: ");
        int id = scanner.nextInt(); // Accept ID from the user

        try {
            // Step 1: Delete appointments for the patient first
            String deleteAppointmentsQuery = "DELETE FROM appointments WHERE patient_id = ?";
            PreparedStatement deleteAppointmentsStmt = connection.prepareStatement(deleteAppointmentsQuery);
            deleteAppointmentsStmt.setInt(1, id);
            deleteAppointmentsStmt.executeUpdate();

            // Step 2: Delete patient from patients table
            String deletePatientQuery = "DELETE FROM patients WHERE id = ?";
            PreparedStatement deletePatientStmt = connection.prepareStatement(deletePatientQuery);
            deletePatientStmt.setInt(1, id);

            int affectedRows = deletePatientStmt.executeUpdate(); // Execute the deletion

            if (affectedRows > 0) {
                System.out.println("Patient with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No patient found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
