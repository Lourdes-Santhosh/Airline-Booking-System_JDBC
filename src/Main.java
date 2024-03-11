import java.sql.*;
import java.util.Scanner;

class AirlineBookingApp {
    // JDBC URL, username, and password
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/airline";
    static final String USERNAME = "root";
    static final String PASSWORD = "password";
    static Scanner scan = new Scanner(System.in);


    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Flight(id INT PRIMARY KEY, name VARCHAR(100), seats INT)");
            statement.execute("CREATE TABLE IF NOT EXISTS Passenger(pid INT PRIMARY KEY, pName VARCHAR(100), sex VARCHAR(1), flightId INT, address VARCHAR(255))");

            // Main loop
            while (true) {
                System.out.println("------------------------------------------------------------------------");
                System.out.println("1. Insert Passenger Details");
                System.out.println("2. Display Flight Details");
                System.out.println("3. Cancel Ticket");
                System.out.println("4. Update Passenger Information");
                System.out.println("5. Update Flight Details");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = scan.nextInt();
                System.out.println("------------------------------------------------------------------------");

                switch (choice) {
                    case 1:
                        // Insert Passenger Details
                        insertPassengerDetails(connection);
                        break;
                    case 2:
                        // Display Flight Details
                        displayFlightDetails(connection);
                        break;
                    case 3:
                        // Cancel Ticket
                        cancelTicket(connection);
                        break;
                    case 4:
                        // Update Passenger Information
                        updatePassengerInformation(connection);
                        break;
                    case 5:
                        // Update Flight Details
                        updateFlightDetails(connection);
                        break;
                    case 6:
                        System.out.println("Exiting application. Thank you!");
                        return;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 6.");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert passenger details
    private static void insertPassengerDetails(Connection connection) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Passenger (pid, pName, sex, flightId, address) VALUES (?, ?, ?, ?, ?)");

            // Get passenger details from user
            System.out.print("Enter Passenger ID: ");
            int pid = scan.nextInt();

            System.out.print("Enter Passenger Name: ");
            String pName = scan.next();

            System.out.print("Enter Passenger Sex: ");
            String sex = scan.next();

            System.out.print("Enter Flight ID: ");
            int flightId = scan.nextInt();

            System.out.print("Enter Passenger City: ");
            String address = scan.next();

            // Set values in prepared statement
            preparedStatement.setInt(1, pid);
            preparedStatement.setString(2, pName);
            preparedStatement.setString(3, sex);
            preparedStatement.setInt(4, flightId);
            preparedStatement.setString(5, address);

            // Execute the statement
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Passenger details inserted successfully.");
            } else {
                System.out.println("Failed to insert passenger details.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter valid numbers for Passenger ID and Flight ID.");
        }
    }


    // Method to display flight details
    private static void displayFlightDetails(Connection connection) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Flight");

            System.out.println("Flight Details:");
            System.out.println("ID\tName\tSeats");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int seats = resultSet.getInt("seats");

                System.out.println(id + "\t" + name + "\t" + seats);
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error displaying flight details: " + e.getMessage());
        }
    }


    // Method to cancel ticket
    private static void cancelTicket(Connection connection) throws SQLException {
        try {
            // Prompt user for passenger ID
            System.out.print("Enter Passenger ID whose ticket you want to cancel: ");
            int pid = scan.nextInt();

            // Check if the passenger exists
            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM Passenger WHERE pid = ?");
            checkStatement.setInt(1, pid);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Passenger exists, delete the ticket
                PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Passenger WHERE pid = ?");
                deleteStatement.setInt(1, pid);
                int rowsAffected = deleteStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Ticket for passenger ID " + pid + " canceled successfully.");
                } else {
                    System.out.println("Failed to cancel ticket for passenger ID " + pid + ".");
                }
            } else {
                // Passenger does not exist
                System.out.println("Passenger with ID " + pid + " does not exist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter a valid number for Passenger ID.");
        }
    }

    // Method to update passenger information
    private static void updatePassengerInformation(Connection connection) throws SQLException {
        try {
            // Prompt user for passenger ID
            System.out.print("Enter Passenger ID whose information you want to update: ");
            int pid = scan.nextInt();

            // Check if the passenger exists
            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM Passenger WHERE pid = ?");
            checkStatement.setInt(1, pid);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Passenger exists, prompt user for new information
                System.out.print("Enter New Passenger Name: ");
                String pName = scan.next();

                System.out.print("Enter New Passenger Sex: ");
                String sex = scan.next();

                System.out.print("Enter New Passenger City: ");
                String address = scan.next();

                // Update passenger information
                PreparedStatement updateStatement = connection.prepareStatement("UPDATE Passenger SET pName = ?, sex = ?, address = ? WHERE pid = ?");
                updateStatement.setString(1, pName);
                updateStatement.setString(2, sex);
                updateStatement.setString(3, address);
                updateStatement.setInt(4, pid);
                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Passenger information updated successfully.");
                } else {
                    System.out.println("Failed to update passenger information.");
                }
            } else {
                // Passenger does not exist
                System.out.println("Passenger with ID " + pid + " does not exist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter a valid number for Passenger ID.");
        }
    }


    // Method to update flight details
    private static void updateFlightDetails(Connection connection) throws SQLException {
        try {
            // Prompt user for flight ID
            System.out.print("Enter Flight ID whose details you want to update: ");
            int flightId = scan.nextInt();

            // Check if the flight exists
            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM Flight WHERE id = ?");
            checkStatement.setInt(1, flightId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Flight exists, prompt user for new information
                System.out.print("Enter New Flight Name: ");
                String newName = scan.next();

                System.out.print("Enter New Number of Seats: ");
                int newSeats = scan.nextInt();

                // Update flight details
                PreparedStatement updateStatement = connection.prepareStatement("UPDATE Flight SET name = ?, seats = ? WHERE id = ?");
                updateStatement.setString(1, newName);
                updateStatement.setInt(2, newSeats);
                updateStatement.setInt(3, flightId);
                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Flight details updated successfully.");
                } else {
                    System.out.println("Failed to update flight details.");
                }
            } else {
                // Flight does not exist
                System.out.println("Flight with ID " + flightId + " does not exist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter a valid number for Flight ID and Number of Seats.");
        }
    }

}