package org.example.service;

import org.example.dto.CardDTO;
import org.example.dto.ProfileDTO;
import org.example.dto.TerminalDTO;
import org.example.dto.TransactionDTO;
import org.example.enums.CardStatus;
import org.example.enums.TransactionType;
import org.example.repository.CardRepository;
import org.example.repository.ProfileCardRepository;
import org.example.repository.TerminalRepository;
import org.example.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.List;

public class TransactionService {
    private CardRepository cardRepository;
    private ProfileCardRepository profileCardRepository;
    private TerminalRepository terminalRepository;
    private TransactionRepository transactionRepository;

    public void getUserTransaction(ProfileDTO profile) {

        List<CardDTO> userCardList = cardRepository.userCardList(profile.getId());
        System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", "Card Number", "Terminal Adress", "Amount", "Transaction Date", "Type");
        System.out.println("------------------------------------------------------------------------------------------------");
        for (CardDTO card : userCardList) {
            Integer cardId = card.getId();
            List<TransactionDTO> userTransaction = transactionRepository.getUserTransaction(cardId);
            for (TransactionDTO transaction : userTransaction) {
                System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", transaction.getCardNumber(), transaction.getTerminalAddress(), transaction.getAmount(), transaction.getTransactionDate(), transaction.getType());
            }
        }
    }

    public void refill(ProfileDTO profile, String cardNumber, Long amount) {
        CardDTO card = cardRepository.getByCardNumber(cardNumber);

        if (card == null) {
            System.out.println("Card not found! Mazgi....");
            return;
        }

        boolean result = profileCardRepository.isHave(card.getId(), profile.getId());
        if (!result) {
            System.out.println("Card not found! Mazgi....");
            return;
        }
        if (card.getStatus().equals(CardStatus.BLOCK)) {
            System.out.println("Card status not active!");
            return;
        }
        if (amount <= 0) {
            System.out.println("Amount cannot be negative! Mazgi....");
            return;
        }

        Long currentBalance = card.getBalance();
        currentBalance += amount;

        cardRepository.updateBalance(cardNumber, currentBalance);

        TransactionDTO transaction = new TransactionDTO();
        transaction.setCardId(card.getId());
        transaction.setAmount(amount);
        transaction.setType(TransactionType.REFILL);
        transactionRepository.refill(transaction);

        System.out.println("Success");
    }

    public void payment(String cardNumber, String terminalCode) {
        CardDTO card = cardRepository.getByCardNumber(cardNumber);
        TerminalDTO terminal = terminalRepository.getByCode(terminalCode);

        Long payment = 1700L;
        if (card == null) {
            System.out.println("Card not found! Mazgi....");
            return;
        }
        if (terminal == null) {
            System.out.println("Terminal not found! Mazgi....");
            return;
        }
        if (card.getStatus().equals(CardStatus.BLOCK)) {
            System.out.println("Card status not active");
            return;
        }

        Long balance = card.getBalance();
        if (balance < payment) {
            System.out.println("There is not enough money on the card");
        } else {
            balance -= payment;
        }
        cardRepository.updateBalance(cardNumber, balance);

        CardDTO companyCard = cardRepository.getCompanyCard();
        Long companyCardBalance = companyCard.getBalance();
        companyCardBalance += payment;
        cardRepository.updateBalance(companyCard.getNumber(),companyCardBalance);

        TransactionDTO transaction = new TransactionDTO();
        transaction.setCardId(card.getId());
        transaction.setAmount(payment);
        transaction.setTerminalId(terminal.getId());
        transaction.setType(TransactionType.PAYMENT);

        transactionRepository.payment(transaction);

        System.out.println("Success");

    }

    public void transactionList() {
        List<TransactionDTO> transactionList = transactionRepository.transactionList();
        transactionList.forEach(System.out::println);
    }

    public void todayTransactions() {
        LocalDate today = LocalDate.now();
        List<TransactionDTO> transactionList = transactionRepository.todayTransactions(today);
        System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", "Card Number", "Terminal Adress", "Amount", "Transaction Date", "Type");
        System.out.println("------------------------------------------------------------------------------------------------");
        for (TransactionDTO transaction : transactionList) {
            System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", transaction.getCardNumber(), transaction.getTerminalAddress(), transaction.getAmount(), transaction.getTransactionDate(), transaction.getType());
        }
    }

    public void dailyTransactions(LocalDate date) {
        LocalDate toDate = date;
        LocalDate fromDate = date.plusDays(1);
        List<TransactionDTO> transactionList = transactionRepository.intervalTransactions(toDate,fromDate);
        System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", "Card Number", "Terminal Adress", "Amount", "Transaction Date", "Type");
        System.out.println("------------------------------------------------------------------------------------------------");
        for (TransactionDTO transaction : transactionList) {
            System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", transaction.getCardNumber(), transaction.getTerminalAddress(), transaction.getAmount(), transaction.getTransactionDate(), transaction.getType());
        }
    }

    public void intervalTransactions(LocalDate fromDate, LocalDate toDate) {
        List<TransactionDTO> transactionList = transactionRepository.intervalTransactions(fromDate,toDate);
        System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", "Card Number", "Terminal Adress", "Amount", "Transaction Date", "Type");
        System.out.println("------------------------------------------------------------------------------------------------");
        for (TransactionDTO transaction : transactionList) {
            System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", transaction.getCardNumber(), transaction.getTerminalAddress(), transaction.getAmount(), transaction.getTransactionDate(), transaction.getType());
        }
    }

    public void transactionByTerminal(String terminalCode) {
        TerminalDTO terminal = terminalRepository.getByCode(terminalCode);

        if (terminal==null){
            System.out.println("Terminal not found! Mazgi...");
            return;
        }

        List<TransactionDTO> transactionList = transactionRepository.getByTerminalId(terminal.getId());
        System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", "Card Number", "Terminal Adress", "Amount", "Transaction Date", "Type");
        System.out.println("------------------------------------------------------------------------------------------------");
        for (TransactionDTO transaction : transactionList) {
            System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", transaction.getCardNumber(), transaction.getTerminalAddress(), transaction.getAmount(), transaction.getTransactionDate(), transaction.getType());
        }
    }

    public void transactionByCard(String cardNumber) {
        CardDTO card = cardRepository.getByCardNumber(cardNumber);
        if (card==null){
            System.out.println("Card not found! Mazgi...");
            return;
        }
        List<TransactionDTO>transactionList=transactionRepository.getByCardNumber(cardNumber);
        System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", "Card Number", "Terminal Adress", "Amount", "Transaction Date", "Type");
        System.out.println("------------------------------------------------------------------------------------------------");
        for (TransactionDTO transaction : transactionList) {
            System.out.printf("| %-20s | %-15s | %-10s | %-25s | %-10s |\n", transaction.getCardNumber(), transaction.getTerminalAddress(), transaction.getAmount(), transaction.getTransactionDate(), transaction.getType());
        }
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void setProfileCardRepository(ProfileCardRepository profileCardRepository) {
        this.profileCardRepository = profileCardRepository;
    }

    public void setTerminalRepository(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
