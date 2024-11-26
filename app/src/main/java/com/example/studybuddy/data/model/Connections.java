package com.example.studybuddy.data.model;

import java.util.ArrayList;

public class Connections {
    private String connectionID;
<<<<<<< Updated upstream
    private String senderEmail;
    private String receiverEmail;
    private String status;

    public Connections(String connectionID, String senderEmail, String receiverEmail, String status) {
        this.connectionID = connectionID;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.status = status;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
=======
    private String senderID;
    private String receiverID;
    private String status;

    public Connections (String connectionID, String senderID, String receiverID, String status) {
        this.connectionID = connectionID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.status = status;
    }


    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
>>>>>>> Stashed changes
    }

    public String getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(String connectionID) {
        this.connectionID = connectionID;
    }

<<<<<<< Updated upstream
    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
=======
    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
>>>>>>> Stashed changes
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
