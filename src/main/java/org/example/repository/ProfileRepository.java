package org.example.repository;

import org.example.container.ComponentContainer;
import org.example.dto.ProfileDTO;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProfileRepository {
    public void registration(ProfileDTO profileDTO) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "INSERT INTO profile (name,surname,phone,password,role,status) values (?,?,?,?,?,?)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, profileDTO.getName());
            prepareStatement.setString(2, profileDTO.getSurname());
            prepareStatement.setString(3, profileDTO.getPhone());
            prepareStatement.setString(4, profileDTO.getPassword());
            prepareStatement.setString(5, profileDTO.getRole().name());
            prepareStatement.setString(6, profileDTO.getStatus().name());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public ProfileDTO getByPhone(String phone) {
        ProfileDTO profileDTO = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "SELECT * FROM profile WHERE phone = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, phone);
            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                profileDTO = ComponentContainer.currentProfile;
                profileDTO.setId(resultSet.getInt("id"));
                profileDTO.setName(resultSet.getString("name"));
                profileDTO.setSurname(resultSet.getString("surname"));
                profileDTO.setPhone(resultSet.getString("phone"));
                profileDTO.setPassword(resultSet.getString("password"));
                profileDTO.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                profileDTO.setVisible(resultSet.getBoolean("visible"));
                profileDTO.setStatus(ProfileStatus.valueOf(resultSet.getString("status")));
                profileDTO.setRole(ProfileRole.valueOf(resultSet.getString("role")));

            }
            return profileDTO;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public List<ProfileDTO> profileList() {
        List<ProfileDTO> list = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "SELECT * FROM profile where role = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, String.valueOf(ProfileRole.USER));
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                ProfileDTO profile = new ProfileDTO();
                profile.setId(resultSet.getInt("id"));
                profile.setName(resultSet.getString("name"));
                profile.setSurname(resultSet.getString("surname"));
                profile.setPhone(resultSet.getString("phone"));
                profile.setPassword(resultSet.getString("password"));
                profile.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                profile.setVisible(resultSet.getBoolean("visible"));
                profile.setStatus(ProfileStatus.valueOf(resultSet.getString("status")));
                profile.setRole(ProfileRole.valueOf(resultSet.getString("role")));
                list.add(profile);
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void changeStatus(ProfileDTO profile) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "update profile set status = ? where phone = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, profile.getStatus().name());
            prepareStatement.setString(2, profile.getPhone());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
