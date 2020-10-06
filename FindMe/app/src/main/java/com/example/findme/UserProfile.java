package com.example.findme;

public class UserProfile {
    public String userEmail, userName, dateOfBirth, gender;

    public UserProfile(){

    }

    public UserProfile(String userEmail, String userName, String dateOfBirth, String gender) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public UserProfile(String userEmail, String userName) {
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
