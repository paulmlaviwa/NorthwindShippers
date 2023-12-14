package com.pluralsight;

import com.pluralsight.Shipper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShippersDataManager {
    private static final String URL = "jdbc:mysql://localhost:3306/northwind";
    private static final String USER = "root";
    private static final String PASSWORD = "*********";

    public int insertShipper(Shipper shipper) throws SQLException {
        String sql = "INSERT INTO shippers (CompanyName, Phone) VALUES (?, ?)";
        int generatedId = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, shipper.getCompanyName());
            preparedStatement.setString(2, shipper.getPhone());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = resultSet.getInt(1);
                }
            }
        }

        return generatedId;
    }

    public List<Shipper> getAllShippers() throws SQLException {
        String sql = "SELECT ShipperID, CompanyName, Phone FROM shippers";
        List<Shipper> shippers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Shipper shipper = new Shipper();
                shipper.setShipperId(resultSet.getInt("ShipperID"));
                shipper.setCompanyName(resultSet.getString("CompanyName"));
                shipper.setPhone(resultSet.getString("Phone"));
                shippers.add(shipper);
            }
        }

        return shippers;
    }

    public void updateShipperPhone(int shipperId, String newPhone) throws SQLException {
        String sql = "UPDATE shippers SET Phone = ? WHERE ShipperID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newPhone);
            preparedStatement.setInt(2, shipperId);

            preparedStatement.executeUpdate();
        }
    }

    public void deleteShipper(int shipperId) throws SQLException {
        String sql = "DELETE FROM shippers WHERE ShipperID = ? AND ShipperID NOT BETWEEN 1 AND 3";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, shipperId);

            preparedStatement.executeUpdate();
        }
    }
}
