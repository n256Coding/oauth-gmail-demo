package com.n256coding.oauthgmaildemo.models;

public class Message {
    public String from;
    public String to;
    public String subject;
    public String date;
    public String body;

    public Message() {
    }

    public Message(String from, String to, String subject, String date, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.date = date;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
