package org.example.util;

import org.example.dto.CardDTO;
import org.example.dto.ProfileDTO;
import org.example.enums.CardStatus;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.repository.CardRepository;
import org.example.repository.ProfileRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatabaseUtill {


    public static void createTable() {
        String profileTable = "CREATE TABLE IF NOT EXISTS profile (id serial primary key," +
                "name varchar(25) not null," +
                "surname varchar(25) not null," +
                "phone varchar(12) not null," +
                "password varchar not null," +
                "created_date timestamp default now()," +
                "visible bool default TRUE," +
                "status varchar(10) not null," +
                "role varchar(10) not null);";
        execute(profileTable);

        String cardTable = "CREATE TABLE IF NOT EXISTS card (" +
                "id serial primary key," +
                "number varchar(16)  not null," +
                "exp_date date not null," +
                "balance numeric default 0.0," +
                "status varchar(10) not null, " +
                "visible bool default TRUE," +
                "created_date timestamp default now());";
        execute(cardTable);

        String profileCardTable = "CREATE TABLE IF NOT EXISTS profile_card (" +
                "id serial primary key," +
                "card_id Integer not null," +
                "profile_id Integer not null," +
                "visible bool default TRUE," +
                "created_date timestamp default now());";
        execute(profileCardTable);

        String terminalTable = "CREATE TABLE IF NOT EXISTS terminal (" +
                "id serial primary key," +
                "code varchar  not null," +
                "address varchar not null," +
                "status varchar(10) not null," +
                "visible bool default TRUE," +
                "created_date timestamp default now());";
        execute(terminalTable);

        String transactionsTable = "CREATE TABLE IF NOT EXISTS transaction (" +
                " id serial primary key," +
                "card_id integer not null," +
                "amount integer not null," +
                "terminal_id integer," +
                "type varchar(10) not null," +
                "transaction_date timestamp default now());";
        execute(transactionsTable);

        initCompanyCard();
    }

    private static void execute(String sql) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnect();
            Statement statement = connection.createStatement();
            statement.execute(sql);
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

    public static void initAdmin() {
        ProfileRepository profileRepository = new ProfileRepository();
        String phone = "998946486808";
        ProfileDTO exists = profileRepository.getByPhone(phone);
        if (exists != null) {
            return;
        }
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName("Takhir");
        profileDTO.setSurname("Ismailov");
        profileDTO.setPhone(phone);
        profileDTO.setPassword(MD5Util.getMd5("takhir7800"));
        profileDTO.setCreated_date(LocalDateTime.now());
        profileDTO.setVisible(true);
        profileDTO.setStatus(ProfileStatus.ACTIVE);
        profileDTO.setRole(ProfileRole.ADMIN);

        profileRepository.registration(profileDTO);
    }
    public static void initCompanyCard(){
        CardRepository cardRepository = new CardRepository();
        String cardNumber = "8600000000000000";
        CardDTO exists = cardRepository.getByCardNumber(cardNumber);
        if (exists!=null){
            return;
        }
        CardDTO card = new CardDTO();
        card.setNumber(cardNumber);
        card.setExpDate(LocalDate.parse("2024-04-09"));
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.createCard(card);
    }
}
