package com.example.Student_Library_Management_System.DTOs;

public class ReturnBookRequestDto {
    private int transId;

    public ReturnBookRequestDto() {
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }
}
