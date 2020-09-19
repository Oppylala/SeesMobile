package com.example.user.emergencyapps;

/**
 * Created by user on 8/24/2020.
 */

public class userSupportreports {
    private String supportemail;
    private String supportdetails;
    private String supportphone;

    public userSupportreports() {
    }

    public userSupportreports(String supportemail, String supportdetails, String supportphone) {
        this.supportemail = supportemail;
        this.supportdetails = supportdetails;
        this.supportphone = supportphone;
    }

    public String getSupportemail() {
        return supportemail;
    }

    public void setSupportemail(String supportemail) {
        this.supportemail = supportemail;
    }

    public String getSupportdetails() {
        return supportdetails;
    }

    public void setSupportdetails(String supportdetails) {
        this.supportdetails = supportdetails;
    }

    public String getSupportphone() {
        return supportphone;
    }

    public void setSupportphone(String supportphone) {
        this.supportphone = supportphone;
    }
}
