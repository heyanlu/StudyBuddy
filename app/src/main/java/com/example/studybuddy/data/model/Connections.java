package com.example.studybuddy.data.model;

import java.util.ArrayList;

public class Connections {
    private String connectionID;
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
    }

    public String getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(String connectionID) {
        this.connectionID = connectionID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
