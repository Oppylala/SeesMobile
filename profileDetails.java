package com.example.user.emergencyapps;

/**
 * Created by user on 9/1/2020.
 */

public class profileDetails {
    private String Username;
    private String Fullname;
    private String Phone;
    private String Image;

    public profileDetails() {
    }

    public profileDetails(String username, String fullname, String phone, String image) {
        this.Username = username;
        this.Fullname = fullname;
        this.Phone = phone;
        this.Image = image;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
