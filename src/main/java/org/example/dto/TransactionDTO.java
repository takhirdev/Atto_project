package org.example.dto;

import org.example.enums.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Integer cardId;
    private Integer terminalId;
    private String cardNumber;
    private String profileName;
    private String profileSurname;
    private String terminalNumber;
    private String terminalAddress;
    private Long amount;
    private LocalDateTime transactionDate;
    private TransactionType type;

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileSurname() {
        return profileSurname;
    }

    public void setProfileSurname(String profileSurname) {
        this.profileSurname = profileSurname;
    }

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public String getTerminalAddress() {
        return terminalAddress;
    }

    public void setTerminalAddress(String terminalAddress) {
        this.terminalAddress = terminalAddress;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return
                "cardNumber = " + cardNumber +
                ", profileName = " + profileName +
                ", profileSurname = " + profileSurname +
                ", terminalNumber = " + terminalNumber +
                ", terminalAddress = " + terminalAddress +
                ", amount = " + amount +
                ", transactionDate = " + transactionDate +
                ", type = " + type;
    }
}
