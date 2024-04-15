package org.example.repository;

import org.example.dto.TerminalDTO;
import org.example.enums.TerminalStatus;
import org.example.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TerminalRepository {
    public void create(TerminalDTO terminal) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "insert into terminal (code,address,status) values (?,?,?)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1,terminal.getCode());
            prepareStatement.setString(2,terminal.getAddress());
            prepareStatement.setString(3, String.valueOf(terminal.getStatus()));

            prepareStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public TerminalDTO getByCode(String code) {
        TerminalDTO terminal = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "select * from terminal where code = ? and visible = true ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, code);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                terminal = new TerminalDTO();
                terminal.setId(resultSet.getInt("id"));
                terminal.setCode(resultSet.getString("code"));
                terminal.setAddress(resultSet.getString("address"));
                terminal.setStatus(TerminalStatus.valueOf(resultSet.getString("status")));
                terminal.setCreateDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                return terminal;
            }
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
        return terminal;
    }

    public List<TerminalDTO> terminalList() {
        List<TerminalDTO> terminalList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "select * from terminal where visible = true";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                TerminalDTO terminal = new TerminalDTO();
                terminal.setId(resultSet.getInt("id"));
                terminal.setCode(resultSet.getString("code"));
                terminal.setAddress(resultSet.getString("address"));
                terminal.setStatus(TerminalStatus.valueOf(resultSet.getString("status")));
                terminal.setCreateDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                terminalList.add(terminal);
            }
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
        return terminalList;
    }

    public void update(TerminalDTO terminal) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "update terminal set address = ? where code = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1,terminal.getAddress());
            prepareStatement.setString(2,terminal.getCode());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void changeStatus(TerminalDTO terminal) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "update terminal set status = ? where code = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1,terminal.getStatus().name());
            prepareStatement.setString(2,terminal.getCode());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void delete(String code) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "update terminal set visible = false where code = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1,code);

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
