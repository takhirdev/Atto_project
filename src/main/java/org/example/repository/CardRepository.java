package org.example.repository;

import org.example.dto.CardDTO;
import org.example.enums.CardStatus;
import org.example.util.DataBaseConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CardRepository {
    public void createCard(CardDTO card) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "INSERT INTO card (number,exp_date,status) values (?,?,?)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, card.getNumber());
            prepareStatement.setDate(2, Date.valueOf(card.getExpDate()));
            prepareStatement.setString(3, card.getStatus().name());
            boolean result = prepareStatement.execute();

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

    public List<CardDTO> cardList() {
        List<CardDTO> list = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from card where visible = true");
            while (resultSet.next()) {
                CardDTO card = new CardDTO();
                card.setId(resultSet.getInt("id"));
                card.setNumber(resultSet.getString("number"));
                card.setExpDate(resultSet.getDate("exp_date").toLocalDate());
                card.setBalance(resultSet.getLong("balance"));
                card.setStatus(CardStatus.valueOf(resultSet.getString("status")));
                card.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                list.add(card);
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

    public CardDTO getByCardNumber(String number) {
        CardDTO cardDTO = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "select * from card where number = ? and visible = true ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, number);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                cardDTO = new CardDTO();
                cardDTO.setId(resultSet.getInt("id"));
                cardDTO.setNumber(resultSet.getString("number"));
                cardDTO.setExpDate(resultSet.getDate("exp_date").toLocalDate());
                cardDTO.setBalance(resultSet.getLong("balance"));
                cardDTO.setStatus(CardStatus.valueOf(resultSet.getString("status")));
                cardDTO.setVisible(resultSet.getBoolean("visible"));
                cardDTO.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                return cardDTO;
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
        return cardDTO;
    }

    public void updateCard(CardDTO card) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "update card set exp_date = ? where number = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setDate(1, Date.valueOf(card.getExpDate()));
            prepareStatement.setString(2, card.getNumber());

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

    public void changeStatus(CardDTO card) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "update card set status = ? where number = ? ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, String.valueOf(card.getStatus()));
            prepareStatement.setString(2, card.getNumber());

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

    public void delete(String cardNumber) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "update card set visible = false where number = ? ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, cardNumber);

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

    public List<CardDTO> userCardList(Integer id) {
        List<CardDTO> list = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = " select card.id, card.number, card.exp_date, card.balance,card.status,card.created_date from card " +
                    " left join profile_card on card.id = profile_card.card_id " +
                    " where profile_card.profile_id = ? and (card.visible = true and profile_card.visible = true) ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                CardDTO card = new CardDTO();
                card.setId(resultSet.getInt("id"));
                card.setNumber(resultSet.getString("number"));
                card.setExpDate(resultSet.getDate("exp_date").toLocalDate());
                card.setBalance(resultSet.getLong("balance"));
                card.setStatus(CardStatus.valueOf(resultSet.getString("status")));
                card.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                list.add(card);
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

    public void updateBalance(String number, Long amount) {

        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "update card set balance = ? where number = ? and visible = true ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setLong(1, amount);
            prepareStatement.setString(2, number);
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

    public CardDTO getCompanyCard(){
        CardDTO cardDTO = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "select * from card where number = ? and visible = true ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1,"8600000000000000");
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                cardDTO = new CardDTO();
                cardDTO.setId(resultSet.getInt("id"));
                cardDTO.setNumber(resultSet.getString("number"));
                cardDTO.setExpDate(resultSet.getDate("exp_date").toLocalDate());
                cardDTO.setBalance(resultSet.getLong("balance"));
                cardDTO.setStatus(CardStatus.valueOf(resultSet.getString("status")));
                cardDTO.setVisible(resultSet.getBoolean("visible"));
                cardDTO.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());

                return cardDTO;
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
        return cardDTO;
    }
}