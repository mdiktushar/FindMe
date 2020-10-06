package com.example.findme;

import com.google.firebase.database.Exclude;

public class UserProfile {
    public String userEmail, userName, dateOfBirth, gender;
    public String description , experience , phone, address, school, collage, university, skills;
    public String imageUri;
    public String key;

    public UserProfile(String imageUri) {
        this.imageUri = imageUri;
    }

    public UserProfile(String userEmail, String userName, String dateOfBirth, String gender, String description, String experience, String phone, String address, String school, String collage, String university, String skills, String imageUri) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.description = description;
        this.experience = experience;
        this.phone = phone;
        this.address = address;
        this.school = school;
        this.collage = collage;
        this.university = university;
        this.skills = skills;
        this.imageUri = imageUri;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
