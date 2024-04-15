package org.example.dto;

import org.example.enums.TerminalStatus;

import java.time.LocalDateTime;

public class TerminalDTO {
    private Integer id;
    private String code;
    private String address;
    private TerminalStatus status;
    private Boolean visible;
    private LocalDateTime createDate;

    public TerminalDTO() {
    }

    public TerminalDTO(Integer id, String code, String address, TerminalStatus status, Boolean visible, LocalDateTime createDate) {
        this.id = id;
        this.code = code;
        this.address = address;
        this.status = status;
        this.visible = visible;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TerminalStatus getStatus() {
        return status;
    }

    public void setStatus(TerminalStatus status) {
        this.status = status;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return
                "id = " + id +
                ", code = " + code +
                ", address = " + address  +
                ", status = " + status +
                ", createDate = " + createDate;
    }
}
