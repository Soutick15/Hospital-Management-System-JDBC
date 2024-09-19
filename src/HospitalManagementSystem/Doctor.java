package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    // Method to view all doctors in the database
    public void viewDoctors() {
        String query = "SELECT * FROM doctors";  // Corrected table name
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

     
            System.out.println("+----+-------------------------+-------------------------+");
            System.out.println("| ID | Name                    | Specialization          |");
            System.out.println("+----+-------------------------+-------------------------+");

            // Iterate over the ResultSet and display each doctor's information
            while (resultSet.next()) {
                int id = resultSet.getInt("id"); 
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");

                System.out.format("| %-2d | %-23s | %-23s |%n", id, name, specialization);
            }

            System.out.println("+----+-------------------------+-------------------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check if a doctor exists by ID
    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";  // Fixed SQL query

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id); 

            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a doctor is found
            if (resultSet.next()) {
                System.out.println("Doctor Found:");
                System.out.println("ID: " + resultSet.getInt("id")); 
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Specialization: " + resultSet.getString("specialization"));
                return true;
            } else {
                System.out.println("No doctor found with ID: " + id);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
