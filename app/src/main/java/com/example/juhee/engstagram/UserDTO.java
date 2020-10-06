package com.example.juhee.engstagram;

public class UserDTO {
    String userID;

    public UserDTO(String userID)
    {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
