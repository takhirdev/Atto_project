package org.example.dto;

import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;

import java.time.LocalDateTime;

public class ProfileDTO {

    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String password;
    private LocalDateTime created_date;
    private Boolean visible;
    private ProfileStatus status;
    private ProfileRole role;
    private Integer cardCount;

    public ProfileDTO(Integer id, String name, String surname, String phone, String password, LocalDateTime created_date, Boolean visible, ProfileStatus status, ProfileRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.password = password;
        this.created_date = created_date;
        this.visible = visible;
        this.status = status;
        this.role = role;
    }

    public ProfileDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public ProfileStatus getStatus() {
        return status;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
    }

    public ProfileRole getRole() {
        return role;
    }

    public void setRole(ProfileRole role) {
        this.role = role;
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", name = " + name +
                ", surname = " + surname  +
                ", phone = " + phone  +
                ", cardCount = " + cardCount  +
                ", password = " + password +
                ", created_date = " + created_date +
                ", status = " + status;
    }
}
