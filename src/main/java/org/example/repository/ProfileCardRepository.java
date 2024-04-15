package org.example.repository;

import org.example.util.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileCardRepository {
    public void addCard(Integer cardId, Integer profileId){
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "insert into profile_card (card_id,profile_id) values (?,?)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1,cardId);
            prepareStatement.setInt(2,profileId);

            prepareStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void delete(Integer Cardid) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "update profile_card set visible = false where card_id = ? ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, Cardid);

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
    public Boolean isHave(Integer cardId, Integer profileId){
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "select * from profile_card where (card_id = ? and profile_id = ?) and visible = true";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1,cardId);
            prepareStatement.setInt(2,profileId);
            ResultSet resultSet = prepareStatement.executeQuery();

            Boolean result = resultSet.next();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (connection!= null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public Integer getCardCount(Integer id){
        Connection connection = null;
        int cardCount = 0;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "select * from profile_card where profile_id = ? and visible = true ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1,id);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()){
                cardCount++;
            }
            return cardCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
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
