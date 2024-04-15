package org.example.repository;

import org.example.dto.TransactionDTO;
import org.example.enums.TransactionType;
import org.example.util.DataBaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class TransactionRepository {

    public List<TransactionDTO> getUserTransaction(Integer cardId) {
        List<TransactionDTO> list = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "select card.number,terminal.address, transaction.amount,transaction.transaction_date,transaction.type from transaction " +
                    "left join card on card.id = transaction.card_id " +
                    "left join terminal on terminal.id = transaction.terminal_id " +
                    "where card_id = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, cardId);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                TransactionDTO transaction = new TransactionDTO();
                transaction.setCardNumber(resultSet.getString("number"));
                transaction.setTerminalAddress(resultSet.getString("address"));
                transaction.setAmount(resultSet.getLong("amount"));
                transaction.setTransactionDate(resultSet.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));

                list.add(transaction);
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

    public void payment(TransactionDTO transaction) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "insert into transaction (card_id,amount,terminal_id,type) values (?,?,?,?)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, transaction.getCardId());
            prepareStatement.setLong(2, transaction.getAmount());
            prepareStatement.setInt(3, transaction.getTerminalId());
            prepareStatement.setString(4, transaction.getType().name());
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

    public void refill(TransactionDTO transaction) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = "insert into transaction (card_id,amount,type) values (?,?,?)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, transaction.getCardId());
            prepareStatement.setLong(2, transaction.getAmount());
            prepareStatement.setString(3, transaction.getType().name());
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

    public List<TransactionDTO> transactionList() {
        List<TransactionDTO> transactionList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = " select card.number,profile.name,profile.surname,terminal.code,terminal.address," +
                    " transaction.amount,transaction.transaction_date," +
                    " transaction.type from transaction " +
                    " left join card on card.id = transaction.card_id " +
                    " left join profile_card on profile_card.card_id = card.id " +
                    " left join profile on profile.id = profile_card.profile_id " +
                    " left join terminal on terminal.id = transaction.terminal_id;";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                TransactionDTO transaction = new TransactionDTO();
                transaction.setCardNumber(resultSet.getString("number"));
                transaction.setProfileName(resultSet.getString("name"));
                transaction.setProfileName(resultSet.getString("surname"));
                transaction.setTerminalNumber(resultSet.getString("code"));
                transaction.setTerminalAddress(resultSet.getString("address"));
                transaction.setAmount(resultSet.getLong("amount"));
                transaction.setTransactionDate(resultSet.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transactionList.add(transaction);
            }
            return transactionList;
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

    public List<TransactionDTO> todayTransactions(LocalDate today) {
        List<TransactionDTO> transactionList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = " select card.number,terminal.address," +
                    " transaction.amount, transaction.transaction_date," +
                    " transaction.type from transaction" +
                    " left join card on card.id = transaction.card_id" +
                    " left join terminal on terminal.id = transaction.terminal_id" +
                    " where transaction.transaction_date >= ?" +
                    " order by transaction.id desc;";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setDate(1, Date.valueOf(today));
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                TransactionDTO transaction = new TransactionDTO();
                transaction.setCardNumber(resultSet.getString("number"));
                transaction.setTerminalAddress(resultSet.getString("address"));
                transaction.setAmount(resultSet.getLong("amount"));
                transaction.setTransactionDate(resultSet.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transactionList.add(transaction);
            }
            return transactionList;
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

    public List<TransactionDTO> intervalTransactions(LocalDate fromDate, LocalDate toDate) {
        List<TransactionDTO> transactionList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = " select card.number,terminal.address," +
                    " transaction.amount, transaction.transaction_date," +
                    " transaction.type from transaction" +
                    " left join card on card.id = transaction.card_id" +
                    " left join terminal on terminal.id = transaction.terminal_id" +
                    " where transaction.transaction_date between ? and ?" +
                    " order by transaction.id desc;";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setDate(1, Date.valueOf(fromDate));
            prepareStatement.setDate(2, Date.valueOf(toDate));
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                TransactionDTO transaction = new TransactionDTO();
                transaction.setCardNumber(resultSet.getString("number"));
                transaction.setTerminalAddress(resultSet.getString("address"));
                transaction.setAmount(resultSet.getLong("amount"));
                transaction.setTransactionDate(resultSet.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transactionList.add(transaction);
            }
            return transactionList;
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

    public List<TransactionDTO> getByTerminalId(Integer id) {
        List<TransactionDTO> transactionList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = " select card.number,terminal.address," +
                    " transaction.amount, transaction.transaction_date," +
                    " transaction.type from transaction" +
                    " join card on card.id = transaction.card_id" +
                    " join terminal on terminal.id = transaction.terminal_id" +
                    " where terminal.id = ?" +
                    " order by transaction.id desc;";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                TransactionDTO transaction = new TransactionDTO();
                transaction.setCardNumber(resultSet.getString("number"));
                transaction.setTerminalAddress(resultSet.getString("address"));
                transaction.setAmount(resultSet.getLong("amount"));
                transaction.setTransactionDate(resultSet.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transactionList.add(transaction);
            }
            return transactionList;
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

    public List<TransactionDTO> getByCardNumber(String cardNumber) {
        List<TransactionDTO> transactionList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            String sql = " select card.number,terminal.address," +
                    " transaction.amount, transaction.transaction_date," +
                    " transaction.type from transaction" +
                    " join card on card.id = transaction.card_id" +
                    " join terminal on terminal.id = transaction.terminal_id" +
                    " where card.number = ?" +
                    " order by transaction.id desc;";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, cardNumber);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                TransactionDTO transaction = new TransactionDTO();
                transaction.setCardNumber(resultSet.getString("number"));
                transaction.setTerminalAddress(resultSet.getString("address"));
                transaction.setAmount(resultSet.getLong("amount"));
                transaction.setTransactionDate(resultSet.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transactionList.add(transaction);
            }
            return transactionList;
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

