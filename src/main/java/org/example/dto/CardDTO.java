package org.example.dto;

import org.example.enums.CardStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDTO {
    private Integer id;
    private String number;
    private LocalDate expDate;
    private Long balance;
    private CardStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;

    public CardDTO() {
    }

    public CardDTO(Integer id, String number, LocalDate expDate, Long balance, CardStatus status, Boolean visible, LocalDateTime createdDate) {
        this.id = id;
        this.number = number;
        this.expDate = expDate;
        this.balance = balance;
        this.status = status;
        this.visible = visible;
        this.createdDate = createdDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getExpDate() {
        return expDate;

    }
    public void setExpDate(LocalDate exp_date) {
        this.expDate = exp_date;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "id = " + id +
                " , number = " + number +
                " , exp_date = " + expDate +
                " , balance = " + balance +
                " , status = " + status +
                " , created_date = " + createdDate;
    }
}
