package com.example.qa_answer.Data.Model;

public class User {
    String idUser;
    String nameOfUser;
    String phoneNumber;
    String email;
    int token;

    public User(String idUser, String nameOfUser, String phoneNumber, String email, int token) {
        this.idUser = idUser;
        this.nameOfUser = nameOfUser;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.token = token;
    }

    public User() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", nameOfUser='" + nameOfUser + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", token=" + token +
                '}';
    }
}
