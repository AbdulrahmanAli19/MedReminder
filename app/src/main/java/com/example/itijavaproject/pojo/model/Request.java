package com.example.itijavaproject.pojo.model;

public class Request {
    private String id_patient;
    private String mail_tacker;
    private boolean state;
    private boolean respond;
    public Request() {
    }

    public Request(String id_patient, String mail_tacker, boolean state, boolean respond) {
        this.id_patient = id_patient;
        this.mail_tacker = mail_tacker;
        this.state = state;
        this.respond = respond;
    }

    public String getId_patient() {
        return id_patient;
    }

    public void setId_patient(String id_patient) {
        this.id_patient = id_patient;
    }

    public String getMail_tacker() {
        return mail_tacker;
    }

    public void setMail_tacker(String mail_tacker) {
        this.mail_tacker = mail_tacker;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isRespond() {
        return respond;
    }

    public void setRespond(boolean respond) {
        this.respond = respond;
    }
}
