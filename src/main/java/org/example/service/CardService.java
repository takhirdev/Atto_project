package org.example.service;

import org.example.dto.CardDTO;
import org.example.dto.ProfileDTO;
import org.example.enums.CardStatus;
import org.example.repository.CardRepository;
import org.example.repository.ProfileCardRepository;
import org.example.repository.ProfileRepository;

import java.util.List;

public class CardService {
    private CardRepository cardRepository;
    private ProfileCardRepository profileCardRepository;

    public void createCard(CardDTO card) {

        if (!(cardNumberIsValid(card.getNumber()) && card.getNumber().length() == 16)) {
            System.out.println("Card number is wrong!");
            return;
        }
        CardDTO exists = cardRepository.getByCardNumber(card.getNumber());

        if (exists != null) {
            System.out.println("Card already exists! Mazgi....");
            return;
        }
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.createCard(card);
        System.out.println("Success");
    }

    public void cardList() {
        List<CardDTO> list = cardRepository.cardList();
        list.forEach(System.out::println);
    }

    public void updateCard(CardDTO card) {
        CardDTO exists = cardRepository.getByCardNumber(card.getNumber());
        if (exists == null) {
            System.out.println("Card is not found! Mazgi....");
            return;
        }
        cardRepository.updateCard(card);
        System.out.println("Success");
    }
    public void changeStatusByAdmin(String cardNumber) {
        CardDTO card = cardRepository.getByCardNumber(cardNumber);
        if (card == null) {
            System.out.println("Card is not found! Mazgi....");
            return;
        }
        CardStatus status = card.getStatus();
        if (status.equals(CardStatus.ACTIVE)) {
            status = CardStatus.BLOCK;
        } else {
            status = CardStatus.ACTIVE;
        }
        card.setStatus(status);
        cardRepository.changeStatus(card);
        System.out.println("Success");
    }
    public void changeStatusByUser(String cardNumber, ProfileDTO profile) {
        CardDTO card = cardRepository.getByCardNumber(cardNumber);
        if (card == null) {
            System.out.println("Card is not found! Mazgi....");
            return;
        }
        boolean result = profileCardRepository.isHave(card.getId(), profile.getId());
        if (!result) {
            System.out.println("Card not found! Mazgi....");
            return;
        }
        CardStatus status = card.getStatus();
        if (status.equals(CardStatus.ACTIVE)) {
            status = CardStatus.BLOCK;
        } else {
            status = CardStatus.ACTIVE;
        }
        card.setStatus(status);
        cardRepository.changeStatus(card);
        System.out.println("Success");
    }

    public void delete(String cardNumber) {
        CardDTO card = cardRepository.getByCardNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found! Mazgi....");
            return;
        }

        profileCardRepository.delete(card.getId());
        cardRepository.delete(cardNumber);
        System.out.println("Success");
    }

    public void deleteUserCard(String cardNumber, ProfileDTO profile) {
        CardDTO card = cardRepository.getByCardNumber(cardNumber);

        if(card == null){
            System.out.println("Card not found! Mazgi....");
            return;
        }
        boolean result = profileCardRepository.isHave(card.getId(), profile.getId());
        if (!result) {
            System.out.println("Card not found! Mazgi....");
            return;
        }
        profileCardRepository.delete(card.getId());
        System.out.println("Success");
    }

    public void addCard(String cardNumber, ProfileDTO profile) {
        CardDTO card = cardRepository.getByCardNumber(cardNumber);

        if (card == null) {
            System.out.println("No such card exists");
            return;
        }
        boolean result = profileCardRepository.isHave(card.getId(), profile.getId());
        if (result) {
            System.out.println("This card already added!");
            return;
        }
        profileCardRepository.addCard(card.getId(), profile.getId());
        System.out.println("Success");
    }

    public void userCardList(Integer id) {
        List<CardDTO> list = cardRepository.userCardList(id);
        list.forEach(System.out::println);
    }

    public boolean cardNumberIsValid(String number) {
        for (int i = 0; i < number.length(); i++) {
            char num = number.charAt(i);
            if (!Character.isDigit(num)) {
                return false;
            }
        }
        return true;
    }

    public void getCompanyBalance() {
        CardDTO companyCard = cardRepository.getCompanyCard();
        Long balance = companyCard.getBalance();
        System.out.println("Balance: " + balance);
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    public void setProfileCardRepository(ProfileCardRepository profileCardRepository) {
        this.profileCardRepository = profileCardRepository;
    }
}
