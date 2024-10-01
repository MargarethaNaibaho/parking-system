package com.ntt.repository;

import com.ntt.config.DBConnector;
import com.ntt.entity.ParkingVehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParkingRepository {
    public void createParkingVehicle(ParkingVehicle parkingVehicle){
        try(Connection connection = DBConnector.getConnection()){
            connection.setAutoCommit(false);

            String insertNewParkingVehicle = "INSERT INTO slot_parking (slot_num, registration_num, type, color) VALUES (?,?,?,?)";

            try(PreparedStatement preparedStatement = connection.prepareStatement(insertNewParkingVehicle)){

                preparedStatement.setInt(1,parkingVehicle.getSlot());
                preparedStatement.setString(2, parkingVehicle.getRegistrationNumber());
                preparedStatement.setString(3, parkingVehicle.getType());
                preparedStatement.setString(4, parkingVehicle.getColor());

                preparedStatement.executeUpdate();
                connection.commit();

            } catch (SQLException e){
                connection.rollback();
                System.out.println("Transaction rollback!");
                System.out.println(e.getMessage());
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void truncateTable(){
        try(Connection connection = DBConnector.getConnection()){
            connection.setAutoCommit(false);

            String truncateTable = "TRUNCATE TABLE slot_parking";

            try(PreparedStatement preparedStatement = connection.prepareStatement(truncateTable)){
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e){
                connection.rollback();
                System.out.println("Transaction rollback!");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteSlot(int slotNumber){
        try(Connection connection = DBConnector.getConnection()){
            connection.setAutoCommit(false);

            String deleteSlotParking = "DELETE FROM slot_parking WHERE slot_num = ?";

            try(PreparedStatement preparedStatement = connection.prepareStatement(deleteSlotParking)){

                preparedStatement.setInt(1, slotNumber);

                preparedStatement.executeUpdate();
                connection.commit();

            } catch (SQLException e){
                connection.rollback();
                System.out.println("Transaction rollback!");
                System.out.println(e.getMessage());
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<ParkingVehicle> getAllVehicles(){
        List<ParkingVehicle> parkingVehicleList = new ArrayList<>();
        String sql = "SELECT * FROM slot_parking ORDER BY slot_num";

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet resultSetAllVehicles = preparedStatement.executeQuery();
            while(resultSetAllVehicles.next()){
                ParkingVehicle parkingVehicle = new ParkingVehicle();

                parkingVehicle.setSlot(resultSetAllVehicles.getInt("slot_num"));
                parkingVehicle.setRegistrationNumber(resultSetAllVehicles.getString("registration_num"));
                parkingVehicle.setType(resultSetAllVehicles.getString("type"));
                parkingVehicle.setColor(resultSetAllVehicles.getString("color"));

                parkingVehicleList.add(parkingVehicle);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return parkingVehicleList;
    }

    public int countParked(String type){
        String sql = "SELECT COUNT(*) FROM slot_parking WHERE LOWER(type) = LOWER(?)";

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, type);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    public List<String> countParkedByColor(String color){
        List<String> registrationNum = new ArrayList<>();
        String sql = "SELECT registration_num FROM slot_parking WHERE LOWER(color) = LOWER(?)";

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, color);

            ResultSet resultSetAllVehicles = preparedStatement.executeQuery();
            while(resultSetAllVehicles.next()){
                registrationNum.add(resultSetAllVehicles.getString("registration_num"));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return registrationNum;
    }

    public Integer findSlotByRegistrationNumber(String registrationNumber){
        String sql = "SELECT slot_num FROM slot_parking WHERE LOWER(registration_num) = LOWER(?)";

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, registrationNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    public List<Integer> findSlotNumberByColor(String color){
        List<Integer> slotNum = new ArrayList<>();
        String sql = "SELECT slot_num FROM slot_parking WHERE LOWER(color) = LOWER(?)";

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, color);

            ResultSet resultSetAllVehicles = preparedStatement.executeQuery();
            while(resultSetAllVehicles.next()){
                slotNum.add(resultSetAllVehicles.getInt("slot_num"));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return slotNum;
    }
}
